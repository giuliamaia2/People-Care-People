package com.example.manugiulia.pcpv1.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manugiulia.pcpv1.Activities.AddForumActivity;
import com.example.manugiulia.pcpv1.Activities.AddRelatoActivity;
import com.example.manugiulia.pcpv1.Activities.ForumDetalhesActivity;
import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.Modelos.Forum;
import com.example.manugiulia.pcpv1.Modelos.Relatos;
import com.example.manugiulia.pcpv1.R;
import com.example.manugiulia.pcpv1.ViewHolders.ForumViewHolder;
import com.example.manugiulia.pcpv1.ViewHolders.RelatosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class FragmentRelatos extends android.support.v4.app.Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference relatos;
    FloatingActionButton btnAddRelato;
    RecyclerView recycler_relatos;
    RecyclerView.LayoutManager layoutManager;
    String UID;
    FirebaseRecyclerAdapter<Relatos,RelatosViewHolder> relatosAdapter;

    public FragmentRelatos(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_relatos,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        UID = mAuth.getCurrentUser().getUid();
        relatos = database.getReference("Relatos");
        btnAddRelato = (FloatingActionButton) getView().findViewById(R.id.btnAdd_relato);
        btnAddRelato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRelatoActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        recycler_relatos = (RecyclerView)getView().findViewById(R.id.recycler_relatos);
        recycler_relatos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_relatos.setLayoutManager(layoutManager);

        loadRelatos();
    }
    private void loadRelatos()
    {
        relatosAdapter = new FirebaseRecyclerAdapter<Relatos, RelatosViewHolder>(Relatos.class,R.layout.relatos_item,RelatosViewHolder.class,relatos) {
            @Override
            protected void populateViewHolder(final RelatosViewHolder viewHolder, final Relatos model, int position) {
                viewHolder.txtHora.setText(model.getHora());
                viewHolder.txtAutor.setText(model.getAutor());
                viewHolder.txtData.setText(model.getData());
                viewHolder.txtTexto.setText(model.getTexto());
                Picasso.with(getActivity()).load(model.getImagem()).resize(720,1080).centerInside().into(viewHolder.imgRelato);
                final Relatos clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent entradaInfo = new Intent(getActivity(),ForumDetalhesActivity.class);
//                        entradaInfo.putExtra("GuiaId",entradasAdapter.getRef(position).getKey());
//                        startActivity(entradaInfo);
                    }
                });
            }
        };
        recycler_relatos.setAdapter(relatosAdapter);
    }

}
