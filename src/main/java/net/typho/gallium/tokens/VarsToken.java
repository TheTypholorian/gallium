package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;
import net.typho.gallium.Variable;

public class VarsToken implements Token {
    @Override
    public LocalVarToken handle(Line line, String next) {
        return new LocalVarToken(line.parent.vars.computeIfAbsent(next, Variable::new));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
