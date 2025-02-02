package net.typho.gallium;

public interface Instruction {
    Object invoke();

    default VarType type() {
        return VarType.identify(this);
    }
}
