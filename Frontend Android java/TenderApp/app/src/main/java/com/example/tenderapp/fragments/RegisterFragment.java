package com.example.tenderapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tenderapp.R;
import com.example.tenderapp.communication.SocketClient;
import com.example.tenderapp.entities.User;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        EditText userameEditText = view.findViewById(R.id.usernameEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);

        SocketClient socketClient = new SocketClient(getContext());
        view.findViewById(R.id.registratiButton).setOnClickListener(view1 -> {
            String username = userameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(!username.isEmpty() && !password.isEmpty()) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                if (socketClient.requestRegistrazione(user)) {
                    Toast.makeText(getContext(), "Registrato", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.loginFragment2);
                }else
                    Toast.makeText(getContext(), "Username già esistente", Toast.LENGTH_SHORT).show();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.loginFragment2);
            }
        });

        return view;
    }
}