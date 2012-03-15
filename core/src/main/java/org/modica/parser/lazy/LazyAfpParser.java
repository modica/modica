package org.modica.parser.lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.PrintingSFIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandlers;
import org.modica.parser.StructuredFieldIntroducerParser;

public class LazyAfpParser {

    public static void main(String[] args) throws IOException {
        final FileInputStream input = new FileInputStream(new File(args[0]));
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        LazyAfpCreatingHandler lazySFCreator = new LazyAfpCreatingHandler(
                new PrintingSFHandler(System.out), input, streamShutdown);
        StructuredFieldIntroducerHandler handlers = StructuredFieldIntroducerHandlers.chain(
                lazySFCreator, new PrintingSFIntroducerHandler(System.out));
        StructuredFieldIntroducerParser parser = new StructuredFieldIntroducerParser(input, handlers);
        // Create a lazy SFTree
        parser.parse();
        // Do not close the stream until all the contexts have been resolved
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    streamShutdown.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        LazyAfp lazyAfp = lazySFCreator.getLazyAfp();
        // Can return the model now
        for (StructuredField sf : lazyAfp.getStructuredFields()) {
            System.out.println(sf);
        }
        lazyAfp.detach();
        // simulate new session 
        final FileInputStream newInput = new FileInputStream(new File(args[0]));
        // We need to control access to Full SFs in a better way!
        lazyAfp.attach(newInput.getChannel());
        // simulate trigger of lazy load
        for (StructuredField sf : lazyAfp.getStructuredFields()) {
            System.out.println(sf.getParameters());
        }
        // simulate session end 
        lazyAfp.detach();
        newInput.close();
    }

}
