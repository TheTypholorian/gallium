package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;
import net.typho.gallium.nodes.PrimitiveNode;

public class PrimitivesToken implements Token {
    @Override
    public Token handle(Line line, String next) {
        PrimitiveNode node = new PrimitiveNode(next);
        line.stack = node;
        return node;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
