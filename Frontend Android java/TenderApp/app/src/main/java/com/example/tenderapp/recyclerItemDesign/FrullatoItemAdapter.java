package com.example.tenderapp.recyclerItemDesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenderapp.R;
import com.example.tenderapp.entities.Drink;
import com.example.tenderapp.entities.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class FrullatoItemAdapter extends RecyclerView.Adapter<FrullatoItemAdapter.FrullatoItemHolder>{
    private final List<Drink> drinkList;

    public FrullatoItemAdapter(List<Drink> drinkList){
        this.drinkList=drinkList;
    }

    @NonNull
    @Override
    public FrullatoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_frullato_item,parent,false);
        return new FrullatoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrullatoItemHolder holder, int position) {
        holder.tipoDrink.setText(drinkList.get(position).getCategoria().toString());
        holder.nomeDrink.setText(drinkList.get(position).getNomeDrink());
        holder.descrizioneDrink.setText(drinkList.get(position).getDescrizione());
        holder.costo = drinkList.get(position).getCosto();
        holder.costoDrink.setText(String.format(Locale.getDefault(),"$%.2f",holder.costo));
        holder.venditeDrink.setText(String.format(Locale.getDefault(),"%d",drinkList.get(position).getVendite()));
        holder.immagineDrink.setImageResource(drinkList.get(position).getImage());
        holder.ingredientList.setText(drinkList.get(position).ingredientListToString());
    }

    @Override
    public int getItemCount() {
        if(drinkList.isEmpty())return 0;
        return drinkList.size();
    }

    public static class FrullatoItemHolder extends RecyclerView.ViewHolder{
        private final TextView nomeDrink;
        private final TextView descrizioneDrink;
        private final TextView costoDrink;
        private final TextView venditeDrink;
        private final TextView tipoDrink;
        private final TextView ingredientList;

        private final ImageView immagineDrink;
        private float costo;

        public FrullatoItemHolder(@NonNull View itemView) {
            super(itemView);
            tipoDrink = itemView.findViewById(R.id.textViewTipoDrinkFrullato);
            nomeDrink = itemView.findViewById(R.id.textViewNomeDrinkFrullato);
            immagineDrink = itemView.findViewById(R.id.imageViewDrinkFrullato);
            descrizioneDrink = itemView.findViewById(R.id.textViewDescrizioneFrullato);
            costoDrink = itemView.findViewById(R.id.textViewCostoFrullato);
            venditeDrink = itemView.findViewById(R.id.textViewVenditeFrullato);
            ingredientList = itemView.findViewById(R.id.textViewIngredientListFrullato);

            FloatingActionButton floatingActionButton = itemView.findViewById(R.id.floatingActionButtonAddFrullato);
            floatingActionButton.setOnClickListener(view -> {
                Drink drink = new Drink();
                drink.setNomeDrink(nomeDrink.getText().toString());
                drink.setDescrizione(descrizioneDrink.getText().toString());
                if(tipoDrink.getText().toString().equals("Cocktail"))
                    drink.setCategoria(Drink.tipoDrink.Cocktail);
                else drink.setCategoria(Drink.tipoDrink.Frullato);
                drink.setCosto(costo);
                Order.getInstance().getDrinkList().add(drink);
                Toast.makeText(view.getContext(), "Drink aggiunto al carrello", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
