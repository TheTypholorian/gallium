package net.typho.gallium.insn;

import net.typho.gallium.Instruction;
import net.typho.gallium.VarType;
import net.typho.gallium.Variable;

public class GetLocalVarInstruction implements Instruction {
    public final Variable var;

    public GetLocalVarInstruction(Variable var) {
        this.var = var;
    }

    @Override
    public Object invoke() {
        return var.value;
    }

    @Override
    public VarType type() {
        return var.type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + var + "]";
    }
}
