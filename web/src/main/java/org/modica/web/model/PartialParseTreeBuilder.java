package org.modica.web.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.parser.StructuredFieldIntroducerHandler;
import org.modica.parser.StructuredFieldIntroducerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class PartialParseTreeBuilder implements AfpTreeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(PartialParseTreeBuilder.class);

    private static class Root extends SfTreeNode {

        private static final long serialVersionUID = 1L;

        private final ReusableStructuredFieldFactory factory;

        public Root(ReusableStructuredFieldFactory factory) {
            super(null);
            this.factory = factory;
        }

        public void attach(FileChannel fileChannel) {
            factory.attach(fileChannel);
        }

        public void detach() throws IOException {
            factory.detach();
        }

    }

    private static class ReusableStructuredFieldFactory implements StructuredFieldFactory {

        private StructuredFieldFactory factory;

        private FileChannel fileChannel;

        ReusableStructuredFieldFactory(FileChannel fileChannel) {
            attach(fileChannel);
        }

        private void attach(FileChannel fileChannel) {
            this.factory = new StructuredFieldFactoryImpl(fileChannel);
            this.fileChannel = fileChannel;
        }

        private void detach() throws IOException {
            factory = null;
            fileChannel.close();
        }

        @Override
        public StructuredField createBegin(StructuredFieldIntroducer introducer) {
            return factory.createBegin(introducer);
        }

        @Override
        public StructuredField createMap(StructuredFieldIntroducer introducer) {
            return factory.createMap(introducer);
        }

        @Override
        public StructuredField createDescriptor(StructuredFieldIntroducer introducer) {
            return factory.createDescriptor(introducer);
        }

        @Override
        public StructuredField createMigration(StructuredFieldIntroducer introducer) {
            return factory.createMigration(introducer);
        }

        @Override
        public StructuredField createEnd(StructuredFieldIntroducer introducer) {
            return factory.createEnd(introducer);
        }

        @Override
        public StructuredField createData(StructuredFieldIntroducer introducer) {
            return factory.createData(introducer);
        }

        @Override
        public StructuredField createPosition(StructuredFieldIntroducer introducer) {
            return factory.createPosition(introducer);
        }

        @Override
        public StructuredField createInclude(StructuredFieldIntroducer introducer) {
            return factory.createInclude(introducer);
        }

        @Override
        public StructuredField createControl(StructuredFieldIntroducer introducer) {
            return factory.createControl(introducer);
        }

        @Override
        public StructuredField createIndex(StructuredFieldIntroducer introducer) {
            return factory.createIndex(introducer);
        }

    }

    private static class StructuredFieldProxy extends AbstractStructuredField {

        private final StructuredFieldFactory factory;

        private StructuredField structuredField;

        private final StructuredFieldIntroducer introducer;

        public StructuredFieldProxy(StructuredFieldIntroducer introducer,
                StructuredFieldFactory factory) {
            super(introducer);
            this.introducer = introducer;
            this.factory = factory;
        }

        @Override
        public List<ParameterAsString> getParameters() {
            if (structuredField == null) {
                LOG.debug("Full parse of introducer " + introducer);
                switch (getType().getTypeCode()) {
                case Begin:
                    structuredField = factory.createBegin(introducer);
                case End:
                    structuredField = factory.createEnd(introducer);
                case Map:
                    structuredField = factory.createMap(introducer);
                    break;
                case Descriptor:
                    structuredField = factory.createDescriptor(introducer);
                    break;
                case Migration:
                    structuredField = factory.createMigration(introducer);
                    break;
                case Data:
                    structuredField = factory.createData(introducer);
                    break;
                case Position:
                    structuredField = factory.createPosition(introducer);
                    break;
                case Include:
                    structuredField = factory.createInclude(introducer);
                    break;
                case Control:
                    structuredField = factory.createControl(introducer);
                    break;
                case Index:
                    structuredField = factory.createIndex(introducer);
                    break;
                default:
                    structuredField = null;
                }
            }
            return (structuredField == null) ? (List<ParameterAsString>) Collections.EMPTY_LIST
                    : structuredField.getParameters();
        }

    }

    private static class TreeBuildingHandler implements StructuredFieldIntroducerHandler {

        private final Stack<SfTreeNode> stack;

        private final ReusableStructuredFieldFactory factory;

        private TreeBuildingHandler(ReusableStructuredFieldFactory factory) {
            stack = new Stack<SfTreeNode>();
            this.factory = factory;

        }

        @Override
        public void startAfp() {
            stack.push(new Root(factory));
        }

        private SfTreeNode createNodeFrom(StructuredFieldIntroducer introducer) {
            return new SfTreeNode(new StructuredFieldProxy(introducer, factory));
        }

        @Override
        public void handleBegin(StructuredFieldIntroducer introducer) {
            stack.push(createNodeFrom(introducer));

        }

        @Override
        public void handleEnd(StructuredFieldIntroducer introducer) {
            stack.peek().addChild(createNodeFrom(introducer));
            SfTreeNode head = stack.pop();
            stack.peek().addChild(head);

        }

        @Override
        public void handle(StructuredFieldIntroducer introducer) {
            stack.peek().addChild(createNodeFrom(introducer));
        }

        @Override
        public void endAfp() {
        }

        public SfTreeNode getTree() {
            return stack.pop();
        }

    }

    @Override
    public SfTreeNode buildTree(FileInputStream inStream) throws IOException {
        ReusableStructuredFieldFactory factory = new ReusableStructuredFieldFactory(
                inStream.getChannel());
        TreeBuildingHandler treeBuilder = new TreeBuildingHandler(factory);
        new StructuredFieldIntroducerParser(inStream, treeBuilder).parse();
        return treeBuilder.getTree();
    }

    @Override
    public void attach(SfTreeNode sfTreeNode, FileInputStream inStream) throws IOException {
        ((Root) sfTreeNode).attach(inStream.getChannel());

    }

    @Override
    public void detach(SfTreeNode sfTreeNode) throws IOException {
        ((Root) sfTreeNode).detach();
    }
}
