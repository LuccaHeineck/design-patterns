package factory;

public class MargheritaFactory extends PizzaFactory {
    @Override
    public Pizza criarPizza() {
        return new PizzaMargherita();
    }
}