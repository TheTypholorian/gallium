package net.typho.gallium;

public class ParsingException extends RuntimeException {
    public final Reason reason;
    public final int line;
    public final String specs;

    public ParsingException(Reason reason, int line, String specs) {
        super("Parsing error " + reason + " at line " + line + " (" + specs + ")");
        this.reason = reason;
        this.line = line;
        this.specs = specs;
    }

    public ParsingException(Reason reason, int line) {
        super("Parsing error " + reason + " at line " + line);
        this.reason = reason;
        this.line = line;
        this.specs = null;
    }

    public ParsingException(Reason reason, Line line, String specs) {
        this(reason, line.line + 1, specs);
    }

    public ParsingException(Reason reason, Line line) {
        this(reason, line.line + 1);
    }

    public enum Reason {
        CLASS_NOT_FOUND,
        FIELD_NOT_FOUND,
        BAD_TOKEN_INPUT,
        BAD_LINE_START,
        BAD_LOCAL_VAR_INPUT_TOKEN,
        BAD_LOCAL_VAR_SET_INPUT_TOKEN,
        BAD_VAR_SET_INPUT_TOKEN,
        BAD_PRINT_INPUT_TOKEN,
        INVALID_PRIMITIVE
    }
}
