package observer;

import java.util.*;

public class EventManager {
    private Map<String, List<Observador>> listeners = new HashMap<>();

    public EventManager(String... eventos) {
        for (String evento : eventos)
            listeners.put(evento, new ArrayList<>());
    }

    public void clear(String evento) {
        listeners.get(evento).clear();
    }

    public void subscribe(String evento, Observador obs) {
        listeners.get(evento).add(obs);
    }

    public void notify(String evento, String mensagem) {
        for (Observador o : listeners.get(evento))
            o.notificar(mensagem);
    }

    public List<Observador> getSubscribers(String evento) {
        return listeners.get(evento);
    }
}
