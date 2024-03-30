import cargos.Cargo;

import java.time.LocalDate;

public class Funcionario {
    private String nome;
    private Cargo cargo;
    private LocalDate inicioContrato;

    public Funcionario(String nome, Cargo cargo, LocalDate inicioContrato) {
        this.nome = nome;
        this.cargo = cargo;
        this.inicioContrato = inicioContrato;
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
