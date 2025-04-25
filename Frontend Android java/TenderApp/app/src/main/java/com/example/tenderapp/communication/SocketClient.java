package com.example.tenderapp.communication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.tenderapp.entities.Drink;
import com.example.tenderapp.entities.Images;
import com.example.tenderapp.entities.Ingredients;
import com.example.tenderapp.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketClient {
    private final Context context;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    public SocketClient(Context context) {
        this.context = context;
    }

    private void initSocket() {
        if(socket == null) {
            try {
                InetAddress serverAddress = InetAddress.getByName("Inserire indirizzo ip");
                socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(serverAddress,8080);
                socket.connect(socketAddress,5000);
                writer = new PrintWriter(socket.getOutputStream(),true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, "connessione al server fallita", Toast.LENGTH_SHORT).show());
            }
        }
    }
    public String getUsernameFromPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("info",Context.MODE_PRIVATE);
        return sharedPreferences.getString("username","UTENTE");
    }

    private void sendUsername() {
        String username = getUsernameFromPreferences();
        writer.println(username);
    }

    private boolean checkResponseOK() {
        String response = "";
        try {
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.equals("ok");
    }


    public void requestLogin() {
        String LOGIN = "login";
        writer.println(LOGIN);
    }

    public boolean checkRegistration(){
        String response = "";
        try {
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.equals("Registration");
    }

    public void requestRegistrazione() {
        String REGISTRAZIONE = "registrazione";
        writer.println(REGISTRAZIONE);
    }
    public void requestAggiungiDenaro() {
        String AGGIUNGI_DENARO = "AggiungiDenaro";
        writer.println(AGGIUNGI_DENARO);
    }


    public void requestHomepage() {
        String HOMEPAGE = "home";
        writer.println(HOMEPAGE);
    }

    public void requestCocktail() {
        String COCKTAIL = "cocktail";
        writer.println(COCKTAIL);
    }

    public void requestFrullati() {
        String FRULLATO = "frullato";
        writer.println(FRULLATO);
    }

    public void requestIngredienti(){
        String INGREDIENTI = "ingredienti";
        writer.println(INGREDIENTI);
    }

    public void requestAcquista() {
        String ACQUISTA = "buy";
        writer.println(ACQUISTA);
    }
    public void requestUpdateVendite(){
        String UPDATE = "update_vendite";
        writer.println(UPDATE);
    }

    public String[] deserializeUser(){
        String response = "";
        try {
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.equals("noLogin")) return null;
        return response.split(";");

    }

    public boolean requestLogin(User user){
        AtomicBoolean flag = new AtomicBoolean(false);
        Runnable login = () -> {
            initSocket();
            if(socket.isConnected()){
                writer.println(user.getUsername());
                if(checkResponseOK()) {
                    requestLogin();
                    if (checkResponseOK()) {
                        writer.println(user.getUsername() + ";" + user.getPassword());
                        String [] userDeserialized = deserializeUser();
                        if (userDeserialized !=null) {
                            flag.set(true);
                            user.setPortafoglio(Float.parseFloat(userDeserialized[2]));
                        }
                    }
                }
            }
            socket=null;
        };
        Thread t = new Thread(login);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag.get();
    }

    public boolean requestRegistrazione(User user){
        AtomicBoolean flag = new AtomicBoolean(false);
        Runnable registration = () ->{
            initSocket();
            if(socket.isConnected()){
                writer.println(user.getUsername());
                if(checkResponseOK()){
                    requestRegistrazione();
                    if(checkResponseOK()) {
                        writer.println(user.getUsername() + ";" + user.getPassword());
                        if (checkRegistration()) {
                            flag.set(true);
                        }
                    }
                }
            }
            socket=null;
        };
        Thread t = new Thread(registration);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag.get();
    }




    public void requestAggiungiDenaro(double denaro){
        Runnable addMoneyThread = () -> {
            initSocket();
            if(socket.isConnected()){
                sendUsername();
                if(checkResponseOK()){
                    requestAggiungiDenaro();
                    if(checkResponseOK()){
                        writer.println(getUsernameFromPreferences() + ";" + denaro);
                    }
                }
            }
            socket = null;
        };
        new Thread(addMoneyThread).start();
    }

    public List<Drink> deserializeDrinks() {
        String s = "";
        List<Drink> drinks = new ArrayList<>();
        while (s != null) {
            try {
                s = reader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(s!= null ){
                String[] arr = s.split(";", 5);
                if(arr.length == 1) break;
                Drink drink = new Drink(arr[0],arr[1], Integer.parseInt(arr[2]),Float.parseFloat(arr[3]), Integer.parseInt(arr[4]), Images.randomImage());
                drinks.add(drink);
            }
        }
        return drinks;
    }

    public List<Ingredients> deserializeIngredients() {
        String s = "";
        List<Ingredients> ingredients = new ArrayList<>();
        while (s != null) {
            try {
                s = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(s!= null ){
                String[] arr = s.split(";", 2);
                if(arr.length == 1) break;
                    Ingredients ingredient = new Ingredients(arr[0],arr[1]);
                    ingredients.add(ingredient);

            }


        }
        return ingredients;
    }

    public void startHomePage(List<Drink> drinks, List<Ingredients> ingredients){
        Runnable homepage = () -> {
            initSocket();
            if(socket.isConnected()){
                sendUsername();
                if(checkResponseOK()){
                    requestHomepage();
                    drinks.addAll(deserializeDrinks());
                    requestIngredienti();
                    ingredients.addAll(deserializeIngredients());

                }
            }
            socket = null;
        };
        Thread t = new Thread(homepage);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void retriveveCocktails(List<Drink> drinks, List<Ingredients> ingredients){
        Runnable cocktail = () -> {
            initSocket();
            if(socket.isConnected()){
                sendUsername();
                if(checkResponseOK()){
                    requestCocktail();
                    drinks.addAll(deserializeDrinks());
                    requestIngredienti();
                    ingredients.addAll(deserializeIngredients());
                }
            }
            socket = null;
        };
        Thread t = new Thread(cocktail);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startFrullato(List<Drink> drinks, List<Ingredients> ingredients){
        Runnable frullato = () -> {
            initSocket();
            if(socket.isConnected()){
                sendUsername();
                if(checkResponseOK()){
                    requestFrullati();
                    drinks.addAll(deserializeDrinks());
                    requestIngredienti();
                    ingredients.addAll(deserializeIngredients());
                }
            }
            socket = null;
        };
        Thread t = new Thread(frullato);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String serializeDrinkName(List<Drink> drinks){
        StringBuilder s = new StringBuilder();
        for(Drink d : drinks){
            s.append(d.getNomeDrink()).append(";");
        }
        return s.toString();
    }
    public void startBuy(float denaro,List<Drink> drinks){
        Runnable buyThread = () -> {
            initSocket();
            if(socket.isConnected()){
                sendUsername();
                if (checkResponseOK()){
                    requestAcquista();
                    if (checkResponseOK()){
                        writer.println(getUsernameFromPreferences() + ";" + denaro);
                        if(checkResponseOK()){
                            requestUpdateVendite();
                            if(checkResponseOK())
                                writer.println(serializeDrinkName(drinks));
                        }
                    }
                }
            }
            socket = null;
        };
        Thread t = new Thread(buyThread);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
