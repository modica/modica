package org.modica.web.model;

import java.util.Stack;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.parser.StructuredFieldHandler;

class TreeBuildingHandler implements StructuredFieldHandler {

    private final Stack<SfTreeNodeImpl> stack;

    private final NodeMaker nodeMaker;

    public TreeBuildingHandler() {
        this(new NodeMaker() {
            @Override
            public SfTreeNodeImpl make(StructuredField structuredField) {
                return new SfTreeNodeImpl(structuredField);
            }
        });
    }

    public TreeBuildingHandler(NodeMaker nodeMaker) {
        this.nodeMaker = nodeMaker;
        stack = new Stack<SfTreeNodeImpl>();
    }

    public SfTreeNodeImpl getTree() {
        return stack.pop();
    }

    @Override
    public void startAfp() {
        stack.push(createNodeFrom(null));
    }

    @Override
    public void handleBegin(StructuredField structuredField) {
        stack.push(createNodeFrom(structuredField));
    }

    @Override
    public void handleEnd(StructuredField structuredField) {
        stack.peek().addChild(createNodeFrom(structuredField));
        SfTreeNodeImpl head = stack.pop();
        stack.peek().addChild(head);
    }

    @Override
    public void handle(StructuredField structuredField) {
        stack.peek().addChild(createNodeFrom(structuredField));
    }

    @Override
    public void endAfp() {
    }

    private SfTreeNodeImpl createNodeFrom(StructuredField structuredField) {
        return nodeMaker.make(structuredField);
    }

    public static interface NodeMaker {
        public SfTreeNodeImpl make(StructuredField structuredField);
    }

}