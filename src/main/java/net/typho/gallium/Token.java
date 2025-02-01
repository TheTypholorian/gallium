package net.typho.gallium;

import net.typho.gallium.tokens.ClassesToken;
import net.typho.gallium.nodes.PrintNode;
import net.typho.gallium.tokens.PrimitivesToken;
import net.typho.gallium.tokens.VarsToken;

public interface Token {
    static Token parse(Line line, String next) {
        return switch (next) {
            case "classes" -> new ClassesToken();
            case "vars" -> new VarsToken();
            case "primitive" -> new PrimitivesToken();
            case "print" -> {
                PrintNode token = new PrintNode(line.parent.console);
                line.insn.add(token);
                yield  token;
            }
            default -> {
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_LINE_START, line, "Invalid line start " + next));
                yield null;
            }
        };
    }

    Token handle(Line line, String next);
}
