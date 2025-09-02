package pizzaria;

import factory.Pizza;
import observer.EventManager;

public class Pizzaria {
    public EventManager eventos;

    public Pizzaria() {
        eventos = new EventManager("pizza_pronta");
    }

    public void pizzaPronta(Pizza pizza) {
        System.out.println("[PIZZARIA] Pizza " + pizza.getNome() + " está pronta!");
        eventos.notify("pizza_pronta", "Sua pizza de " + pizza.getNome() + " está pronta!");
    }
}
