package net.typho.gallium.nodes;

import net.typho.gallium.*;

public class SetLocalNode implements Node {
    public final LocalVarNode var;
    public Instruction supplier;

    public SetLocalNode(LocalVarNode var) {
        this.var = var;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "classes":
            case "vars":
            case "primitive":
                line.queue.add(0, i -> {
                    supplier = i;

                    if (i != null) {
                        var.var.type = i.type();
                        var.var.value = i.type().supplier.apply(i);
                    }
                    return this;
                });
                break;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_LOCAL_VAR_SET_INPUT_TOKEN, line, "Invalid local var set input token " + next));
                break;
        }

        return null;
    }

    @Override
    public Object invoke() {
        var.var.set(supplier.invoke());
        return null;
    }

    @Override
    public VarType type() {
        return var.var.type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + ", " + supplier + "]";
    }
}
