package com.example.tenderapp.recyclerItemDesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {
    private final List<Drink> drinkList;

    public CartItemAdapter(List<Drink> drinkList){
        this.drinkList=drinkList;
    }
    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cart_item,parent,false);
        return new CartItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        holder.tipoDrink.setText(drinkList.get(position).getCategoria().toString());
        holder.nomeDrink.setText(drinkList.get(position).getNomeDrink());
        holder.costoDrink.setText(String.format(Locale.getDefault(),"$%.2f",drinkList.get(position).getCosto()));
        holder.floatingActionButton.setOnClickListener(view -> {
            Order.getInstance().getDrinkList().remove(drinkList.get(holder.getAdapterPosition()));
            Toast.makeText(view.getContext(), "Drink rimosso dal carrello", Toast.LENGTH_SHORT).show();
            notifyItemRemoved(holder.getAdapterPosition());
            TextView textViewTotaleCostoLabel = view.getRootView().findViewById(R.id.textViewTotaleCosto);
            String tot = String.format(Locale.getDefault(),"$%.2f",Order.getInstance().getTotale());
            textViewTotaleCostoLabel.setText(tot);
        });
    }

    @Override
    public int getItemCount() {
        if(drinkList == null) return 0;
        return drinkList.size();
    }


    public static class CartItemHolder extends RecyclerView.ViewHolder{
        private final FloatingActionButton floatingActionButton;
        private final TextView nomeDrink;
        private final TextView tipoDrink;
        private final TextView costoDrink;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            tipoDrink = itemView.findViewById(R.id.textViewTipoDrinkCart);
            nomeDrink = itemView.findViewById(R.id.textViewNomeDrinkCart);
            costoDrink = itemView.findViewById(R.id.textViewCostoCart);
            floatingActionButton = itemView.findViewById(R.id.floatingActionButtonRemove);

        }
    }
}
