package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.Token;

public class MethodsToken implements Token {
    public final ClassToken cls;

    public MethodsToken(ClassToken cls) {
        this.cls = cls;
    }

    @Override
    public MethodToken handle(Line line, String next) {
        return new MethodToken(this, next);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + cls + "]";
    }
}
