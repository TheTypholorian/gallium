package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Node;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;
import net.typho.gallium.nodes.ClassNode;

public class ClassesToken implements Token {
    @Override
    public ClassNode handle(Line line, String next) {
        try {
            ClassNode node = new ClassNode(next);
            line.stack = node;
            return node;
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
