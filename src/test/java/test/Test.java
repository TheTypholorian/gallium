package test;

import net.typho.gallium.Gallium;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        Gallium gallium = new Gallium.Builder()
                .code(new File("test.ga"))
                .debug()
                .create();

        gallium.run();
    }
}
