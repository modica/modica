package org.modica.web.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.parser.lazy.LazySFCreatingHandler;
import org.modica.parser.lazy.LazyStructuredField;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class LazyParsingTreeBuilder implements AfpTreeBuilder {

    private static class LazyParsingNode implements SfTreeNode {

        private static final long serialVersionUID = 1L;

        private final SfTreeNode delegate;

        private final List<LazyStructuredField> lazyStructuredFields;

        public LazyParsingNode(SfTreeNode delegate, List<LazyStructuredField> lazyStructuredFields) {
            this.delegate = delegate;
            this.lazyStructuredFields = lazyStructuredFields;
        }

        public void attach(FileChannel fileChannel) {
            for (LazyStructuredField sf : lazyStructuredFields) {
                sf.attach(fileChannel);
            }
        }

        public void detach() throws IOException {
            for (LazyStructuredField sf : lazyStructuredFields) {
                sf.detach();
            }
        }

        @Override
        public LazyStructuredField getField() {
            return (LazyStructuredField) delegate.getField();
        }

        @Override
        public List<SfTreeNodeImpl> getChilden() {
            return delegate.getChilden();
        }

    }

    @Override
    public SfTreeNode buildTree(FileInputStream input) throws IOException {
        TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
        final CountDownLatch streamShutdown = new CountDownLatch(1);
        LazySFCreatingHandler lazySFCreator = new LazySFCreatingHandler(treeBuilder, input,
                streamShutdown);
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input,
                lazySFCreator);
        preParser.parse();
        List<LazyStructuredField> lazyStructuredFields = lazySFCreator.getStructuredFields();
        return new LazyParsingNode(treeBuilder.getTree(), lazyStructuredFields);
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
