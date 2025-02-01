package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;
import net.typho.gallium.instructions.PrimitiveInstruction;

public class PrimitivesToken implements Token {
    @Override
    public Token handle(Line line, String next) {
        line.stack = new PrimitiveInstruction(next);
        return null;
    }
}
