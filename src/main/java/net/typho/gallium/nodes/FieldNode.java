package net.typho.gallium.nodes;

import net.typho.gallium.*;
import net.typho.gallium.insn.GetFieldInstruction;

import java.lang.reflect.Field;

public class FieldNode implements Node {
    public final FieldsNode fields;
    public final Field field;

    public FieldNode(FieldsNode fields, String name) throws NoSuchFieldException {
        this.fields = fields;
        field = fields.cls.cls.getField(name);
        field.setAccessible(true);
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "get":
                line.stack = new GetFieldInstruction(field, fields.cls::instance);
                return new ObjectNode(field.getType(), field);
            case "set":
                SetFieldNode token = new SetFieldNode(this);
                line.insn.add(token);
                return token;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_TOKEN_INPUT, line, "Invalid field token input " + next));
        }

        return null;
    }

    @Override
    public Object invoke() {
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + fields + ", " + field + "]";
    }
}
