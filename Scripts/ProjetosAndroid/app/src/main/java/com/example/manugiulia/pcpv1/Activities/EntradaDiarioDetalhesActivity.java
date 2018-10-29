package com.example.manugiulia.pcpv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Fragments.Perfil;
import com.example.manugiulia.pcpv1.Modelos.EntradaDiario;
import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class EntradaDiarioDetalhesActivity extends AppCompatActivity {
    TextView guia_name,guia_autor,guia_descricao;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnSalvar;
    String UID;
    FirebaseAuth mAuth;
    DatabaseReference entradas;
    String guiasId="";

    FirebaseDatabase database;
    DatabaseReference guias;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada_diario_detalhes);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        UID = mAuth.getCurrentUser().getUid();
        entradas = database.getReference("User").child(UID).child("Entradas");

        guia_name = (TextView)findViewById(R.id.guia_name);
        guia_autor=(TextView)findViewById(R.id.guia_autor);
        guia_descricao=(TextView)findViewById(R.id.guia_descricao);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        btnSalvar = (FloatingActionButton)findViewById(R.id.btnSalvar);

        if (getIntent() != null)
            guiasId = getIntent().getStringExtra("GuiaId");
        if (!guiasId.isEmpty() && guiasId != null) {
            getEntradaDetail(guiasId);
        }

    }
    private void getEntradaDetail(String guiasId)
    {
        entradas.child(guiasId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EntradaDiario guias = dataSnapshot.getValue(EntradaDiario.class);

                guia_name.setText(guias.getData());

                guia_autor.setText(mAuth.getCurrentUser().getDisplayName());

                guia_descricao.setText(guias.getTexto());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EntradaDiarioDetalhesActivity.this, MainActivity.class);
        intent.putExtra("fragment",2);
        startActivity(intent);
        finish();
    }
}
