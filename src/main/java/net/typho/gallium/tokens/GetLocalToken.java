package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;

public class GetLocalToken implements Token {
    public final LocalVarToken var;

    public GetLocalToken(LocalVarToken var) {
        this.var = var;
    }

    @Override
    public Token handle(Line line, String next) {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + "]";
    }
}
