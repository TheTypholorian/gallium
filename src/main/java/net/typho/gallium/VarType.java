package net.typho.gallium;

import net.typho.gallium.nodes.FieldsNode;
import net.typho.gallium.nodes.ClassNode;
import net.typho.gallium.nodes.FieldNode;

import java.util.function.Function;

public enum VarType {
    CLASS(ClassNode.class, o -> (Token) o),
    FIELD(FieldNode.class, o -> (Token) o),
    FIELDS(FieldsNode.class, o -> (Token) o),
    OBJECT(Object.class, o -> null);

    public final Class<?> check;
    public final Function<Object, Token> supplier;

    VarType(Class<?> check, Function<Object, Token> supplier) {
        this.check = check;
        this.supplier = supplier;
    }

    public static VarType identify(Object value) {
        if (value != null) {
            return identify(value.getClass());
        }

        return OBJECT;
    }

    public static VarType identify(Class<?> cls) {
        for (VarType value : values()) {
            if (value.check.isAssignableFrom(cls)) {
                return value;
            }
        }

        return OBJECT;
    }
}
