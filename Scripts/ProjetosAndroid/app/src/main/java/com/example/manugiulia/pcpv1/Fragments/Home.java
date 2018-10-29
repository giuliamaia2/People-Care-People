package com.example.manugiulia.pcpv1.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Activities.MainActivity;
import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends Fragment {

    TextView txtOla,txtHomeDiario;

    private FirebaseAuth mAuth;

    String nomeDoUsuario;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtOla = (TextView)view.findViewById(R.id.txtOla);
        txtHomeDiario = (TextView)view.findViewById(R.id.homeDiario);
        txtHomeDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diario = new Intent(getActivity(),MainActivity.class);
                diario.putExtra("fragment",2);
                startActivity(diario);
            }
        });
        Typeface spotify = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_bold.ttf");
        Typeface spotifyLight = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_light.ttf");

        mAuth = FirebaseAuth.getInstance();
        nomeDoUsuario = mAuth.getCurrentUser().getDisplayName();

        txtOla.setText("Olá, "+nomeDoUsuario+"!");
        txtOla.setTypeface(spotify);
    }
}
