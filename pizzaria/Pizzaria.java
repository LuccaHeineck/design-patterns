package pizzaria;

import observer.EventManager;
import command.PedidoCommand;
import command.PizzariaInvoker;
import factory.Pizza;

public class Pizzaria {
    private static Pizzaria instance;
    public EventManager eventos;
    private PizzariaInvoker invoker;

    private Pizzaria() {
        eventos = new EventManager("pizza_pronta");
        invoker = new PizzariaInvoker();
    }

    public static Pizzaria getInstance() {
        if (instance == null) {
            instance = new Pizzaria();
        }
        return instance;
    }

    public void adicionarPedido(PedidoCommand pedido) {
        invoker.adicionarPedido(pedido);
    }

    public PedidoCommand processarProximoPedido() {
        return invoker.processarProximoPedido();
    }

    public boolean temPedidos() {
        return invoker.temPedidos();
    }

    public void pizzaPronta(Pizza pizza) {
        System.out.println("[PIZZARIA] Pizza " + pizza.getNome() + " está pronta!");
        eventos.notify("pizza_pronta", "Sua pizza de " + pizza.getNome() + " está pronta!");
    }
}
