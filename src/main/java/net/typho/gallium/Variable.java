package net.typho.gallium;

public class Variable<O> {
    public final String name;
    public O value = null;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(String name, O value) {
        this.name = name;
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public void set(Object o) {
        value = (O) o;
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
