import cargos.Cargo;
import cargos.Gerente;
import cargos.Secretario;
import cargos.Vendedor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.Normalizer;

public class Empresa {
    private final Funcionario[] funcionarios;

    public Empresa(JsonArray funcionariosJson) {
        this.funcionarios = new Funcionario[funcionariosJson.size()];

        if(!funcionariosJson.isEmpty()) {
            for (int i = 0; i < funcionariosJson.size(); i++) {
                JsonObject funcionario = funcionariosJson.get(i).getAsJsonObject();

                String nomeFuncionario = funcionario.get("nome").getAsString();
                String cargoFuncionario = Normalizer.normalize(funcionario.get("cargo").getAsString(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "")
                        .toLowerCase();
                String contratacao = funcionario.get("contratacao").getAsString();

                Funcionario funcionarioInstancia = getFuncionario(cargoFuncionario, nomeFuncionario, contratacao);
                this.funcionarios[i] = funcionarioInstancia;
            }
        } else {
            System.out.println("A lista para cadastro está vazia!");
        }
    }

    private static Funcionario getFuncionario(String cargoFuncionario, String nomeFuncionario, String contratacao) {
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

        return new Funcionario(nomeFuncionario, cargo, contratacao);
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
