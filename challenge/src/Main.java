import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);

        double[] listaValoresPagosSB = empresa.calcularValorPagoSemBeneficios(LocalDate.of(2010, 1, 1));
        double[] listaValoresPagosCB = empresa.calcularValorPagoComBeneficios(LocalDate.of(2023, 1, 1), vendas);
        double valorPagoEmBeneficios = empresa.calcularValorTotalEmBeneficios(LocalDate.of(2023, 1, 1), vendas);
        Funcionario funcionarioMaisPagoSB = empresa.calcularValorMaisAltoMesSB(LocalDate.of(2023, 1, 1));
        Funcionario funcionarioMaisPagoCB = empresa.calcularValorMaisAltoMesCB(LocalDate.of(2023, 1, 1), vendas);
        Funcionario vendedorMaisPago = empresa.calcularMelhorVendedor(LocalDate.of(2023, 4, 1), vendas);

        double valorTotalPagoSB = 0;
        double valorTotalPagoCB = 0;

        for(double valor : listaValoresPagosSB) {
            System.out.println(valor);
            System.out.println();
            valorTotalPagoSB += valor;
        }

        for(double valor : listaValoresPagosCB) {
            System.out.println();
            System.out.println(valor);
            System.out.println();
            valorTotalPagoCB += valor;
        }

        System.out.println();
        System.out.println("Valor total pago sem benefícios: " + valorTotalPagoSB);
        System.out.println();
        System.out.println("Valor total pago com benefícios: " + valorTotalPagoCB);
        System.out.println();
        System.out.println("Valor total pago em benefícios: " + valorPagoEmBeneficios);
        System.out.println();
        System.out.println("Funcionário que mais recebeu no mês: " + funcionarioMaisPagoSB.getNome());
        System.out.println();
        System.out.println("Funcionário com benefícios que mais recebeu no mês: " + funcionarioMaisPagoCB.getNome());
        System.out.println();

        if(vendedorMaisPago != null) {
            System.out.println("Vendedor mais bem pago: " + vendedorMaisPago.getNome());
            System.out.println();
        } else {
            System.out.println("Não existiram vendas nesse mês");
        }
    }
}