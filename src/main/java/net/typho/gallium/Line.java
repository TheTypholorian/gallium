package net.typho.gallium;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class Line {
    public final Gallium parent;
    public final int line;
    public final List<Instruction> insn = new LinkedList<>();
    public final List<UnaryOperator<Instruction>> queue = new LinkedList<>();
    public Instruction stack = null;

    public Line(Gallium parent, int num, String line) {
        this.parent = parent;
        this.line = num;
        Token token = null;

        Iterator<String> it = Arrays.stream(line.split("\\s+")).iterator();

        while (it.hasNext()) {
            String s = it.next();

            if (s.startsWith("//")) {
                break;
            }

            parent.debug.accept("Parsing token " + s);

            if (token == null) {
                parent.debug.accept("\tStarting line");
                token = Token.parse(this, s);
                parent.debug.accept("\t\tReturned " + token);
            } else {
                parent.debug.accept("\tAppending to " + token);
                token = token.handle(this, s);

                if (it.hasNext()) {
                    if (token == null) {
                        parent.debug.accept("\t\tStarting inner line");
                        token = Token.parse(this, s);
                        parent.debug.accept("\t\tReturned " + token);
                    } else {
                        parent.debug.accept("\t\tReturned " + token);
                    }
                }
            }
        }

        queue.forEach(c -> stack = c.apply(stack));
    }

    public void invoke() {
        insn.forEach(i -> {
            parent.debug.accept("Invoking " + i);
            i.invoke();
        });
    }
}
