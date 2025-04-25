package com.example.tenderapp.entities;

public class Ingredients {
    private String nomeIngrediente;
    private String nomeDrink;

    public Ingredients(String nomeIngrediente,String nomeDrink) {
        this.nomeIngrediente = nomeIngrediente;
        this.nomeDrink=nomeDrink;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public String getNomeDrink() {
        return nomeDrink;
    }

    public void setNomeDrink(String nomeDrink) {
        this.nomeDrink = nomeDrink;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "nomeIngrediente='" + nomeIngrediente + '\'' +
                ", nomeDrink='" + nomeDrink + '\'' +
                '}';
    }
}
