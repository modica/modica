package org.afpparser.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.afpparser.afp.modca.StructuredField;

public class DocumentReader {

    private final FileChannel channel;

    public DocumentReader(File afpDoc) throws FileNotFoundException {
        this(new FileInputStream(afpDoc));
    }

    public DocumentReader(FileInputStream afpDoc) {
        channel = afpDoc.getChannel();
    }

    public StructuredField readIntroducer() throws IOException {
        // The introducer is 5 bytes, but all triplets start with a {0x5a 0x00} so 7 bytes
        ByteBuffer buffer = ByteBuffer.allocate(7);
        channel.read(buffer);
        byte[] introducer = new byte[7];
        buffer.get(introducer);
        if (introducer[0] != 0x5a || introducer[1] != 0x00) {
            throw new IllegalStateException("Triplet at " + channel.position() + " is not valid.");
        }
        return null;
    }

    private StructuredField createStructuredField(byte[] bytes) {
        return null;
    }

}
