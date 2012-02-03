package org.afpparser.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.parser.AfpParser;
import org.afpparser.parser.StructuredFieldHandler;
import org.afpparser.web.sftree.SfTreeNode;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class AfpTreeBuilder {

    private static class TreeBuildingHandler implements StructuredFieldHandler {

        private final Stack<SfTreeNode> stack;

        private TreeBuildingHandler() {
            stack = new Stack<SfTreeNode>();
            stack.push(new SfTreeNode(null));
        }

        public void handle(StructuredField structuredField) {
            SfTreeNode node = new SfTreeNode(structuredField);
            switch (structuredField.getType().getTypeCode()) {
            case Begin:
                stack.push(node);
                break;
            case End:
                stack.peek().addChild(node);
                SfTreeNode head = stack.pop();
                stack.peek().addChild(head);
                break;
            default:
                stack.peek().addChild(node);
            }
        }

        private SfTreeNode getTree() {
            return stack.pop();
        }
    }

    public SfTreeNode buildTree(File afpDocument) throws IOException {
        FileInputStream inStream = new FileInputStream(afpDocument);
        try {
            TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
            AfpParser.forInput(inStream)
                    .withHandler(treeBuilder)
                    .parse();
            return treeBuilder.getTree();
        } finally {
            inStream.close();
        }
    }
}
