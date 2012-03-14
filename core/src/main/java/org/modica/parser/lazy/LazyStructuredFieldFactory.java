package org.modica.parser.lazy;

import java.nio.channels.FileChannel;
import java.util.Stack;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.MODCAContext;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.StructuredFieldFactory;
import org.modica.afp.modca.StructuredFieldFactoryImpl;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

public class LazyStructuredFieldFactory implements StructuredFieldFactory {
    
    private final StructuredFieldFactory delegate;
    
    private final ContextStack contextStack;
    
    private final CreationListener creationListener;
    
    public LazyStructuredFieldFactory(FileChannel fileChannel, CreationListener creationListener) {
        this.contextStack = new ContextStack();
        this.creationListener = creationListener;
        this.delegate = new StructuredFieldFactoryImpl(fileChannel, contextStack);
        
    }
    
    public static interface CreationListener {
        void created(StructuredFieldIntroducer introducer, Context context);
    }
    
    private void beforeCreation(StructuredFieldIntroducer introducer) {
        pushContext();
        onCreation(introducer);
    }
    
    private void onCreation(StructuredFieldIntroducer introducer) {
        creationListener.created(introducer, contextStack.getLast());
    }
    
    private static class ContextStack implements Context {
        
        private Stack<ContextRecorder> stack;
        
        ContextStack() {
            stack = new Stack<ContextRecorder>();
            stack.push(new ContextRecorder(new ContextImpl(), null));
        }
        
        public void push(Context context) {
            stack.push(new ContextRecorder(context, getLast()));
        }
        
        private ContextRecorder getLast() {
            return stack.peek();
        }

        @Override
        public void put(FOCAContext focaContext, Object obj) {
            getLast().put(focaContext, obj);
        }

        @Override
        public void put(MODCAContext modcaContext, Object obj) {
            getLast().put(modcaContext, obj);
        }

        @Override
        public Object get(FOCAContext focaContext) {
            return getLast().get(focaContext);
        }

        @Override
        public Object get(MODCAContext modcaContext) {
            return getLast().get(modcaContext);
        }
    }
    
    
    private static class ContextRecorder implements Context {
        
        private final Context current;
        
        private final ContextRecorder previous;
        
        public ContextRecorder(Context first, ContextRecorder previous) {
            this.current = first;
            this.previous = previous;
        }
        
        @Override
        public Object get(FOCAContext focaContext) {
            Object ob = current.get(focaContext);
            if (ob == null && previous != null) {
                ob = previous.get(focaContext);
                if (ob != null) {
                    current.put(focaContext, ob);
                }
            }
            return ob;
        }

        @Override
        public Object get(MODCAContext modcaContext) {
            Object ob = current.get(modcaContext);
            if (ob == null) {
                ob = previous.get(modcaContext);
                if (ob != null && previous != null) {
                    current.put(modcaContext, ob);
                }
            }
            return ob;
        }

        @Override
        public void put(FOCAContext focaContext, Object obj) {
            current.put(focaContext, obj);
        }

        @Override
        public void put(MODCAContext modcaContext, Object obj) {
            current.put(modcaContext, obj);
        }
    }
    
    private void pushContext() {
        Context next = new ContextImpl();
        next.put(MODCAContext.GCSGID, null);
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

}
