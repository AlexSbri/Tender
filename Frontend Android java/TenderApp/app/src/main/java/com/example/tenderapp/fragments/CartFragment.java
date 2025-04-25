package com.example.tenderapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenderapp.R;
import com.example.tenderapp.communication.SocketClient;
import com.example.tenderapp.entities.Drink;
import com.example.tenderapp.entities.Order;
import com.example.tenderapp.recyclerItemDesign.CartItemAdapter;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    private TextView textViewTotale;
    private String totale="0.00";
    private float portafoglio;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        textViewTotale = view.findViewById(R.id.textViewTotaleCosto);
        totale = String.format(Locale.getDefault(),"$%.2f",Order.getInstance().getTotale());
        textViewTotale.setText(totale);

        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        portafoglio = preferences.getFloat("portafoglio",0.0f);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCartItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        SocketClient socketClient =new SocketClient(getContext());
        RecyclerView.Adapter<CartItemAdapter.CartItemHolder> adapter = new CartItemAdapter(Order.getInstance().getDrinkList());
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.buttonBuy).setOnClickListener(view1 -> {
            Order order = Order.getInstance();
            List<Drink> drinkList = order.getDrinkList();
            if(!drinkList.isEmpty()) {
                float totaleCarrello = order.getTotale();
                if(totaleCarrello <= portafoglio) {
                    int size = drinkList.size();
                    totale = String.format(Locale.getDefault(),"$%.2f",totaleCarrello);
                    editor.putFloat("portafoglio",portafoglio - totaleCarrello);
                    editor.apply();
                    socketClient.startBuy(totaleCarrello, drinkList);
                    drinkList.clear();
                    textViewTotale.setText(R.string.zero);
                    adapter.notifyItemRangeRemoved(0, size);
                    Toast.makeText(getContext(), "Ordine andato a buon fine", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Soldi insufficienti", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
        return view;
    }

}
