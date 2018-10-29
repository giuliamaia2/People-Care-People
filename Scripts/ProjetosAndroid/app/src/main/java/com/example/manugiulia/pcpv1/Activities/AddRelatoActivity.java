package com.example.manugiulia.pcpv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Modelos.Forum;
import com.example.manugiulia.pcpv1.Modelos.Relatos;
import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;

public class AddRelatoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference relatos,info,info2,imagens;
    FButton btnAddRelatos;
    String UID,UID2;
    CircularImageView imgRelato;
    EditText edtTexto;
    Button btnFechar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_relatos);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        relatos=database.getReference("Relatos");
        UID = mAuth.getCurrentUser().getDisplayName();
        UID2 = mAuth.getCurrentUser().getUid();
        info = database.getReference("User").child(UID);
        info2 = database.getReference("User").child(UID2);
        imagens = database.getReference("Imagens");
        edtTexto = (EditText) findViewById(R.id.relatos_Texto);
        imgRelato = (CircularImageView)findViewById(R.id.relatos_Imagem);
        if(mAuth.getCurrentUser().getUid().equals("TyTWZIDPr6Ve0zAE4BdzjqSTILZ2")) {
            Picasso.with(this).load("https://i.imgur.com/IVtCJKb.jpg").resize(720, 1080).centerInside().into(imgRelato);
        }else if(mAuth.getCurrentUser().getUid().equals("ld9xTdi8SJbV7zkpoi2lXac8yn32"))
        {
            Picasso.with(this).load("https://i.imgur.com/QX0LWpw.jpg").resize(720, 1080).centerInside().into(imgRelato);

        }
        btnFechar = (Button)findViewById(R.id.btnFechar_Relatos);
        btnAddRelatos = (FButton)findViewById(R.id.btn_addRelato);

        btnAddRelatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = edtTexto.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                String stringHora = hora.format(calendar.getTime());
                String stringData = data.format(calendar.getTime());
                String autor = UID;
                if(mAuth.getCurrentUser().getUid().equals("TyTWZIDPr6Ve0zAE4BdzjqSTILZ2")) {
                    Relatos relatos1 = new Relatos(autor,stringData,stringHora,texto,"https://i.imgur.com/IVtCJKb.jpg");
                    relatos.push().setValue(relatos1);
                }else if(mAuth.getCurrentUser().getUid().equals("ld9xTdi8SJbV7zkpoi2lXac8yn32"))
                {
                    Relatos relatos1 = new Relatos(autor,stringData,stringHora,texto,"https://i.imgur.com/QX0LWpw.jpg");
                    relatos.push().setValue(relatos1);
                }
                Toast.makeText(getApplicationContext(),"Opa, aí deu bom!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddRelatoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Relato Cancelado.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddRelatoActivity.this,MainActivity.class);
                intent.putExtra("fragment",3);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(AddRelatoActivity.this,MainActivity.class);
        home.putExtra("fragment",3);
        startActivity(home);
        finish();
    }
}

