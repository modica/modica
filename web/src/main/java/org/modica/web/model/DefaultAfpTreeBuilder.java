package org.modica.web.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.AfpParser;
import org.modica.parser.StructuredFieldHandler;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class DefaultAfpTreeBuilder implements AfpTreeBuilder {

    private static class TreeBuildingHandler implements StructuredFieldHandler {

        private final Stack<SfTreeNode> stack;

        private TreeBuildingHandler() {
            stack = new Stack<SfTreeNode>();
        }

        @Override
        public void startAfp() {
            stack.push(new SfTreeNode(null));
        }

        @Override
        public void handleBegin(StructuredField structuredField) {
            stack.push(createNodeFrom(structuredField));
        }

        @Override
        public void handleEnd(StructuredField structuredField) {
            stack.peek().addChild(createNodeFrom(structuredField));
            SfTreeNode head = stack.pop();
            stack.peek().addChild(head);
        }

        @Override
        public void handle(StructuredField structuredField) {
            stack.peek().addChild(createNodeFrom(structuredField));
        }

        @Override
        public void endAfp() {
        }

        private SfTreeNode createNodeFrom(StructuredField structuredField) {
            return new SfTreeNode(structuredField);
        }

        private SfTreeNode getTree() {
            return stack.pop();
        }

    }

    @Override
    public SfTreeNode buildTree(FileInputStream inStream) throws IOException {
        try {
            TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
            AfpParser.forInput(inStream).withHandler(treeBuilder).parse();
            return treeBuilder.getTree();
        } finally {
            inStream.close();
        }
    }
}
