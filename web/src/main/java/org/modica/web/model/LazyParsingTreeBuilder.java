package org.modica.web.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.parser.lazy.LazyAfp;
import org.modica.parser.lazy.LazyAfpCreatingHandler;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class LazyParsingTreeBuilder implements AfpTreeBuilder {

    private static class LazyParsingNode implements SfTreeNode {

        private static final long serialVersionUID = 1L;

        private final SfTreeNode delegate;

        private final LazyAfp lazyAfp;

        public LazyParsingNode(SfTreeNode delegate, LazyAfp lazyAfp) {
            this.delegate = delegate;
            this.lazyAfp = lazyAfp;
        }

        public void attach(FileChannel fileChannel) {
            lazyAfp.attach(fileChannel);
        }

        public void detach() throws IOException {
            lazyAfp.detach();
        }

        @Override
        public StructuredField getField() {
            return delegate.getField();
        }

        @Override
        public List<SfTreeNode> getChilden() {
            return delegate.getChilden();
        }

        @Override
        public List<SfTreeNode> getAll() {
            // TODO Auto-generated method stub
            return delegate.getAll();
        }

    }

    @Override
    public SfTreeNode buildTree(FileInputStream input) throws IOException {
        TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        LazyAfpCreatingHandler lazyAfpCreator = new LazyAfpCreatingHandler(treeBuilder, input,
                streamShutdown);
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input,
                lazyAfpCreator);
        preParser.parse();
        LazyAfp lazyAfp = lazyAfpCreator.getLazyAfp();
        return new LazyParsingNode(treeBuilder.getTree(), lazyAfp);
    }

    @Override
    public void attach(SfTreeNode sfTreeNode, FileInputStream inStream) throws IOException {
        // TODO improve type inference
        ((LazyParsingNode) sfTreeNode).attach(inStream.getChannel());

    }

    @Override
    public void detach(SfTreeNode sfTreeNode) throws IOException {
        // TODO improve type inference
        ((LazyParsingNode) sfTreeNode).detach();
    }
}
