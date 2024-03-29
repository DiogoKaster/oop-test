package cargos;

public class Secretario extends Cargo{
    public Secretario() {
        super(7000, 1000);
    }
    public double calcularBeneficio(int anosServico) {
        return 0.20 * super.salario;
    }

    @Override
    public String toString() {
        return "Secretário";
    }
}
