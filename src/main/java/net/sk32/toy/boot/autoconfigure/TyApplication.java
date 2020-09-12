package net.sk32.toy.boot.autoconfigure;

public class TyApplication {
    private final Class<?> primarySource;

    public TyApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public static void run(Class<?> primarySource, String... args) {
        new TyApplication(primarySource).run(args);
    }

    private void run(String[] args) {
        // Banner
        System.out.println("" +
                "  _______          _____ ____   _____ \n" +
                " |__   __|        |_   _/ __ \\ / ____|\n" +
                "    | | ___  _   _  | || |  | | |     \n" +
                "    | |/ _ \\| | | | | || |  | | |     \n" +
                "    | | (_) | |_| |_| || |__| | |____ \n" +
                "    |_|\\___/ \\__, |_____\\____/ \\_____|\n" +
                "              __/ |                   \n" +
                "             |___/         ToyIOC v1.0");

        TyApplicationContext context = createApplicationContext();
        context.loadBeans(primarySource);
    }

    private TyApplicationContext createApplicationContext() {
        return TyApplicationContext.getInstance();
    }
}
