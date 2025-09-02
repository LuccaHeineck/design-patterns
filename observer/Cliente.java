package observer;

public class Cliente implements Observador {
    private String nome;

    public Cliente(String nome) {
        this.nome = nome;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("[CLIENTE " + nome + "] recebeu: " + mensagem);
    }

    @Override
    public String getNome() {
        return nome;
    }
}
