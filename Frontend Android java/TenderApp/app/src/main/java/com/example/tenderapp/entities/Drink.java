package com.example.tenderapp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drink {

    private String nomeDrink;
    private String descrizione;
    private List<Ingredients> ingredientsList;

    private int image;

    private int vendite;
    private float costo;
    public enum tipoDrink {Cocktail, Frullato};
    private tipoDrink categoria;

    public Drink(){}

    public Drink(String nomeDrink, String descrizione, int vendite,float costo, int categoria, int image) {
        this.nomeDrink = nomeDrink;
        this.descrizione = descrizione;
        this.vendite = vendite;
        this.costo = costo;
        this.categoria = tipoDrink.values()[categoria];
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getVendite() {
        return vendite;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public tipoDrink getCategoria() {
        return categoria;
    }

    public void setCategoria(tipoDrink categoria) {
        this.categoria = categoria;
    }



    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNomeDrink() {
        return nomeDrink;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setNomeDrink(String nomeDrink) {
        this.nomeDrink = nomeDrink;
    }

    public void addIngredientList(List<Ingredients> ingredientsList){
        this.ingredientsList = new ArrayList<>();
        for (Ingredients i: ingredientsList) {
            if(i.getNomeDrink().equals(nomeDrink))
                this.ingredientsList.add(i);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Objects.equals(nomeDrink, drink.nomeDrink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeDrink);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "nomeDrink='" + nomeDrink + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", vendite=" + vendite +
                ", costo=" + costo +
                ", categoria=" + categoria +
                '}';
    }

    public String ingredientListToString(){

        StringBuilder s = new StringBuilder();
        for (int i = 0;i<ingredientsList.size();i++) {
            if(i==ingredientsList.size()-1) s.append(ingredientsList.get(i).getNomeIngrediente());
            else s.append(ingredientsList.get(i).getNomeIngrediente()).append(",");
        }
        return s.toString();
    }
}
