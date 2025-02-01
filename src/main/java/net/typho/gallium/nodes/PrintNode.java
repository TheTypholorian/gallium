package net.typho.gallium.nodes;

import net.typho.gallium.*;
import net.typho.gallium.instructions.PrimitiveInstruction;

import java.util.function.Consumer;

public class PrintNode implements Node {
    public final Consumer<String> console;
    public Instruction supplier;

    public PrintNode(Consumer<String> console) {
        this.console = console;
    }

    @Override
    public Token handle(Line line, String next) {
        switch (next) {
            case "classes":
            case "vars":
            case "primitive":
                line.queue.add(0, i -> {
                    supplier = i;
                    return this;
                });
                break;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_PRINT_INPUT_TOKEN, line, "Invalid print input token " + next));
                break;
        }

        return null;
    }

    @Override
    public Object invoke() {
        console.accept(String.valueOf(supplier.invoke()));

        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + supplier + "]";
    }
}
