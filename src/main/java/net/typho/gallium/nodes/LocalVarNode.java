package net.typho.gallium.nodes;

import net.typho.gallium.*;
import net.typho.gallium.insn.GetLocalVarInstruction;

public class LocalVarNode implements Node {
    public final Variable var;

    public LocalVarNode(Variable var) {
        this.var = var;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "get":
                line.stack = new GetLocalVarInstruction(var);
                return var.getToken();
            case "set":
                SetLocalNode token = new SetLocalNode(this);
                line.insn.add(token);
                return token;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_LOCAL_VAR_INPUT_TOKEN, line, "Invalid local var input token " + next));
                return null;
        }
    }

    @Override
    public Object invoke() {
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + "]";
    }
}
