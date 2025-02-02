package net.typho.gallium.nodes;

import net.typho.gallium.Line;
import net.typho.gallium.Node;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;

public class ClassNode implements Node {
    public final Class<?> cls;

    public ClassNode(String cls) throws ClassNotFoundException {
        this(Class.forName(cls));
    }

    public ClassNode(Class<?> cls)  {
        this.cls = cls;
    }

    public Object instance() {
        return null;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "fields":
                FieldsNode node = new FieldsNode(this);
                line.stack = node;
                return node;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_TOKEN_INPUT, line, "Invalid class token input " + next));
                return null;
        }
    }

    @Override
    public Object invoke() {
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + cls + "]";
    }
}
