package net.typho.gallium;

public class Variable {
    public VarType type = VarType.OBJECT;
    public Object value = null;

    public Variable() {
    }

    public Variable(Object value) {
        set(value);
    }

    public Token getToken() {
        return type.supplier.apply(value);
    }

    public void set(Object o) {
        value = o;
        type = VarType.identify(o);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
