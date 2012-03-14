package org.modica.web.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.parser.lazy.LazyParser.ProxyCreatingHandler;
import org.modica.parser.lazy.LazyParser.StructuredFieldProxy;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class PartialParseTreeBuilder implements AfpTreeBuilder {

    private static class DetachableNode implements SfTreeNode {

        private static final long serialVersionUID = 1L;

        final SfTreeNode delegate;

        final List<StructuredFieldProxy> fields;

        public DetachableNode(SfTreeNode delegate, List<StructuredFieldProxy> fields) {
            this.delegate = delegate;
            this.fields = fields;
        }

        public void attach(FileChannel fileChannel) {
            for (StructuredFieldProxy p : fields) {
                p.attach(fileChannel);
            }
        }

        public void detach() throws IOException {
            for (StructuredFieldProxy p : fields) {
                p.detach();
            }
        }

        @Override
        public StructuredFieldProxy getField() {
            return (StructuredFieldProxy) delegate.getField();
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
        ProxyCreatingHandler proxyCreator = new ProxyCreatingHandler(treeBuilder, input,
                streamShutdown);
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input,
                proxyCreator);
        preParser.parse();
        List<StructuredFieldProxy> fields = proxyCreator.getFields();
        return new DetachableNode(treeBuilder.getTree(), fields);
    }

    @Override
    public void attach(SfTreeNode sfTreeNode, FileInputStream inStream) throws IOException {
        ((DetachableNode) sfTreeNode).attach(inStream.getChannel());

    }

    @Override
    public void detach(SfTreeNode sfTreeNode) throws IOException {
        ((DetachableNode) sfTreeNode).detach();
    }
}
