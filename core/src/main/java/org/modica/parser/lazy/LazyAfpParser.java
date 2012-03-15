package org.modica.parser.lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.PrintingSFHandler;
import org.modica.parser.StructuredFieldHandler;
import org.modica.parser.StructuredFieldIntroducerParser;

public class LazyAfpParser {

    public static void main(String[] args) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(new File(args[0]));
        // This latch can be used to coordinate stream closing
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        // This printer will demonstrate that we are creating Lazy SFs
        final StructuredFieldHandler printer = new PrintingSFHandler(System.out);
        LazyAfpCreatingHandler lazySFCreator = new LazyAfpCreatingHandler(printer, fileInputStream,
                streamShutdown);
        StructuredFieldIntroducerParser parser = new StructuredFieldIntroducerParser(fileInputStream,
                lazySFCreator);
        // Create the lazy AFP
        parser.parse();
        //Close the stream when all contexts have been resolved
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    streamShutdown.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        LazyAfp lazyAfp = lazySFCreator.getLazyAfp();
        // We could do a deep query of the SFs in this 'session', but we won't...
        lazyAfp.detach();
        // Begin a new session
        final FileInputStream newInput = new FileInputStream(new File(args[0]));
        lazyAfp.attach(newInput.getChannel());
        // Trigger lazy loading of all SFs, (of course this could be random access)
        for (StructuredField sf : lazyAfp.getStructuredFields()) {
            for (ParameterAsString param : sf.getParameters()) {
                System.out.println(param);
            }
        }
        // End the session
        lazyAfp.detach();
        newInput.close();
    }

}
