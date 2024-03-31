import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Repository {
    private JsonObject funcionarios;
    private JsonObject vendas;

    public Repository() {
        Gson gson = new Gson();
        try {
            InputStreamReader funcionariosData = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/funcionarios.json")));
            funcionarios = gson.fromJson(funcionariosData, JsonObject.class);
            funcionariosData.close();

            InputStreamReader vendasData = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/vendas.json")));
            vendas = gson.fromJson(vendasData, JsonObject.class);
            vendasData.close();

        } catch (IOException error) {
            System.out.println("Error: " + error.getMessage());
        }
    }
    public JsonArray getFuncionarios() {
        return funcionarios.getAsJsonArray("funcionarios");
    }

    public JsonArray getVendas() {
        return vendas.getAsJsonArray("vendas");
    }
}
