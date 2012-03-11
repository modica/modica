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

    private AfpTreeBuilder afpTreeBuilder;

    private final ThreadLocal<FileInputStream> fileChannelStore = new ThreadLocal<FileInputStream>();

    private final ThreadLocal<SfTreeNode> sfTreeNodeStore = new ThreadLocal<SfTreeNode>();

    public void setAfpFile(File afpFile) throws FileNotFoundException {
        ModicaSession session = ModicaSession.get();
        File previous = session.getAfpFile();
        if (previous != null) {
            previous.delete();
        }
        session.setAfpFile(afpFile);
        load(afpFile);
    }

    public SfTreeNode getSfTreeNode() {
        SfTreeNode sfTreeNode = sfTreeNodeStore.get();
        if (sfTreeNode == null) {
            FileInputStream input = fileChannelStore.get();
            if (input != null) {
                try {
                    sfTreeNode = afpTreeBuilder.buildTree(input);
                    sfTreeNodeStore.set(sfTreeNode);
                } catch (IOException e) {
                    throw new WicketRuntimeException("Error building afp tree", e);
                }
            }
        }
        return sfTreeNode;
    }

    private void load(File afpFile) throws FileNotFoundException {
        fileChannelStore.set(new FileInputStream(afpFile));
        sfTreeNodeStore.set(null);
    }

    public void beginSession() throws FileNotFoundException {
        ModicaSession session = ModicaSession.get();
        if (session != null) {
            File afpFile = session.getAfpFile();
            if (afpFile != null) {
                try {
                    load(afpFile);
                } catch (FileNotFoundException e) {
                    throw new WicketRuntimeException(e);
                }
            }
        }
        LOG.debug("beginSession()");
    }

    public void endSession() throws IOException {
        FileInputStream input = fileChannelStore.get();
        fileChannelStore.set(null);
        sfTreeNodeStore.set(null);
        if (input != null) {
            input.close();
        }
        LOG.debug("endSession()");
    }

    public void setAfpTreeBuilder(AfpTreeBuilder afpTreeBuilder) {
        this.afpTreeBuilder = afpTreeBuilder;
    }
}
