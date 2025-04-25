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

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeItemHolder> {
    private final List<Drink> drinkList;

    public HomeItemAdapter(List<Drink> drinkList)
    {
        this.drinkList=drinkList;
    }
    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_item,parent,false);
        return new HomeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder holder, int position) {
        holder.tipoDrink.setText(drinkList.get(position).getCategoria().toString());
        holder.nomeDrink.setText(drinkList.get(position).getNomeDrink());
        holder.descrizioneDrink.setText(drinkList.get(position).getDescrizione());
        holder.costo = drinkList.get(position).getCosto();
        holder.costoDrink.setText(String.format(Locale.getDefault(),"$%.2f", holder.costo));
        holder.venditeDrink.setText(String.format(Locale.getDefault(),"%d",drinkList.get(position).getVendite()));
        holder.imageViewDrink.setImageResource(drinkList.get(position).getImage());
        holder.ingredientList.setText(drinkList.get(position).ingredientListToString());

    }

    @Override
    public int getItemCount() {
        if(drinkList.isEmpty())return 0;
        return drinkList.size();
    }

    public static class HomeItemHolder extends RecyclerView.ViewHolder{
        private final TextView nomeDrink;
        private final TextView descrizioneDrink;
        private final TextView costoDrink;
        private final TextView venditeDrink;
        private final TextView tipoDrink;
        private final TextView ingredientList;

        private final ImageView imageViewDrink;
        private float costo;
        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            tipoDrink = itemView.findViewById(R.id.textViewTipoDrink);
            nomeDrink = itemView.findViewById(R.id.textViewNomeDrink);
            descrizioneDrink = itemView.findViewById(R.id.textViewDescrizione);
            costoDrink = itemView.findViewById(R.id.textViewCosto);
            venditeDrink = itemView.findViewById(R.id.textViewVendite);
            ingredientList = itemView.findViewById(R.id.textViewIngredientList);
            imageViewDrink = itemView.findViewById(R.id.imageViewDrink1);
            FloatingActionButton floatingActionButton = itemView.findViewById(R.id.floatingActionButtonAdd);
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
