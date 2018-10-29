package com.example.manugiulia.pcpv1.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class FragmentSobreNos extends Fragment implements View.OnClickListener {

    private CircularImageView imgManu, imgGiulia;
    private ImageView imgMigas;
    private TextView txtTitulo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sobre_nos,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgManu = (CircularImageView)view.findViewById(R.id.fragmentSobreNos_imagem1);
        imgGiulia = (CircularImageView)view.findViewById(R.id.fragmentSobreNos_imagem2);
        imgMigas = (ImageView) view.findViewById(R.id.fragmentSobreNos_imagem3);

        txtTitulo = (TextView)view.findViewById(R.id.fragmentSobreNos_titulo);

        Typeface ameliaPond = Typeface.createFromAsset(getActivity().getAssets(), "Fontes/amelia_pond.ttf");

        txtTitulo.setTypeface(ameliaPond);

        Picasso.with(getActivity()).load(R.drawable.imagem_manu).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(imgManu);
        Picasso.with(getActivity()).load(R.drawable.imagem_giulia).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(imgGiulia);
        Picasso.with(getActivity()).load(R.drawable.imagem_migas).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(imgMigas);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i)
        {

        }
    }
}
