package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;

public class MethodToken implements Token {
    public final MethodsToken methods;
    public final String name;

    public MethodToken(MethodsToken methods, String name) {
        this.methods = methods;
        this.name = name;
    }

    @Override
    public Token handle(Line line, String next) {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + methods + ", " + name + "]";
    }
}
