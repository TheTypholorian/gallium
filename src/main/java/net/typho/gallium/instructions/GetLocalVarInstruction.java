package net.typho.gallium.instructions;

import net.typho.gallium.Instruction;
import net.typho.gallium.Variable;

public class GetLocalVarInstruction implements Instruction {
    public final Variable<?> var;

    public GetLocalVarInstruction(Variable<?> var) {
        this.var = var;
    }

    @Override
    public Object invoke() {
        return var.value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + "]";
    }
}
