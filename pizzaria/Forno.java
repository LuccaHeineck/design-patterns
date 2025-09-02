package pizzaria;

public class Forno {
    private static Forno instance;

    private Forno() {
    }

    public static Forno getInstance() {
        if (instance == null)
            instance = new Forno();
        return instance;
    }

    public void assarPizza(String tipo) {
        System.out.println("[FORNO] Assando pizza de " + tipo + "...");
    }
}
