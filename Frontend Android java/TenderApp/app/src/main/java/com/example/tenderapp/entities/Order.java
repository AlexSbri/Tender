package com.example.tenderapp.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static Order instance = null;
    private User user;
    private List<Drink> drinkList;

    private Order(){
        drinkList = new ArrayList<>();
    }

    public static Order getInstance() {
        if(instance == null) instance = new Order();
        return instance;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }

    public User getUser() {
        return user;
    }


    public float getTotale(){
        return (float) drinkList.stream().mapToDouble(Drink::getCosto).sum();
    }
}
