package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This service is used to manage the session scoped AfpModel object:
 * It will coordinate the AFP file management and the AFP document building
 *
 */
public class AfpService {

    private IAfpState afpState;

    private AfpTreeBuilder afpTreeBuilder;

    public void load(File afpFile) throws IOException {
        clear();
        openStream(afpFile);
    }

    public SfTreeNode getRootNode() throws IOException {
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode == null) {
            File file = getAfpFile();
            if (file != null) {
                sfTreeNode = buildTree(file);
                setSfTreeNode(sfTreeNode);
            }
        }
        return sfTreeNode;
    }

    private SfTreeNode buildTree(File file) throws IOException {
        return afpTreeBuilder.buildTree(file);
    }

    private void clear() throws IOException {
        SfTreeNode node = getSfTreeNode();
        if (node != null) {
            afpTreeBuilder.detach(node);
        }
        setSfTreeNode(null);
        File previous = getAfpFile();
        if (previous != null) {
            previous.delete();
        }
        setAfpFile(null);
    }

    private void openStream(File afpFile) throws FileNotFoundException {
        setAfpFile(afpFile);
    }

    private File getAfpFile() {
        return afpState.getAfpFile();
    }

    private void setAfpFile(File afpFile) {
        afpState.setAfpFile(afpFile);
    }

    private SfTreeNode getSfTreeNode() {
        return afpState.getSfTreeNode();
    }

    private void setSfTreeNode(SfTreeNode sfTreeNode) {
        afpState.setSfTreeNode(sfTreeNode);
    }

    void beginRequest() throws IOException {
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode != null) {
            afpTreeBuilder.attach(sfTreeNode, new FileInputStream(getAfpFile()));
        }
    }

    void endRequest() throws IOException {
        SfTreeNode sfTreeNode = getSfTreeNode();
        if (sfTreeNode != null) {
            afpTreeBuilder.detach(sfTreeNode);
        }
    }

    public void setAfpTreeBuilder(AfpTreeBuilder afpTreeBuilder) {
        this.afpTreeBuilder = afpTreeBuilder;
    }

    public void setAfpState(IAfpState afpState) {
        this.afpState = afpState;
    }
}
