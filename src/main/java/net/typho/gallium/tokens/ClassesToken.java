package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;

public class ClassesToken implements Token {
    @Override
    public ClassToken handle(Line line, String next) {
        try {
            return new ClassToken(next);
        } catch (ClassNotFoundException e) {
            line.parent.error.accept(new ParsingException(ParsingException.Reason.CLASS_NOT_FOUND, line, "Class " + next + " not found"));
            return null;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
