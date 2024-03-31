import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);

        double[] listaValoresPagosSB = empresa.calcularValorPagoSemBeneficios(LocalDate.of(2023, 1, 1));
        double[] listaValoresPagosCB = empresa.calcularValorPagoComBeneficios(LocalDate.of(2023, 1, 1), vendas);
        double valorPagoEmBeneficios = empresa.calcularValorTotalEmBeneficios(LocalDate.of(2023, 1, 1), vendas);
        Funcionario funcionarioMaisPago = empresa.calcularValorMaisAltoMes(LocalDate.of(2023, 1, 1));

        double valorTotalPagoSB = 0;
        double valorTotalPagoCB = 0;

        for(double valor : listaValoresPagosSB) {
            valorTotalPagoSB += valor;
        }

        for(double valor : listaValoresPagosCB) {
            valorTotalPagoCB += valor;
        }

        System.out.println();
        System.out.println("Valor total pago sem benefícios: " + valorTotalPagoSB);
        System.out.println();
        System.out.println("Valor total pago com benefícios: " + valorTotalPagoCB);
        System.out.println();
        System.out.println("Valor total pago em benefícios: " + valorPagoEmBeneficios);
        System.out.println();
        System.out.println("Funcionário que mais recebeu no mês: " + funcionarioMaisPago.getNome());
        System.out.println();
    }
}