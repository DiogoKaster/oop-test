import cargos.Cargo;
import cargos.Gerente;
import cargos.Secretario;
import cargos.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.Normalizer;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();

        JsonArray funcionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        Cargo[] cargos = {
                new Secretario(),
                new Vendedor(),
                new Gerente()
        };

        for (int i = 0; i < funcionarios.size(); i++) {
            JsonObject funcionario = funcionarios.get(i).getAsJsonObject();

            String nomeFuncionario = funcionario.get("nome").getAsString();
            String cargoFuncionario = Normalizer.normalize(funcionario.get("cargo").getAsString(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .toLowerCase();
            String contratacao = funcionario.get("contratacao").getAsString();

            Cargo cargo = null;

            switch (cargoFuncionario) {
                case "secretario":
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

            Funcionario funcionarioInstancia = new Funcionario(nomeFuncionario, cargo, contratacao);

            System.out.println("Nome: " + funcionarioInstancia.getNome());
            System.out.println("Cargo: " + funcionarioInstancia.getCargo());
            System.out.println("Contratacao: " + funcionarioInstancia.getInicioContrato());
            System.out.println();
        }
    }
}