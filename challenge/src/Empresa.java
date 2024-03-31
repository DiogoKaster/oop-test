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

    public double[] calcularValorPago(LocalDate date, JsonArray vendas, boolean comBeneficios) {
        double[] totalPago = new double[this.funcionarios.length];

        for(int i = 0; i < this.funcionarios.length; i++) {
            Funcionario funcionario = this.funcionarios[i];

            totalPago[i] = funcionario.getSalarioRecebidoNoMes(date);

            if (comBeneficios) {
                if(funcionario.getCargo() instanceof Secretario) {
                    totalPago[i] += totalPago[i] * funcionario.getCargo().getBonificacao();
                } else if (funcionario.getCargo() instanceof Vendedor) {
                    totalPago[i] += ((Vendedor) funcionario.getCargo()).getBonificacaoNoMes(vendas, funcionario.getNome(), date);
                }
            }
        }
        return totalPago;
    }
    public double[] calcularValorTotalEmBeneficios(LocalDate date, JsonArray vendas) {
        double[] totalPago = new double[this.funcionarios.length];

        for (int i = 0; i < this.funcionarios.length; i++) {
            Funcionario funcionario = this.funcionarios[i];

            if (funcionario.getCargo() instanceof Secretario) {
                double salarioFuncionario = funcionario.getSalarioRecebidoNoMes(date);
                totalPago[i] = salarioFuncionario * funcionario.getCargo().getBonificacao();
            } else if (funcionario.getCargo() instanceof Vendedor) {
                totalPago[i] = ((Vendedor) funcionario.getCargo()).getBonificacaoNoMes(vendas, funcionario.getNome(), date);
            }
        }

        return totalPago;
    }

    public Funcionario calcularValorMaisAltoMesSB(LocalDate date) {
        double salarioMaisAlto = 0;
        Funcionario funcionarioMaisPago = null;

        for (Funcionario funcionario : this.funcionarios) {
            if (date.isAfter(funcionario.getInicioContrato())) {
                double salarioFinal = funcionario.getSalarioRecebidoNoMes(date);

                if(salarioFinal > salarioMaisAlto) {
                    salarioMaisAlto = salarioFinal;
                    funcionarioMaisPago = funcionario;
                }
            }
        }

        return funcionarioMaisPago;
    }

    public Funcionario calcularValorMaisAltoMesCB(LocalDate date, JsonArray vendas) {
        double salarioMaisAlto = 0;
        Funcionario funcionarioMaisPago = null;

        for (Funcionario funcionario : this.funcionarios) {
            if (date.isAfter(funcionario.getInicioContrato()) && !(funcionario.getCargo() instanceof Gerente)) {
                double salarioFinal = funcionario.getSalarioRecebidoNoMes(date);

                if (funcionario.getCargo() instanceof Secretario) {
                    salarioFinal = salarioFinal * funcionario.getCargo().getBonificacao();
                } else if (funcionario.getCargo() instanceof Vendedor) {
                    for (int i = 0; i < vendas.size(); i++) {
                        JsonObject venda = vendas.get(i).getAsJsonObject();

                        String nomeVendedor = venda.get("nome").getAsString();
                        if(nomeVendedor.equals(funcionario.getNome())) {
                            JsonObject vendasPorMes = venda.getAsJsonObject("vendas_por_mes");
                            for(String mes : vendasPorMes.keySet()) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                                LocalDate dataVenda = LocalDate.parse("01/" + mes, formatter);

                                if(dataVenda.isAfter(date)){
                                    salarioFinal = 0;
                                } else {
                                    double valorVenda = vendasPorMes.get(mes).getAsDouble();
                                    salarioFinal = valorVenda * funcionario.getCargo().getBonificacao();
                                }
                            }
                        }
                    }
                }

                if(salarioFinal > salarioMaisAlto) {
                    salarioMaisAlto = salarioFinal;
                    funcionarioMaisPago = funcionario;
                }
            }
        }

        return funcionarioMaisPago;
    }

    public Funcionario calcularMelhorVendedor(LocalDate date, JsonArray vendas) {
        Funcionario melhorVendedorMes = null;
        double maiorVenda = 0;

        for (Funcionario funcionario : this.funcionarios) {
            double vendaAtual = 0;
            if (funcionario.getCargo() instanceof Vendedor) {
                for (int i = 0; i < vendas.size(); i++) {
                    JsonObject venda = vendas.get(i).getAsJsonObject();
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
    public double getValorTotalLista(double[] totalPagoLista) {
        if(totalPagoLista.length > 0) {
            double totalPago = 0;

            for (double valor : totalPagoLista) {
                System.out.println(valor);
                totalPago += valor;
            }

            return totalPago;
        }

        return 0;
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
