package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;
import net.typho.gallium.Variable;
import net.typho.gallium.nodes.LocalVarNode;

public class LocalVarsToken implements Token {
    @Override
    public LocalVarNode handle(Line line, String next) {
        LocalVarNode token = new LocalVarNode(line.parent.vars.computeIfAbsent(next, k -> new Variable()));
        line.stack = token;
        return token;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
