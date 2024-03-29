package cargos;

public class Cargo {
    protected double salario;
    protected double bonusPorAno;

    public Cargo (double salario, double bonusPorAno) {
        this.salario = salario;
        this.bonusPorAno = bonusPorAno;
    }

    public void calculoBonusPorAno(int anos) {
        this.salario += this.bonusPorAno * anos;
    }

    public double getSalario() {
        return salario;
    }

    public double getBonusPorAno() {
        return bonusPorAno;
    }
}
