package net.typho.gallium.instructions;

import net.typho.gallium.Instruction;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class GetFieldInstruction implements Instruction {
    public final Field field;
    public final Supplier<Object> target;

    public GetFieldInstruction(Field field, Supplier<Object> target) {
        this.field = field;
        field.setAccessible(true);
        this.target = target;
    }

    @Override
    public Object invoke() {
        try {
            return field.get(target.get());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + field + "]";
    }
}
