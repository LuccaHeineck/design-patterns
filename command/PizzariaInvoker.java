package command;

import java.util.LinkedList;
import java.util.Queue;

public class PizzariaInvoker {
    private Queue<PedidoCommand> filaPedidos = new LinkedList<>();

    public void adicionarPedido(PedidoCommand pedido) {
        filaPedidos.add(pedido);
    }

    public PedidoCommand processarProximoPedido() {
        return filaPedidos.poll(); // Retorna e remove o próximo pedido
    }

    public boolean temPedidos() {
        return !filaPedidos.isEmpty();
    }
}
