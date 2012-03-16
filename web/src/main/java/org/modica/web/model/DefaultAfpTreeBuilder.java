package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.modica.parser.AfpParser;

/**
 * Parses an AFP document and creates a tree with structured fields as the nodes of the tree.
 */
public class DefaultAfpTreeBuilder implements AfpTreeBuilder {

    @Override
    public SfTreeNode buildTree(File file) throws IOException {
        FileInputStream inStream = new FileInputStream(file);
        try {
            TreeBuildingHandler treeBuilder = new TreeBuildingHandler();
            AfpParser.forInput(inStream).withHandler(treeBuilder).parse();
            return treeBuilder.getTree();
        } finally {
            inStream.close();
        }
    }

    @Override
    public void attach(SfTreeNode sfTreeNode, FileInputStream input) throws IOException {
        // NOP
    }

    @Override
    public void detach(SfTreeNode sfTreeNode) {
        // NOP
    }
}
