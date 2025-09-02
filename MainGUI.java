import javax.swing.*;
import java.awt.*;
import factory.*;
import observer.Cliente;
import observer.Observador;
import pizzaria.Forno;
import pizzaria.Pizzaria;

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
        setTitle("Pizzaria Observer Demo");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        pizzaria = new Pizzaria();
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

        // Ações - FACTORY é utilizado para criar as pizzas
        assarMargheritaBtn.addActionListener(e -> assarPizza(new MargheritaFactory().criarPizza()));
        assarCalabresaBtn.addActionListener(e -> assarPizza(new CalabresaFactory().criarPizza()));
    }

    private void assarPizza(Pizza pizza) {
        // SINGLETON é utilizado chamando a instância única do forno
        Forno.getInstance().assarPizza(pizza.getNome());

        logArea.append("Assando " + pizza.getNome() + "...\n");

        pizzaria.eventos.clear("pizza_pronta");
        if (chkCliente1.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c1);
        if (chkCliente2.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c2);
        if (chkCliente3.isSelected())
            pizzaria.eventos.subscribe("pizza_pronta", c3);

        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> {
            pizzaria.pizzaPronta(pizza);

            // Imagem da pizza
            String imgPath = pizza.getNome().equalsIgnoreCase("Margherita") ? "imagens/margherita.png"
                    : "imagens/calabresa.png";
            ImageIcon icon = new ImageIcon(imgPath);
            Image scaled = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            pizzaLabel.setIcon(new ImageIcon(scaled));

            // Clientes são notificados
            logArea.append("Pizza " + pizza.getNome() + " pronta!\nClientes notificados:\n");
            for (Observador c : pizzaria.eventos.getSubscribers("pizza_pronta")) {
                logArea.append(" - " + c.getNome() + "\n");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
