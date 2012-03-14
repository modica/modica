package org.modica.parser.lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.PrintingSFIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerHandlers;
import org.modica.parser.StructuredFieldIntroducerParser;

public class LazyParser {

    public static void main(String[] args) throws IOException {
        final FileInputStream input = new FileInputStream(new File(args[0]));
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        LazySFCreatingHandler lazySFCreator = new LazySFCreatingHandler(
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
        List<LazyStructuredField> fields = lazySFCreator.getStructuredFields();
        // Can return the model now
        for (LazyStructuredField sf : fields) {
            System.out.println(sf);
            sf.detach();
        }
        // simulate new session 
        final FileInputStream newInput = new FileInputStream(new File(args[0]));
        // We need to control access to Full SFs in a better way!
        for (LazyStructuredField sf : fields) {
            sf.attach(newInput.getChannel());
        }
        // simulate trigger of lazy load
        for (StructuredField sf : fields) {
            System.out.println(sf.getParameters());
        }
        // simulate session end 
        for (LazyStructuredField sf : fields) {
            sf.detach();
        }
        newInput.close();
    }

}
