package com.example.manugiulia.pcpv1.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Activities.AddForumActivity;
import com.example.manugiulia.pcpv1.Activities.EntradaDiarioDetalhesActivity;
import com.example.manugiulia.pcpv1.Activities.ForumDetalhesActivity;
import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.Modelos.EntradaDiario;
import com.example.manugiulia.pcpv1.Modelos.Forum;
import com.example.manugiulia.pcpv1.R;
import com.example.manugiulia.pcpv1.ViewHolders.DiarioViewHolder;
import com.example.manugiulia.pcpv1.ViewHolders.ForumViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hoang8f.widget.FButton;

public class FragmentForum extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference forum;
    FloatingActionButton btnAddEntrada;
    RecyclerView recycler_entradas;
    RecyclerView.LayoutManager layoutManager;
    String UID;
    FirebaseRecyclerAdapter<Forum,ForumViewHolder> entradasAdapter;

    public FragmentForum(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forum,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        UID = mAuth.getCurrentUser().getUid();
        forum = database.getReference("Forum");
        btnAddEntrada = (FloatingActionButton) getView().findViewById(R.id.btnAddForumItem);
        btnAddEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddForumActivity.class);
            startActivity(intent);
            getActivity().finish();
            }
        });
        recycler_entradas = (RecyclerView)getView().findViewById(R.id.recycler_forum);
        recycler_entradas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_entradas.setLayoutManager(layoutManager);

        loadForum();
    }
    private void loadForum()
    {
        entradasAdapter = new FirebaseRecyclerAdapter<Forum, ForumViewHolder>(Forum.class,R.layout.forum_item,ForumViewHolder.class,forum) {
            @Override
            protected void populateViewHolder(final ForumViewHolder viewHolder, final Forum model, int position) {
                viewHolder.txtTItulo.setText(model.getTitulo());
                viewHolder.txtData.setText(model.getData());
                viewHolder.txtCriadoPor.setText("Criado por: "+model.getAutor());
                viewHolder.txtPreview.setText(model.getTexto());
                //Picasso.with(getActivity()).load(model.getImagem()).into(viewHolder.imgConquista);
                final Forum clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent entradaInfo = new Intent(getActivity(),ForumDetalhesActivity.class);
                        entradaInfo.putExtra("GuiaId",entradasAdapter.getRef(position).getKey());
                        startActivity(entradaInfo);
                    }
                });
            }
        };
        recycler_entradas.setAdapter(entradasAdapter);
    }
}
