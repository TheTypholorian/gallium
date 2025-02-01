package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;

public class FieldsToken implements Token {
    public final ClassToken cls;

    public FieldsToken(ClassToken cls) {
        this.cls = cls;
    }

    @Override
    public FieldToken handle(Line line, String next) {
        try {
            return new FieldToken(this, next);
        } catch (NoSuchFieldException e) {
            line.parent.error.accept(new ParsingException(ParsingException.Reason.FIELD_NOT_FOUND, line, "Field " + next + " not found in " + cls.cls));
            return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + cls + "]";
    }
}
