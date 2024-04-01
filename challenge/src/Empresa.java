import cargos.Cargo;
import cargos.Gerente;
import cargos.Secretario;
import cargos.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Empresa {
    private Funcionario[] funcionarios;

    private JsonArray vendas;

    public Empresa(JsonArray funcionariosJson, JsonArray vendas) {
        this.funcionarios = new Funcionario[funcionariosJson.size()];

        if(!vendas.isEmpty()) {
            this.vendas = vendas;
        } else {
            this.vendas = new JsonArray();
            System.out.println("A lista de vendas está vazia!");
        }

        if(!funcionariosJson.isEmpty()) {
            for (int i = 0; i < funcionariosJson.size(); i++) {
                JsonObject funcionario = funcionariosJson.get(i).getAsJsonObject();

                String nomeFuncionario = funcionario.get("nome").getAsString();
                String cargoFuncionario = funcionario.get("cargo").getAsString().toLowerCase();
                String contratacao = funcionario.get("contratacao").getAsString();

                Funcionario funcionarioInstancia = setFuncionarioObj(cargoFuncionario, nomeFuncionario, contratacao);
                this.funcionarios[i] = funcionarioInstancia;
            }
        } else {
            System.out.println("A lista de funcionários está vazia!");
        }
    }

    public double calcularValorPagoNoMes(LocalDate date, boolean comBeneficios) {
        double totalPago = 0;

        for(Funcionario funcionario : this.funcionarios) {
            double salarioFuncionario = this.calculaSalario(funcionario, comBeneficios, date);
            totalPago += salarioFuncionario;
        }
        return totalPago;
    }
    public double calcularValorTotalEmBeneficios(LocalDate date) {
        double totalPago = 0;

        for (Funcionario funcionario : this.funcionarios) {
            double beneficioPago = this.calculaBeneficio(funcionario, date);
            totalPago += beneficioPago;
        }

        return totalPago;
    }

    public Funcionario calcularMaiorSalarioSB(LocalDate date) {
        double salarioMaisAlto = 0;
        Funcionario funcionarioMaisPago = null;

        for (Funcionario funcionario : this.funcionarios) {
            double salarioFuncionario = this.calculaSalario(funcionario, false, date);

            if(salarioFuncionario > salarioMaisAlto) {
                salarioMaisAlto = salarioFuncionario;
                funcionarioMaisPago = funcionario;
            }
        }

        return funcionarioMaisPago;
    }

    public Funcionario calcularMaiorBeneficio(LocalDate date) {
        double maiorBeneficio = 0;
        Funcionario funcionarioMaisPago = null;

        for (Funcionario funcionario : this.funcionarios) {
            double beneficioPago = this.calculaBeneficio(funcionario, date);

            if(beneficioPago > maiorBeneficio) {
                maiorBeneficio = beneficioPago;
                funcionarioMaisPago = funcionario;
            }
        }

        return funcionarioMaisPago;
    }

    public Funcionario calcularMelhorVendedor(LocalDate date) {
        Funcionario melhorVendedorMes = null;
        double maiorVenda = 0;

        for (Funcionario funcionario : this.funcionarios) {
            double vendaAtual = 0;
            if (funcionario.getCargo() instanceof Vendedor) {
                for (int i = 0; i < this.vendas.size(); i++) {
                    JsonObject venda = this.vendas.get(i).getAsJsonObject();
                    String nomeVendedor = venda.get("nome").getAsString();

                    if (nomeVendedor.equals(funcionario.getNome())) {
                        JsonObject vendasPorMes = venda.getAsJsonObject("vendas_por_mes");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                        String dataFormatada = date.format(formatter);

                        if(vendasPorMes.get(dataFormatada) != null) {
                            vendaAtual = vendasPorMes.get(dataFormatada).getAsDouble();
                        }
                    }
                }
            }

            if(vendaAtual > maiorVenda) {
                maiorVenda = vendaAtual;
                melhorVendedorMes = funcionario;
            }
        }


        return melhorVendedorMes;
    }

    public double calculaSalario(Funcionario funcionario, boolean comBeneficios, LocalDate date) {
        if (comBeneficios) {
            return funcionario.getSalarioRecebidoNoMes(date) + calculaBeneficio(funcionario, date);
        } else {
            return funcionario.getSalarioRecebidoNoMes(date);
        }
    }

    public double calculaBeneficio(Funcionario funcionario, LocalDate date) {
        double salarioFuncionario = 0;

        if (funcionario.getCargo() instanceof Secretario) {
            salarioFuncionario = funcionario.getSalarioRecebidoNoMes(date) * funcionario.getCargo().getBonificacao();
        } else if (funcionario.getCargo() instanceof Vendedor) {
            salarioFuncionario = ((Vendedor) funcionario.getCargo()).getBonificacaoNoMes(this.vendas, funcionario.getNome(), date);
        }

        return salarioFuncionario;
    }

    private static Funcionario setFuncionarioObj(String cargoFuncionario, String nomeFuncionario, String contratacao) {
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

    public void setFuncionarios(Funcionario[] funcionarios) {
        this.funcionarios = funcionarios;
    }

    public void setVendas(JsonArray vendas) {
        this.vendas = vendas;
    }
}
