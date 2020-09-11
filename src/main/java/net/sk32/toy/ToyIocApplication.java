package net.sk32.toy;

public class ToyIocApplication {
    private final Class<?> primarySource;

    public ToyIocApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public static void run(Class<?> primarySource, String... args) {
        new ToyIocApplication(primarySource).run(args);
    }

    private void run(String[] args) {
        System.out.println("" +
                "  _______          _____ ____   _____ \n" +
                " |__   __|        |_   _/ __ \\ / ____|\n" +
                "    | | ___  _   _  | || |  | | |     \n" +
                "    | |/ _ \\| | | | | || |  | | |     \n" +
                "    | | (_) | |_| |_| || |__| | |____ \n" +
                "    |_|\\___/ \\__, |_____\\____/ \\_____|\n" +
                "              __/ |                   \n" +
                "             |___/         ToyIOC v1.0");
        System.out.println(primarySource);
    }
}
