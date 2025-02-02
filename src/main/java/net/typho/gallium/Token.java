package net.typho.gallium;

import net.typho.gallium.tokens.ClassesToken;
import net.typho.gallium.nodes.PrintNode;
import net.typho.gallium.tokens.PrimitivesToken;
import net.typho.gallium.tokens.LocalVarsToken;

public interface Token {
    static Token parse(Line line, String next) {
        switch (next) {
            case "classes":
                return new ClassesToken();
            case "vars":
                return new LocalVarsToken();
            case "primitive":
                return new PrimitivesToken();
            case "print":
                PrintNode token = new PrintNode(line.parent.console);
                line.insn.add(token);
                return token;
            default:
                line.parent.error.accept(new ParsingException(ParsingException.Reason.BAD_LINE_START, line, "Invalid line start " + next));
                return null;
        }
    }

    Token handle(Line line, String next);
}
