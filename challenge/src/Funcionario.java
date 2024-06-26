import cargos.Cargo;
import com.google.gson.JsonArray;

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

    public double getSalarioRecebidoNoMes(LocalDate date) {
        if(!date.isBefore(this.inicioContrato)) {
            Period periodo = Period.between(this.getInicioContrato(), date);
            int anosTrabalhados = periodo.getYears();
            double salarioDoCargo = this.getCargo().getSalario();
            double bonusAnual = this.getCargo().getBonusPorAno();

            return salarioDoCargo + (bonusAnual * anosTrabalhados);
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
