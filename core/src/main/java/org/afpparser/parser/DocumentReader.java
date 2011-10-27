package org.afpparser.parser;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import org.afpparser.afp.modca.StructuredField;
import org.afpparser.common.ByteUtils;

public class DocumentReader implements Iterable<StructuredField> {

    private final FileChannel channel;

    private final ByteUtils byteUtils;

    private static final byte CARRIAGE_CONTROL = 0x5a;

    private final SfIterator sfIterator;

    public DocumentReader(File afpDoc) throws FileNotFoundException {
        this(new FileInputStream(afpDoc));
    }

    public DocumentReader(FileInputStream afpDoc) {
        channel = afpDoc.getChannel();
        byteUtils = ByteUtils.newLittleEndianUtils();
        sfIterator = new SfIterator();
    }

    private final boolean hasStructuredField() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        channel.read(buffer);
        byte[] fiveA = buffer.array();
        return fiveA[0] == CARRIAGE_CONTROL;
    }

    private StructuredField createStructuredField() {
        try {
            if (!hasStructuredField()) {
                return null;
            }
            long offset = channel.position();
            ByteBuffer buffer = ByteBuffer.allocate(7);
            channel.read(buffer);
            byte[] introducer = buffer.array();
            int sfLength = byteUtils.bytesToUnsignedInt(introducer, 0, 2);
            byte[] id = new byte[3];
            System.arraycopy(introducer, 2, id, 0, 3);
            byte flags = introducer[5];
            int extLength = 0;
            if (StructuredField.hasSfiExtension(flags)) {
                buffer = ByteBuffer.allocate(1);
                channel.read(buffer);
                extLength = byteUtils.bytesToUnsignedInt(buffer.array());
            }
            StructuredField sf = new StructuredField(offset, sfLength, id, flags, extLength);
            channel.position(offset += sf.bytesToNextStructuredField());
            return sf;
        } catch (EOFException eof) {
            return null;
        } catch (IOException ioe) {
            throw new InvalidAfpException("This document is invalid.", ioe);
        }
    }

    public Iterator<StructuredField> iterator() {
        return sfIterator;
    }

    private final class SfIterator implements Iterator<StructuredField> {

        private StructuredField nextSf;

        private SfIterator() {
            nextSf = createStructuredField();
        }

        public boolean hasNext() {
            return nextSf != null;
        }

        public StructuredField next() {
            StructuredField sf = nextSf;
            nextSf = createStructuredField();
            return sf;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() not supported");
        }

    }

}
