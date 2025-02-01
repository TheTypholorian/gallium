package net.typho.gallium.nodes;

import net.typho.gallium.*;
import net.typho.gallium.tokens.LocalVarToken;

public class SetLocalNode implements Node {
    public final LocalVarToken var;
    public Instruction supplier;

    public SetLocalNode(LocalVarToken var) {
        this.var = var;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "classes", "vars", "primitive":
                line.queue.add(0, i -> {
                    supplier = i;
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
    public String toString() {
        return getClass().getSimpleName() + "[" + var + ", " + supplier + "]";
    }
}
