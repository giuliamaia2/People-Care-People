package com.example.manugiulia.pcpv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.Modelos.EntradaDiario;
import com.example.manugiulia.pcpv1.Modelos.Forum;
import com.example.manugiulia.pcpv1.Modelos.ForumComentario;
import com.example.manugiulia.pcpv1.R;
import com.example.manugiulia.pcpv1.ViewHolders.ForumComentariosViewHolder;
import com.example.manugiulia.pcpv1.ViewHolders.ForumViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.hoang8f.widget.FButton;

public class ForumDetalhesActivity extends AppCompatActivity {
    TextView txtTItulo,txtPreview,txtCriadoPor,txtData;
    FButton btnAddComentario;
    EditText edtComentario;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnSalvar;
    String UID;
    FirebaseAuth mAuth;
    DatabaseReference entradas,comentarios;
    String guiasId="";

    FirebaseDatabase database;
    DatabaseReference guias;

    RecyclerView recycler_entradas;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<ForumComentario,ForumComentariosViewHolder> entradasAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_detalhes);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        UID = mAuth.getCurrentUser().getDisplayName();
        entradas = database.getReference("Forum");
        if (getIntent() != null)
            guiasId = getIntent().getStringExtra("GuiaId");
        if (!guiasId.isEmpty() && guiasId != null) {
            getEntradaDetail(guiasId);
        }
        comentarios = entradas.child(guiasId).child("Comentarios");

        txtTItulo = (TextView)findViewById(R.id.forum_titulo);
        txtCriadoPor = (TextView)findViewById(R.id.forum_autor);
        txtData = (TextView)findViewById(R.id.forum_data);
        txtPreview = (TextView)findViewById(R.id.forum_texto);

        btnAddComentario = (FButton)findViewById(R.id.btn_addComentario);
        edtComentario = (EditText)findViewById(R.id.edtComentario);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        btnSalvar = (FloatingActionButton)findViewById(R.id.btnSalvar);


        recycler_entradas = (RecyclerView)findViewById(R.id.forumComentarios_recycler);
        recycler_entradas.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recycler_entradas.setLayoutManager(layoutManager);
        loadComentarios();
        btnAddComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = edtComentario.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String hora = sdf.format(calendar.getTime());
                String autor = UID ;
                ForumComentario comentario = new ForumComentario(autor,hora,texto);
                comentarios.push().setValue(comentario);
                edtComentario.setText("");

            }
        });
        loadComentarios();
    }
    private void loadComentarios()
    {
        entradasAdapter = new FirebaseRecyclerAdapter<ForumComentario, ForumComentariosViewHolder>(ForumComentario.class,
                R.layout.forum_comentario,ForumComentariosViewHolder.class,comentarios) {
            @Override
            protected void populateViewHolder(final ForumComentariosViewHolder viewHolder, final ForumComentario model, int position) {
                viewHolder.txtAutor.setText(model.getAutor());
                viewHolder.txtData.setText(model.getData());
                viewHolder.txtTexto.setText(model.getTexto());
                final ForumComentario clickItem = model;
            }
        };
        recycler_entradas.setAdapter(entradasAdapter);
    }


    private void getEntradaDetail(String guiasId)
    {
        entradas.child(guiasId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Forum guias = dataSnapshot.getValue(Forum.class);

                txtTItulo.setText(guias.getTitulo());

                txtCriadoPor.setText(guias.getAutor());

                txtData.setText(guias.getData());

                txtPreview.setText(guias.getTexto());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForumDetalhesActivity.this, MainActivity.class);
        intent.putExtra("fragment",4);
        startActivity(intent);
        finish();
    }
    }

