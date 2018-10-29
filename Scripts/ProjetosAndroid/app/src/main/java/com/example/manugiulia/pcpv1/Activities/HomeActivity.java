package com.example.manugiulia.pcpv1.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    TextView txtOla;

    private FirebaseAuth mAuth;

    String nomeDoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtOla = (TextView)findViewById(R.id.txtOla);

        Typeface spotify = Typeface.createFromAsset(getAssets(), "Fontes/gotham_bold.ttf");
        Typeface spotifyLight = Typeface.createFromAsset(getAssets(), "Fontes/gotham_light.ttf");

        mAuth = FirebaseAuth.getInstance();
        nomeDoUsuario = mAuth.getCurrentUser().getDisplayName();

        txtOla.setText("Olá, "+nomeDoUsuario+"!");
        txtOla.setTypeface(spotify);

    }
}
