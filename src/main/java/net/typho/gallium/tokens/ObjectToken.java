package net.typho.gallium.tokens;

import java.lang.reflect.Field;

public class ObjectToken extends ClassToken {
    public final Field field;

    public ObjectToken(String cls, Field field) throws ClassNotFoundException {
        super(cls);
        this.field = field;
        field.setAccessible(true);
    }

    public ObjectToken(Class<?> cls, Field field) {
        super(cls);
        field.setAccessible(true);
        this.field = field;
    }

    @Override
    public Object instance() {
        try {
            return field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
