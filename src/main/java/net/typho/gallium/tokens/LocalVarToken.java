package net.typho.gallium.tokens;

import net.typho.gallium.Line;
import net.typho.gallium.ParsingException;
import net.typho.gallium.Token;
import net.typho.gallium.Variable;
import net.typho.gallium.instructions.GetLocalVarInstruction;
import net.typho.gallium.nodes.SetLocalNode;

public class LocalVarToken implements Token {
    public final Variable<?> var;

    public LocalVarToken(Variable<?> var) {
        this.var = var;
    }

    @Override
    public Token handle(Line line, String next) {
        return switch (next) {
            case "get" -> {
                line.stack = new GetLocalVarInstruction(var);
                yield null;
            }
            case "set" -> {
                SetLocalNode token = new SetLocalNode(this);
                line.insn.add(token);
                yield token;
            }
            default -> {
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_LOCAL_VAR_INPUT_TOKEN, line, "Invalid local var input token " + next));
                yield null;
            }
        };
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + "]";
    }
}
