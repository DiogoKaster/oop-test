package cargos;

public class Secretario extends Cargo{
    public Secretario() {
        super(7000, 1000, 0.20);
    }

    public double getBonificacao() {
        return this.bonificacao;
    }

    @Override
    public String toString() {
        return "Secretário";
    }
}
