package net.typho.gallium.nodes;

import net.typho.gallium.*;

public class SetFieldNode implements Node {
    public final FieldNode field;
    public Instruction supplier;

    public SetFieldNode(FieldNode field) {
        this.field = field;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "classes":
            case "vars":
            case "primitive":
                line.queue.add(0, i -> {
                    supplier = i;
                    return this;
                });
                break;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_VAR_SET_INPUT_TOKEN, line, "Invalid var set input token " + next));
                break;
        }

        return null;
    }

    @Override
    public Object invoke() {
        try {
            Gallium.setField(field.field, field.fields.cls.instance(), supplier.invoke());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + field + ", " + supplier + "]";
    }
}
