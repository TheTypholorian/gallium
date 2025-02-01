package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;

public class ClassToken implements Token {
    public final Class<?> cls;

    public ClassToken(String cls) throws ClassNotFoundException {
        this(Class.forName(cls));
    }

    public ClassToken(Class<?> cls)  {
        this.cls = cls;
    }

    public Object instance() {
        return null;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "fields":
                return new FieldsToken(this);
            case "methods":
                return new MethodsToken(this);
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_TOKEN_INPUT, line, "Invalid class token input " + next));
                return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + cls + "]";
    }
}
