package net.typho.gallium;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Gallium {
    public static final Consumer<String> defDebug = s -> System.out.println("\033[38;5;11m" + s + "\033[0m");
    public final Map<String, Variable> vars = new LinkedHashMap<>();
    public final List<Line> lines = new LinkedList<>();
    public final Consumer<Exception> error;
    public final Consumer<String> debug, console;

    public Gallium(Builder builder) {
        error = builder.error;
        debug = builder.debug;
        console = builder.console;

        int i = 0;

        for (String code : builder.code) {
            for (String line : code.replace("(", "").replace(")", "").split("\n")) {
                lines.add(new Line(this, i++, line));
            }
        }
    }

    public void run() {
        lines.forEach(Line::invoke);
    }

    public static void setField(Field field, Object target, Object value) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (fieldType.isPrimitive()) {
            if (fieldType == int.class) {
                value = ((Number) value).intValue();
            } else if (fieldType == long.class) {
                value = ((Number) value).longValue();
            } else if (fieldType == double.class) {
                value = ((Number) value).doubleValue();
            } else if (fieldType == float.class) {
                value = ((Number) value).floatValue();
            } else if (fieldType == short.class) {
                value = ((Number) value).shortValue();
            } else if (fieldType == byte.class) {
                value = ((Number) value).byteValue();
            } else if (fieldType == char.class) {
                value = (char) ((Number) value).intValue();
            }
        } else if (!fieldType.isInstance(value)) {
            if (Number.class.isAssignableFrom(fieldType)) {
                if (fieldType == Integer.class) {
                    value = ((Number) value).intValue();
                } else if (fieldType == Long.class) {
                    value = ((Number) value).longValue();
                } else if (fieldType == Double.class) {
                    value = ((Number) value).doubleValue();
                } else if (fieldType == Float.class) {
                    value = ((Number) value).floatValue();
                } else if (fieldType == Short.class) {
                    value = ((Number) value).shortValue();
                } else if (fieldType == Byte.class) {
                    value = ((Number) value).byteValue();
                }
            }
        }

        field.set(target, value);
    }

    public static final class Builder {
        public final List<String> code = new LinkedList<>();
        public Consumer<Exception> error = e -> {
            throw (e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e));
        };
        public Consumer<String> debug = s -> {}, console = System.out::println;

        private Builder error(Exception e) {
            error.accept(e);
            return this;
        }

        public Builder code(String code) {
            this.code.add(code);
            return this;
        }

        public Builder code(Path path) {
            return code(path.toFile());
        }

        public Builder code(File file) {
            try (FileInputStream in = new FileInputStream(file)) {
                return code(in);
            } catch (IOException e) {
                return error(e);
            }
        }

        public Builder code(URL url) {
            try {
                return code(url.openStream());
            } catch (IOException e) {
                return error(e);
            }
        }

        public Builder code(InputStream in) {
            try {
                try (DataInputStream d = new DataInputStream(in)) {
                    byte[] b = new byte[d.available()];

                    d.readFully(b);

                    return code(new String(b));
                }
            } catch (IOException e) {
                return error(e);
            }
        }

        public Builder error(Consumer<Exception> error) {
            this.error = error;
            return this;
        }

        public Builder debug(Consumer<String> debug) {
            this.debug = debug;
            return this;
        }

        public Builder debug(boolean debug) {
            return debug(debug ? defDebug : s -> {});
        }

        public Builder debug() {
            return debug(defDebug);
        }

        public Builder console(Consumer<String> console) {
            this.console = console;
            return this;
        }

        public Gallium create() {
            return new Gallium(this);
        }
    }
}