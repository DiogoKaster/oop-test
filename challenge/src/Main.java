import com.google.gson.JsonArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repositorio = new Repository();
        JsonArray funcionarios = repositorio.getFuncionarios();
        JsonArray vendas = repositorio.getVendas();

        Empresa empresa = new Empresa(funcionarios, vendas);

        double valorTotalPagoSB = empresa.calcularValorPagoNoMes(LocalDate.of(2015, 12, 1), false);
        double valorTotalPagoCB = empresa.calcularValorPagoNoMes(LocalDate.of(2015, 12, 1), true);
        double valorPagoEmBeneficios = empresa.calcularValorTotalEmBeneficios(LocalDate.of(2015, 12, 1));
        Funcionario funcionarioMaisPagoSB = empresa.calcularMaiorSalarioSB(LocalDate.of(2020, 1, 1));
        Funcionario funcionarioMaisPagoCB = empresa.calcularMaiorBeneficio(LocalDate.of(2020, 1, 1));
        Funcionario vendedorMaisPago = empresa.calcularMelhorVendedor(LocalDate.of(2021, 12, 1));

        System.out.println();
        System.out.println("Valor total pago sem benefícios: " + valorTotalPagoSB);
        System.out.println();
        System.out.println("Valor total pago com benefícios: " + valorTotalPagoCB);
        System.out.println();
        System.out.println("Valor total pago em benefícios no mês: " + valorPagoEmBeneficios);

        System.out.println();
        if(funcionarioMaisPagoSB != null) {
            System.out.println("Funcionário sem benefícios que mais recebeu no mês: " + funcionarioMaisPagoSB.getNome());
        } else {
            System.out.println("Não existe nenhum funcionário com benefícios que trabalhou nesse mês/ano.");
        }

        System.out.println();
        if(funcionarioMaisPagoCB != null) {
            System.out.println("Funcionário com benefícios que mais recebeu no mês: " + funcionarioMaisPagoCB.getNome());
        } else {
            System.out.println("Não existe nenhum funcionário com benefícios que trabalhou nesse mês/ano.");
        }

        System.out.println();
        if(vendedorMaisPago != null) {
            System.out.println("Vendedor mais bem pago: " + vendedorMaisPago.getNome());
        } else {
            System.out.println("Não existiram vendas nesse mês");
        }
    }
}