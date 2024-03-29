import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();

        JsonArray cargos = repositorio.getCargos();
        JsonArray funcionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        for (int i = 0; i < cargos.size(); i++) {
            JsonObject cargo = cargos.get(i).getAsJsonObject();

            String nomeCargo = cargo.get("cargo").getAsString();
            double salarioBase = cargo.get("salario_base").getAsDouble();
            double beneficioAnual = cargo.get("beneficio_anual").getAsDouble();

            System.out.println("Cargo: " + nomeCargo);
            System.out.println("Salário Base: " + salarioBase);
            System.out.println("Benefício Anual: " + beneficioAnual);
            System.out.println();
        }
        for (int i = 0; i < funcionarios.size(); i++) {
            JsonObject funcionario = funcionarios.get(i).getAsJsonObject();

            String nomeFuncionario = funcionario.get("nome").getAsString();
            String cargoFuncionario = funcionario.get("cargo").getAsString();
            String contratacao = funcionario.get("contratacao").getAsString();

            System.out.println("Nome: " + nomeFuncionario);
            System.out.println("Cargo: " + cargoFuncionario);
            System.out.println("Contratacao: " + contratacao);
            System.out.println();
        }
        for (int i = 0; i < vendas.size(); i++) {
            JsonObject venda = vendas.get(i).getAsJsonObject();

            String nomeVenda = venda.get("nome").getAsString();
            String cargoVendas = venda.get("cargo").getAsString();
            JsonObject vendasPorMes = venda.get("vendas_por_mes").getAsJsonObject();

            System.out.println("Nome: " + nomeVenda);
            System.out.println("Cargo: " + cargoVendas);
            System.out.println("Vendas por mes: " + vendasPorMes);
            System.out.println();
        }
    }
}