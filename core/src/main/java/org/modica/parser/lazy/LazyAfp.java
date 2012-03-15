package org.modica.parser.lazy;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;

public class LazyAfp {
    
    private final List<StructuredField> lazyStructuredFields;
    
    private final FileChannelProvider fileChannelProvider;
    
    public LazyAfp(List<StructuredField> lazyStructuredFields,
            FileChannelProvider fileChannelProvider) {
        this.lazyStructuredFields = lazyStructuredFields;
        this.fileChannelProvider = fileChannelProvider;
    }
    
    public List<StructuredField> getStructuredFields() {
        return Collections.unmodifiableList(lazyStructuredFields);
    }
    
    public void attach(FileChannel fileChannel) {
        fileChannelProvider.fileChannel = fileChannel;
    }

    public void detach() throws IOException {
        fileChannelProvider.fileChannel = null;
    }
    
    public static class FileChannelProvider {
        
        private FileChannel fileChannel;

        FileChannelProvider(FileChannel fileChannel) {
            this.fileChannel = fileChannel;
        }
        
        public FileChannel getFileChannel() {
            return fileChannel;
        }
    }
    
}
