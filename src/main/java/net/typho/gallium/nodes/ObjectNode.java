package net.typho.gallium.nodes;

import java.lang.reflect.Field;
import java.util.Objects;

public class ObjectNode extends ClassNode {
    public final Field field;

    public ObjectNode(String cls, Field field) throws ClassNotFoundException {
        super(cls);
        this.field = field;
        field.setAccessible(true);
    }

    public ObjectNode(Class<?> cls, Field field) {
        super(cls);
        this.field = field;
        field.setAccessible(true);
    }

    @Override
    public Object instance() {
        try {
            return Objects.requireNonNull(field.get(null), field + " is null");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + cls + ", " + field + "]";
    }
}
