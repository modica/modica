package org.modica.parser.lazy;

import java.nio.channels.FileChannel;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor;

public class LazyStructuredFieldFactory implements StructuredFieldFactory {

    private final StructuredFieldFactory delegate;

    private final ContextStack contextStack;

    private Context previousContext;

    public LazyStructuredFieldFactory(FileChannel fileChannel) {
        this.contextStack = new ContextStack(new ContextImpl(), null);
        previousContext = contextStack;
        this.delegate = new StructuredFieldFactoryImpl(fileChannel, contextStack);
    }

    public Context getPreviousContext() {
        return previousContext;
    }

    private void beforeCreation(StructuredFieldIntroducer introducer) {
        previousContext = contextStack;
        pushContext();
    }

    private void pushContext() {
        Context next = new ContextImpl();
        next.put(ContextType.MODCA_GCSGID, null);
        contextStack.push(next);
    }

    @Override
    public StructuredField createBegin(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createBegin(introducer);
    }

    @Override
    public StructuredField createMap(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createMap(introducer);
    }

    @Override
    public StructuredField createDescriptor(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createDescriptor(introducer);
    }

    @Override
    public StructuredField createMigration(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createMigration(introducer);
    }

    @Override
    public StructuredField createEnd(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createEnd(introducer);
    }

    @Override
    public StructuredField createData(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createData(introducer);
    }

    @Override
    public StructuredField createPosition(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createPosition(introducer);
    }

    @Override
    public StructuredField createInclude(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createInclude(introducer);
    }

    @Override
    public StructuredField createControl(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createControl(introducer);
    }

    @Override
    public StructuredField createIndex(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createIndex(introducer);
    }

    @Override
    public StructuredField createAttribute(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createAttribute(introducer);
    }

    @Override
    public StructuredField createCopyCount(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createCopyCount(introducer);
    }

    @Override
    public StructuredField createProcess(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createProcess(introducer);
    }

    @Override
    public StructuredField createOrientation(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createOrientation(introducer);
    }

    @Override
    public StructuredField createTable(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createTable(introducer);
    }

    @Override
    public StructuredField createVariable(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createVariable(introducer);
    }

    @Override
    public StructuredField createLink(StructuredFieldIntroducer introducer) {
        beforeCreation(introducer);
        return delegate.createLink(introducer);
    }

    private static class ContextStack implements Context {

        private final Context current;

        private final ContextStack previous;

        public ContextStack(Context first, ContextStack previous) {
            this.current = first;
            this.previous = previous;
        }

        public ContextStack push(Context context) {
            return new ContextStack(context, this);
        }

        @Override
        public Object get(ContextType contextObj) {
            Object ob = current.get(contextObj);
            if (ob == null && previous != null) {
                ob = previous.get(contextObj);
                if (ob != null) {
                    current.put(contextObj, ob);
                }
            }
            return ob;
        }

        @Override
        public void put(ContextType contextObj, Object obj) {
            current.put(contextObj, obj);
        }

        @Override
        public void setCpgidForCodePage(CodePageDescriptor descriptor) {
        }

        @Override
        public void setCurrentCodePageName(String name) {
        }

        @Override
        public int getPTXEncoding() {
            return EbcdicStringHandler.DEFAULT_CPGID;
        }

        @Override
        public void endCodePage() {
        }
    }
}
