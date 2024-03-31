package cargos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vendedor extends Cargo{
    public Vendedor() {
        super(12000, 1800, 0.30);
    }

    public double getBonificacaoEmVendas(JsonArray vendas, String nome, LocalDate date) {
        double bonificacaoTotal = 0;
        if(!vendas.isEmpty()) {
            for (int i = 0; i < vendas.size(); i++) {
                JsonObject venda = vendas.get(i).getAsJsonObject();

                String nomeVendedor = venda.get("nome").getAsString();
                if(nomeVendedor.equals(nome)) {
                    JsonObject vendasPorMes = venda.getAsJsonObject("vendas_por_mes");
                    for(String mes : vendasPorMes.keySet()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                        LocalDate dataVenda = LocalDate.parse("01/" + mes, formatter);

                        if(dataVenda.isAfter(date)){
                            bonificacaoTotal += 0;
                        } else {
                            double valorVenda = vendasPorMes.get(mes).getAsDouble();
                            bonificacaoTotal += valorVenda * this.bonificacao;
                        }
                    }
                }
            }
        } else {
            System.out.println("A lista para cadastro está vazia!");
        }

        return bonificacaoTotal;
    }

    public double getBonificacaoNoMes(JsonArray vendas, String nome, LocalDate date) {
        double vendaNoMes = 0;
        for (int i = 0; i < vendas.size(); i++) {
            JsonObject venda = vendas.get(i).getAsJsonObject();
            String nomeVendedor = venda.get("nome").getAsString();

            if (nomeVendedor.equals(nome)) {
                JsonObject vendasPorMes = venda.getAsJsonObject("vendas_por_mes");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                String dataFormatada = date.format(formatter);

                if (vendasPorMes.get(dataFormatada) != null) {
                    vendaNoMes = vendasPorMes.get(dataFormatada).getAsDouble() * this.getBonificacao();
                    System.out.println("A venda no mes foi: " + vendaNoMes);
                }
            }
        }

        return vendaNoMes;
    }

    @Override
    public String toString() {
        return "Vendedor";
    }
}
