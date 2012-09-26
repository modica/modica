package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.StructuredFieldIntroducerParser;
import org.modica.parser.lazy.LazyAfp;
import org.modica.parser.lazy.LazyAfpCreatingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class LazyParsingTreeBuilder implements AfpTreeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(LazyParsingTreeBuilder.class);

    private static class LazyParsingNode implements SfTreeNode {

        private static final long serialVersionUID = 1L;

        private final SfTreeNode delegate;

        private final LazyAfp lazyAfp;

        private FileInputStream fileInputStream;

        public LazyParsingNode(SfTreeNode delegate, LazyAfp lazyAfp) {
            this.delegate = delegate;
            this.lazyAfp = lazyAfp;
        }

        public void attach(FileInputStream fileInputStream) {
            this.fileInputStream = fileInputStream;
            lazyAfp.attach(fileInputStream.getChannel());
        }

        public void detach() throws IOException {
            lazyAfp.detach();
            FileInputStream in = fileInputStream;
            fileInputStream = null;
            if (in != null) {
                in.close();
            }
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
            return delegate.getAll();
        }

    }

    @Override
    public SfTreeNode buildTree(File file) throws IOException {
        final FileInputStream input = new FileInputStream(file);
        TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
        LazyAfpCreatingHandler lazyAfpCreator = new LazyAfpCreatingHandler(treeBuilder, input);
        StructuredFieldIntroducerParser preParser = new StructuredFieldIntroducerParser(input,
                lazyAfpCreator);
        final long t = System.currentTimeMillis();
        preParser.parse();
        LOG.debug("Introducers parsed in " + (System.currentTimeMillis() - t) + "ms");
        final LazyAfp lazyAfp = lazyAfpCreator.getLazyAfp();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lazyAfp.await();
                LOG.debug("SF contexts resolved in " + (System.currentTimeMillis() - t) + "ms");
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        SfTreeNode sfTreeNode = new LazyParsingNode(treeBuilder.getTree(), lazyAfp);
        attach(sfTreeNode, new FileInputStream(file));
        return sfTreeNode;
    }

    @Override
    public void attach(SfTreeNode sfTreeNode, FileInputStream fileInputStream) throws IOException {
        // TODO improve type inference
        ((LazyParsingNode) sfTreeNode).attach(fileInputStream);

    }

    @Override
    public void detach(SfTreeNode sfTreeNode) throws IOException {
        // TODO improve type inference
        ((LazyParsingNode) sfTreeNode).detach();
    }
}
