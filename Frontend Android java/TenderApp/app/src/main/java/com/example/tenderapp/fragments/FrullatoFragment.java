package com.example.tenderapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenderapp.R;
import com.example.tenderapp.communication.SocketClient;
import com.example.tenderapp.entities.Drink;
import com.example.tenderapp.entities.Ingredients;
import com.example.tenderapp.recyclerItemDesign.FrullatoItemAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FrullatoFragment extends Fragment {
    public static FrullatoFragment newInstance() {
        Bundle args = new Bundle();
        FrullatoFragment fragment = new FrullatoFragment();
        
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frullato, container, false);
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        List<Drink> listDrinks = new ArrayList<>();
        List<Ingredients> listIngredient = new ArrayList<>();
        SocketClient socketClient = new SocketClient(getContext());
        socketClient.startFrullato(listDrinks,listIngredient);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFrullatoItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new FrullatoItemAdapter(listDrinks));
        for (Drink d:listDrinks) {
            d.addIngredientList(listIngredient);
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
        return view ;
    }
}
