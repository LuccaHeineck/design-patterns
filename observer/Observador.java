package observer;

public interface Observador {
    void notificar(String mensagem);

    String getNome();
}
