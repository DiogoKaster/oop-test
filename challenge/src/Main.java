import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray dataVendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);

        double[] valorTotal = empresa.calcularValorPagoSemBeneficios(LocalDate.of(2020, 1, 1));
        double valorTotalEmBeneficios = empresa.calcularValorTotalEmBeneficios(LocalDate.of(2020, 1, 1), dataVendas);

        for(double valor : valorTotal) {
            System.out.println(valor);
        }

        System.out.println(valorTotalEmBeneficios);
    }
}