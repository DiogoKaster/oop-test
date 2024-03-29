package cargos;

import com.google.gson.JsonArray;

public class Vendedor extends Cargo{
    public Vendedor() {
        super(12000, 1800);
    }

    public double calcularBeneficio(JsonArray vendas) {
        return 0;
    }

    @Override
    public String toString() {
        return "Vendedor";
    }
}
