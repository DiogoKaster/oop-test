package cargos;

public class Gerente extends Cargo{
    public Gerente() {
        super(20000, 3000);
    }

    @Override
    public String toString() {
        return "Gerente";
    }
}
