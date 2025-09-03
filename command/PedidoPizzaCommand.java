package command;

import pizzaria.Pizzaria;
import factory.Pizza;
import pizzaria.Forno;

public class PedidoPizzaCommand implements PedidoCommand {
    private Pizza pizza;
    private Pizzaria pizzaria;

    public PedidoPizzaCommand(Pizza pizza, Pizzaria pizzaria) {
        this.pizza = pizza;
        this.pizzaria = pizzaria;
    }

    @Override
    public void executar() {
        // Assa pizza
        Forno.getInstance().assarPizza(pizza.getNome());

        // Dispara evento do Observer
        pizzaria.pizzaPronta(pizza);
    }

    public Pizza getPizza() {
        return pizza;
    }
}
