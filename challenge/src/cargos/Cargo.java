package cargos;

public class Cargo {
    protected double salario;
    protected double bonusPorAno;
    protected double bonificacao;

    public Cargo (double salario, double bonusPorAno, double bonificacao) {
        this.salario = salario;
        this.bonusPorAno = bonusPorAno;
        this.bonificacao = bonificacao;
    }

    public double getSalario() {
        return salario;
    }

    public double getBonusPorAno() {
        return bonusPorAno;
    }

    public double getBonificacao() { return bonificacao; }
}
