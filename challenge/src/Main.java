import com.google.gson.JsonArray;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray dataVendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);
        empresa.printaFuncionarios();
    }
}