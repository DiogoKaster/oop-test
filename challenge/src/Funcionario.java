import cargos.Cargo;

public class Funcionario {
    private String nome;
    private Cargo cargo;
    private String inicioContrato;

    public Funcionario(String nome, Cargo cargo, String inicioContrato) {
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

    public String getInicioContrato() {
        return inicioContrato;
    }
}
