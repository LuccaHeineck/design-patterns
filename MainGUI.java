import javax.swing.*;
import java.awt.*;
import factory.*;
import observer.Cliente;
import observer.Observador;
import pizzaria.Pizzaria;
import factory.Pizza;
import command.PedidoCommand;
import command.PedidoPizzaCommand;

public class MainGUI extends JFrame {
    private Pizzaria pizzaria;
    private JTextArea logArea;
    private JLabel pizzaLabel;

    private JCheckBox chkCliente1 = new JCheckBox("Cliente 1", true);
    private JCheckBox chkCliente2 = new JCheckBox("Cliente 2", true);
    private JCheckBox chkCliente3 = new JCheckBox("Cliente 3", true);

    private Cliente c1 = new Cliente("Cliente 1");
    private Cliente c2 = new Cliente("Cliente 2");
    private Cliente c3 = new Cliente("Cliente 3");

    public MainGUI() {
        setTitle("Pizzaria Observer + Singleton + Factory + Command");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pizzaria = Pizzaria.getInstance();
        logArea = new JTextArea();
        pizzaLabel = new JLabel();
        pizzaLabel.setHorizontalAlignment(JLabel.CENTER);
        pizzaLabel.setVerticalAlignment(JLabel.CENTER);

        // Clientes
        JPanel clientesPanel = new JPanel();
        clientesPanel.setLayout(new BoxLayout(clientesPanel, BoxLayout.Y_AXIS));
        clientesPanel.setBorder(BorderFactory.createTitledBorder("Clientes"));
        clientesPanel.add(chkCliente1);
        clientesPanel.add(chkCliente2);
        clientesPanel.add(chkCliente3);

        // Pizza
        JPanel pizzaPanel = new JPanel(new BorderLayout());
        pizzaPanel.setBorder(BorderFactory.createTitledBorder("Pizza Pronta"));
        pizzaPanel.add(pizzaLabel, BorderLayout.CENTER);

        // Log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
        logArea.setEditable(false);
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        // Botôes
        JPanel botoesPanel = new JPanel();
        JButton assarMargheritaBtn = new JButton("Assar Margherita");
        JButton assarCalabresaBtn = new JButton("Assar Calabresa");
        botoesPanel.add(assarMargheritaBtn);
        botoesPanel.add(assarCalabresaBtn);

        // Layout principal
        add(botoesPanel, BorderLayout.NORTH);
        add(clientesPanel, BorderLayout.WEST);
        add(pizzaPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);

        // Ações - FACTORY cria a pizza, COMMAND encapsula o pedido
        assarMargheritaBtn.addActionListener(e -> criarPedido(new MargheritaFactory().criarPizza()));
        assarCalabresaBtn.addActionListener(e -> criarPedido(new CalabresaFactory().criarPizza()));
    }

    private void criarPedido(Pizza pizza) {
        // Cria o comando
        PedidoCommand pedido = new PedidoPizzaCommand(pizza);
        pizzaria.adicionarPedido(pedido);

        logArea.append("Pedido adicionado: " + pizza.getNome() + "\n");

        // Configura os clientes inscritos
        pizzaria.eventos.clear("pizza_pronta");
        if (chkCliente1.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c1);
        if (chkCliente2.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c2);
        if (chkCliente3.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c3);

        // Processa o pedido depois de 3 segundos
        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
            PedidoCommand cmd = pizzaria.processarProximoPedido();
            if (cmd instanceof PedidoPizzaCommand pedidoPizza) {
                Pizza p = pedidoPizza.getPizza();

                pizzaria.pizzaPronta(p); // dispara evento do Observer

                // Mostra imagem da pizza
                String imgPath = p.getNome().equalsIgnoreCase("Margherita") ? "imagens/margherita.png"
                        : "imagens/calabresa.png";
                ImageIcon icon = new ImageIcon(imgPath);
                Image scaled = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                pizzaLabel.setIcon(new ImageIcon(scaled));

                // Loga notificações
                logArea.append("Pizza " + p.getNome() + " pronta!\nClientes notificados:\n");
                for (Observador c : pizzaria.eventos.getSubscribers("pizza_pronta")) {
                    logArea.append(" - " + c.getNome() + "\n");
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
