package org.modica.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service is used to manage the session scoped AfpModel object:
 * It will coordinate the AFP file management and the AFP document building
 *
 */
public class AfpService {

    private static final Logger LOG = LoggerFactory.getLogger(AfpService.class);

    private AfpTreeBuilder afpTreeBuilder;

    private final ThreadLocal<FileInputStream> fileChannelStore = new ThreadLocal<FileInputStream>();

    private final ThreadLocal<SfTreeNode> sfTreeNodeStore = new ThreadLocal<SfTreeNode>();

    public void startSession(File afpFile) throws FileNotFoundException {
        if (afpFile != null) {
            loadDocument(afpFile);
        }
    }

    public void loadDocument(File afpFile) throws FileNotFoundException {
        fileChannelStore.set(new FileInputStream(afpFile));
        sfTreeNodeStore.set(null);
    }

    public SfTreeNode getSfTreeNode() {
        SfTreeNode sfTreeNode = sfTreeNodeStore.get();
        if (sfTreeNode == null) {
            FileInputStream input = fileChannelStore.get();
            if (input != null) {
                try {
                    sfTreeNode = afpTreeBuilder.buildTree(fileChannelStore.get());
                    sfTreeNodeStore.set(sfTreeNode);
                } catch (IOException e) {
                    throw new WicketRuntimeException("Error building afp tree", e);
                }
            }
        }
        return sfTreeNode;
    }

    public void endSession() throws IOException {
        FileInputStream input = fileChannelStore.get();
        fileChannelStore.set(null);
        sfTreeNodeStore.set(null);
        if (input != null) {
            input.close();
        }
        LOG.debug("end afp session");
    }

    public void setAfpTreeBuilder(AfpTreeBuilder afpTreeBuilder) {
        this.afpTreeBuilder = afpTreeBuilder;
    }
}
