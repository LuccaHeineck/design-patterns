package command;

import factory.Pizza;
import pizzaria.Forno;

public class PedidoPizzaCommand implements PedidoCommand {
    private Pizza pizza;

    public PedidoPizzaCommand(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public void executar() {
        Forno.getInstance().assarPizza(pizza.getNome());
    }

    public Pizza getPizza() {
        return pizza;
    }
}
