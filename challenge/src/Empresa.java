import cargos.Cargo;
import cargos.Gerente;
import cargos.Secretario;
import cargos.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
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

    public double[] calcularValorPagoComBeneficios(LocalDate date, JsonArray vendas) {
        double[] totalPago = new double[this.funcionarios.length];

        for(int i = 0; i < this.funcionarios.length; i++) {
            Funcionario funcionario = this.funcionarios[i];

            totalPago[i] = funcionario.getSalarioRecebido(date);

            if(funcionario.cargo() instanceof Secretario) {
                totalPago[i] += totalPago[i] * ((Secretario) funcionario.cargo()).getBonificacao();
            } else if (funcionario.cargo() instanceof Vendedor) {
                totalPago[i] += ((Vendedor) funcionario.cargo()).getBonificacaoEmVendas(vendas, funcionario.nome(), date);
            }
        }
        return totalPago;
    }

    public double[] calcularValorPagoSemBeneficios(LocalDate date) {
        double[] totalPago = new double[this.funcionarios.length];

        for(int i = 0; i < this.funcionarios.length; i++) {
            Funcionario funcionario = this.funcionarios[i];

            totalPago[i] = funcionario.getSalarioRecebido(date);
        }
        return totalPago;
    }

    public double calcularValorTotalEmBeneficios(LocalDate date, JsonArray vendas) {
        double totalPago = 0;

        for (Funcionario funcionario : this.funcionarios) {
            if (funcionario.cargo() instanceof Secretario) {
                double salarioFuncionario = funcionario.getSalarioRecebido(date);
                totalPago += salarioFuncionario * ((Secretario) funcionario.cargo()).getBonificacao();
            } else if (funcionario.cargo() instanceof Vendedor) {
                totalPago += ((Vendedor) funcionario.cargo()).getBonificacaoEmVendas(vendas, funcionario.nome(), date);
            }
        }
        return totalPago;
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
            System.out.println("Nome: " + funcionario.nome());
            System.out.println("Cargo: " + funcionario.cargo());
            System.out.println("Contratacao: " + funcionario.inicioContrato());
            System.out.println();
        }
    }
}
