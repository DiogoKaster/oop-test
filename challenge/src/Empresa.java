import cargos.Cargo;
import cargos.Gerente;
import cargos.Secretario;
import cargos.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Empresa {
    private final Funcionario[] funcionarios;

    public Empresa(JsonArray funcionariosJson) {
        this.funcionarios = new Funcionario[funcionariosJson.size()];

        if(!funcionariosJson.isEmpty()) {
            for (int i = 0; i < funcionariosJson.size(); i++) {
                JsonObject funcionario = funcionariosJson.get(i).getAsJsonObject();

                String nomeFuncionario = funcionario.get("nome").getAsString();
                String cargoFuncionario = funcionario.get("cargo").getAsString().toLowerCase();
                String contratacao = funcionario.get("contratacao").getAsString();

                Funcionario funcionarioInstancia = getFuncionario(cargoFuncionario, nomeFuncionario, contratacao);
                this.funcionarios[i] = funcionarioInstancia;
            }
        } else {
            System.out.println("A lista para cadastro está vazia!");
        }
    }

    public double[] calcularValorPago(LocalDate date) {
        double[] totalPago = new double[this.funcionarios.length];

        for(int i = 0; i < this.funcionarios.length; i++) {
            Funcionario funcionario = this.funcionarios[i];

            if (date.isAfter(funcionario.getInicioContrato())) {
                Period periodo = Period.between(funcionario.getInicioContrato(), date);
                int anosTrabalhados = periodo.getYears();

                totalPago[i] = getSalarioRecebido(anosTrabalhados, periodo, funcionario);
            }
        }
        return totalPago;
    }

    private static double getSalarioRecebido(int anosTrabalhados, Period periodo, Funcionario funcionario) {
        int mesesTrabalhados = anosTrabalhados * 12 + periodo.getMonths();
        int mesesTrabalhadosComBonus = mesesTrabalhados - 12;

        double bonusAnual = funcionario.getCargo().getBonusPorAno();
        double salarioBase = funcionario.getCargo().getSalario();
        double salarioRecebido = (salarioBase * mesesTrabalhados) + (mesesTrabalhadosComBonus * bonusAnual);

        while (mesesTrabalhadosComBonus > 0) {
            mesesTrabalhadosComBonus -= 12;
            if(mesesTrabalhadosComBonus > 0){
                salarioRecebido += mesesTrabalhadosComBonus * bonusAnual;
        }}
        return salarioRecebido;
    }

    private static Funcionario getFuncionario(String cargoFuncionario, String nomeFuncionario, String contratacao) {
        Cargo cargo = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dateContratacao = LocalDate.parse("01/" + contratacao, formatter);

        switch (cargoFuncionario) {
            case "secretário":
                cargo = new Secretario();
                break;
            case "vendedor":
                cargo = new Vendedor();
                break;
            case "gerente":
                cargo = new Gerente();
                break;
            default:
                break;
        }

        return new Funcionario(nomeFuncionario, cargo, dateContratacao);
    }

    public Funcionario[] getFuncionarios() {
        return funcionarios;
    }

    public void printaFuncionarios() {
        for(Funcionario funcionario : this.funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Cargo: " + funcionario.getCargo());
            System.out.println("Contratacao: " + funcionario.getInicioContrato());
            System.out.println();
        }
    }
}
