package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.modica.web.ModicaSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service is used to manage the session scoped AfpModel object:
 * It will coordinate the AFP file management and the AFP document building
 *
 */
public class AfpService {

    private static final Logger LOG = LoggerFactory.getLogger(AfpService.class);

    private DefaultAfpTreeBuilder afpTreeBuilder;

    private final ThreadLocal<FileInputStream> fileInputStore = new ThreadLocal<FileInputStream>();

    public void setAfpFile(File afpFile) throws IOException {
        clear();
        load(afpFile);
    }

    public SfTreeNode getSfTreeNode() {
        SfTreeNode sfTreeNode = ModicaSession.get().getSfTreeNode();
        if (sfTreeNode == null) {
            FileInputStream input = fileInputStore.get();
            if (input != null) {
                sfTreeNode = buildTree(input);
                ModicaSession.get().setSfTreeNode(sfTreeNode);
            }
        }
        return sfTreeNode;
    }

    private SfTreeNode buildTree(FileInputStream input) {
        try {
            return afpTreeBuilder.buildTree(input);
        } catch (IOException e) {
            throw new WicketRuntimeException("Error building afp tree", e);
        }
    }

    private void clear() throws IOException {
        ModicaSession session = ModicaSession.get();
        File previous = session.getAfpFile();
        if (previous != null) {
            previous.delete();
        }
        session.setAfpFile(null);
        session.setSfTreeNode(null);
        FileInputStream input = fileInputStore.get();
        fileInputStore.set(null);
        if (input != null) {
            input.close();
        }
    }

    private void load(File afpFile) throws FileNotFoundException {
        ModicaSession session = ModicaSession.get();
        session.setAfpFile(afpFile);
        fileInputStore.set(new FileInputStream(afpFile));
    }

    void beginSession() throws IOException {
        ModicaSession session = ModicaSession.get();
        File afpFile = session.getAfpFile();
        if (afpFile != null) {
            fileInputStore.set(new FileInputStream(afpFile));
        }

        SfTreeNode sfTreeNode = session.getSfTreeNode();

        if (sfTreeNode != null) {
            afpTreeBuilder.attach(sfTreeNode, fileInputStore.get());
            // attach sfTreeNode
        }
        LOG.debug("beginSession()");
    }

    void endSession() throws IOException {
        ModicaSession session = ModicaSession.get();
        FileInputStream input = fileInputStore.get();
        fileInputStore.set(null);
        if (input != null) {
            input.close();
        }
        SfTreeNode sfTreeNode = session.getSfTreeNode();
        if (sfTreeNode != null) {
            afpTreeBuilder.detach(sfTreeNode);
        }
        LOG.debug("endSession()");
    }

    public void setAfpTreeBuilder(DefaultAfpTreeBuilder afpTreeBuilder) {
        this.afpTreeBuilder = afpTreeBuilder;
    }
}
