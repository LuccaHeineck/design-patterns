package factory;

public class CalabresaFactory extends PizzaFactory {
    @Override
    public Pizza criarPizza() {
        return new PizzaCalabresa();
    }
}