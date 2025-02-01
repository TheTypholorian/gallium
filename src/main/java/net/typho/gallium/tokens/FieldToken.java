package net.typho.gallium.tokens;

import net.typho.gallium.*;
import net.typho.gallium.instructions.GetFieldInstruction;
import net.typho.gallium.nodes.SetFieldNode;

import java.lang.reflect.Field;

public class FieldToken implements Token {
    public final FieldsToken fields;
    public final Field field;

    public FieldToken(FieldsToken fields, String name) throws NoSuchFieldException {
        this.fields = fields;
        field = fields.cls.cls.getField(name);
        field.setAccessible(true);
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "get" -> {
                line.stack = new GetFieldInstruction(field, fields.cls::instance);
                return new ObjectToken(field.getType(), field);
            }
            case "set" -> {
                SetFieldNode token = new SetFieldNode(this);
                line.insn.add(token);
                return token;
            }
            default ->
                    line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_TOKEN_INPUT, line, "Invalid field token input " + next));
        }

        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + fields + ", " + field + "]";
    }
}
