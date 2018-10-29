package com.example.manugiulia.pcpv1.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Activities.AddRelatoActivity;
import com.example.manugiulia.pcpv1.Activities.EntradaDiarioDetalhesActivity;
import com.example.manugiulia.pcpv1.Interface.ItemClickListener;
import com.example.manugiulia.pcpv1.Modelos.EntradaDiario;
import com.example.manugiulia.pcpv1.R;
import com.example.manugiulia.pcpv1.ViewHolders.DiarioViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class Diario extends android.support.v4.app.Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference entradas;
    FloatingActionButton btnAddEntrada;
    RecyclerView recycler_entradas;
    RecyclerView.LayoutManager layoutManager;
    String UID;
    FirebaseRecyclerAdapter<EntradaDiario,DiarioViewHolder> entradasAdapter;

    public Diario(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diario,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        UID = mAuth.getCurrentUser().getUid();
        entradas = database.getReference("User").child(UID).child("Entradas");
        Typeface spotify = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_bold.ttf");
        Typeface spotifyLight = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_light.ttf");

;       btnAddEntrada = (FloatingActionButton) getView().findViewById(R.id.btn_addEntradaDiario);
//        btnAddEntrada.setTypeface(spotify);
        btnAddEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.add_entrada_diario_item, null);
                final EditText edtData = (EditText) mView.findViewById(R.id.edtData);
                final FButton btnAdd = (FButton) mView.findViewById(R.id.btnAddEntrada);
                final EditText edtTexto = (EditText) mView.findViewById(R.id.edtTexto);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EntradaDiario entradaDiario = new EntradaDiario(edtData.getText().toString(), edtTexto.getText().toString());
                        entradas.push().setValue(entradaDiario);
                        Toast.makeText(getActivity(), "Entrada adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        recycler_entradas = (RecyclerView)getView().findViewById(R.id.recycler_diario);
        recycler_entradas.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_entradas.setLayoutManager(layoutManager);

        loadEntradas();
    }

    private void loadEntradas()
    {
        entradasAdapter = new FirebaseRecyclerAdapter<EntradaDiario, DiarioViewHolder>(EntradaDiario.class,R.layout.entrada_diario_item,DiarioViewHolder.class,entradas) {
            @Override
            protected void populateViewHolder(final DiarioViewHolder viewHolder, final EntradaDiario model, int position) {
                viewHolder.txtData.setText(model.getData());
                viewHolder.txtPreview.setText(model.getTexto());
                //Picasso.with(getActivity()).load(model.getImagem()).into(viewHolder.imgConquista);
                final EntradaDiario clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent entradaInfo = new Intent(getActivity(),EntradaDiarioDetalhesActivity.class);
                        entradaInfo.putExtra("GuiaId",entradasAdapter.getRef(position).getKey());
                        startActivity(entradaInfo);
                    }
                });
            }
        };
        recycler_entradas.setAdapter(entradasAdapter);
    }
}
