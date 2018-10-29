package com.example.manugiulia.pcpv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Modelos.Forum;
import com.example.manugiulia.pcpv1.R;
import com.example.manugiulia.pcpv1.ViewHolders.ForumViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;

public class AddForumActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference forum;
    FButton btnAddForum;
    String UID;
    EditText edtTitulo,edtTexto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_forum);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        forum=database.getReference("Forum");
        UID = mAuth.getCurrentUser().getDisplayName();
        edtTexto = (EditText) findViewById(R.id.edtTexto);
        edtTitulo= (EditText)findViewById(R.id.edtTitulo);

        btnAddForum = (FButton) findViewById(R.id.btnAddForum);

        btnAddForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = edtTitulo.getText().toString();
                String texto = edtTexto.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String hora = sdf.format(calendar.getTime());
                String autor = UID;
                Forum forums = new Forum(autor,hora,texto,titulo);
                        forum.push().setValue(forums);
                        Toast.makeText(getApplicationContext(),"Opa, aí deu bom!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddForumActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(AddForumActivity.this,MainActivity.class);
        home.putExtra("fragment",4);
        startActivity(home);
        finish();
    }
}

