import cargos.Cargo;

import java.time.LocalDate;
import java.time.Period;

public class Funcionario {
    private String nome;
    private Cargo cargo;
    private LocalDate inicioContrato;

    public Funcionario(String nome, Cargo cargo, LocalDate inicioContrato) {
        this.nome = nome;
        this.cargo = cargo;
        this.inicioContrato = inicioContrato;
    }

    public double getSalarioRecebido(LocalDate date) {
        if (date.isAfter(this.inicioContrato)) {
            Period periodo = Period.between(this.inicioContrato, date);

            int anosTrabalhados = periodo.getYears();
            int mesesTrabalhados = anosTrabalhados * 12 + periodo.getMonths();
            int mesesTrabalhadosComBonus = mesesTrabalhados - 12;

            double salarioRecebido;

            if (mesesTrabalhadosComBonus > 0) {
                salarioRecebido = (this.cargo.getSalario() * mesesTrabalhados) + (mesesTrabalhadosComBonus * this.cargo.getBonusPorAno());
            } else {
                salarioRecebido = (this.cargo.getSalario() * mesesTrabalhados);
            }

            while (mesesTrabalhadosComBonus > 0) {
                mesesTrabalhadosComBonus -= 12;
                if (mesesTrabalhadosComBonus > 0) {
                    salarioRecebido += mesesTrabalhadosComBonus * this.cargo.getBonusPorAno();
                }
            }

            return salarioRecebido;
        }
        return 0;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public LocalDate getInicioContrato() {
        return inicioContrato;
    }
}
