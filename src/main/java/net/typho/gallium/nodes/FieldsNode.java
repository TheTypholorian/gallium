package net.typho.gallium.nodes;

import net.typho.gallium.*;

public class FieldsNode implements Node {
    public final ClassNode cls;

    public FieldsNode(ClassNode cls) {
        this.cls = cls;
    }

    @Override
    public FieldNode handle(Line line, String next) {
        try {
            FieldNode token = new FieldNode(this, next);
            line.stack = token;
            return token;
        } catch (NoSuchFieldException e) {
            line.parent.error.accept(new ParsingException(ParsingException.Reason.FIELD_NOT_FOUND, line, "Field " + next + " not found in " + cls.cls));
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
