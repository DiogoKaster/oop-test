import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray dataFuncionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        Empresa empresa = new Empresa(dataFuncionarios);

        double[] listaValoresPagosSB = empresa.calcularValorPago(LocalDate.of(2022, 1, 1), vendas, false);
        double valorTotalPagoSB = empresa.getValorTotalLista(listaValoresPagosSB);
        System.out.println();
        System.out.println("Valor total pago sem benefícios: " + valorTotalPagoSB);

        double[] listaValoresPagosCB = empresa.calcularValorPago(LocalDate.of(2022, 1, 1), vendas, true);
        double valorTotalPagoCB = empresa.getValorTotalLista(listaValoresPagosCB);
        System.out.println();
        System.out.println("Valor total pago com benefícios: " + valorTotalPagoCB);

        double[] listaBeneficiosNoMes = empresa.calcularValorTotalEmBeneficios(LocalDate.of(2022, 1, 1), vendas);
        double valorPagoEmBeneficios = empresa.getValorTotalLista(listaBeneficiosNoMes);
        System.out.println();
        System.out.println("Valor total pago em benefícios no mês: " + valorPagoEmBeneficios);

        Funcionario funcionarioMaisPagoSB = empresa.calcularValorMaisAltoMesSB(LocalDate.of(2022, 1, 1));
        System.out.println();
        System.out.println("Funcionário que mais recebeu no mês: " + funcionarioMaisPagoSB.getNome());


//        Funcionario funcionarioMaisPagoCB = empresa.calcularValorMaisAltoMesCB(LocalDate.of(2020, 1, 1), vendas);
//        Funcionario vendedorMaisPago = empresa.calcularMelhorVendedor(LocalDate.of(2022, 4, 1), vendas);

//
//        System.out.println();
//        System.out.println("Funcionário com benefícios que mais recebeu no mês: " + funcionarioMaisPagoCB.getNome());
//        System.out.println();
//
//        if(vendedorMaisPago != null) {
//            System.out.println("Vendedor mais bem pago: " + vendedorMaisPago.getNome());
//            System.out.println();
//        } else {
//            System.out.println("Não existiram vendas nesse mês");
//        }
    }
}