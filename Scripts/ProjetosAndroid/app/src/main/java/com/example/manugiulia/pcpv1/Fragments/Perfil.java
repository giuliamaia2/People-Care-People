package com.example.manugiulia.pcpv1.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Perfil extends Fragment implements View.OnClickListener {

    FirebaseDatabase database;
    DatabaseReference users,info;
    TextView txtNome, txtDiario, txtMetas;
    CircularImageView perfilImagem;
    FirebaseAuth mAuth;
    String UID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Diario diario = new Diario();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        ft.replace(R.id.perfilFrame,diario);
        ft.commit();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");
        UID = mAuth.getCurrentUser().getUid();
        info = users.child(UID);
        Typeface spotify = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_bold.ttf");
        Typeface spotifyLight = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/gotham_light.ttf");

        txtNome = (TextView)view.findViewById(R.id.perfil_txtUserName);
        txtDiario = (TextView)view.findViewById(R.id.perfil_txtDiario);
        txtMetas = (TextView)view.findViewById(R.id.perfil_txtMetas);
        perfilImagem = (CircularImageView) view.findViewById(R.id.perfil_imagem);

        txtNome.setTypeface(spotify);
        txtDiario.setTypeface(spotifyLight);
        txtMetas.setTypeface(spotifyLight);

        txtDiario.setOnClickListener(this);
        txtMetas.setOnClickListener(this);

        txtNome.setText(mAuth.getCurrentUser().getDisplayName());
        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtNome.setText(dataSnapshot.child("Dados").child("apelido").getValue().toString());
                final String url = dataSnapshot.child("Dados").child("imagem").getValue().toString();
                //Picasso.with(getApplicationContext()).load(url).resize(1080,1920).centerInside().into(perfilImagem);
                Picasso.with(getContext())
                        .load(url).resize(1080,1920).centerInside()
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(perfilImagem, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                //Try again online if cache failed
                                Picasso.with(getContext())
                                        .load(url)
                                        .error(R.drawable.ic_menu_camera)
                                        .into(perfilImagem, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.v("Picasso","Could not fetch image");
                                            }
                                        });
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Picasso.with(getActivity()).load(R.drawable.starbucks_logo).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(perfilImagem);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i)
        {
            case R.id.perfil_txtDiario:
                Diario diario = new Diario();
                FragmentTransaction ftDiario = getFragmentManager().beginTransaction();
                ftDiario.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                ftDiario.replace(R.id.perfilFrame,diario);
                ftDiario.commit();
                return;
            case R.id.perfil_txtMetas:
                Metas metas = new Metas();
                FragmentTransaction ftMetas = getFragmentManager().beginTransaction();
                ftMetas.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                ftMetas.replace(R.id.perfilFrame,metas);
                ftMetas.commit();
        }
    }


}
