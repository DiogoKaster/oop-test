import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray dataVendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);

        double[] valorTotal;

        valorTotal = empresa.calcularValorPago(LocalDate.of(2019, 5, 1));

        for(double valor : valorTotal) {
            System.out.println(valor);
        }
    }
}