package com.example.manugiulia.pcpv1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Modelos.Usuario;
import com.example.manugiulia.pcpv1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class EditarPerfil extends Fragment {

    EditText edtNome,edtApelido,edtDataDeNasc,edtEmail,edtSenha;
    CircularImageView perfilImagem;
    FButton btnSalvar;
    String UID, Nome, Apelido, DataDeNasc, Email, Senha;
    private FirebaseAuth mAuth;
    DatabaseReference user,info,imagem;
    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_perfil,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null) {
            UID = mAuth.getCurrentUser().getUid();
        }
        info = user.child(UID);

        edtNome = (EditText)view.findViewById(R.id.editarPerfil_edtNome);
        edtApelido = (EditText)view.findViewById(R.id.editarPerfil_edtApelido);
        edtDataDeNasc = (EditText)view.findViewById(R.id.editarPerfil_edtDataDeNasc);
        edtEmail = (EditText)view.findViewById(R.id.editarPerfil_edtEmail);
        edtSenha = (EditText)view.findViewById(R.id.editarPerfil_edtSenha);

        perfilImagem = (CircularImageView)view.findViewById(R.id.perfilImagem);
        Picasso.with(getActivity()).load(R.drawable.starbucks_logo).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(perfilImagem);

        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Nome = dataSnapshot.child("Dados").child("nome").getValue().toString();
                    edtNome.setText(Nome);
                    if (mAuth.getCurrentUser() != null) {
                        Apelido = mAuth.getCurrentUser().getDisplayName();
                    }
                    edtApelido.setText(Apelido);
                    DataDeNasc = dataSnapshot.child("Dados").child("dataDeNasc").getValue().toString();
                    edtDataDeNasc.setText(DataDeNasc);
                    Email = mAuth.getCurrentUser().getEmail();
                    edtEmail.setText(Email);
                    Senha = dataSnapshot.child("Dados").child("senha").getValue().toString();
                    edtSenha.setText(Senha);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSalvar = (FButton)view.findViewById(R.id.salvarPerfil);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuarios = new Usuario(edtNome.getText().toString(),edtApelido.getText().toString(),edtDataDeNasc.getText().toString(),
                        edtEmail.getText().toString(),edtSenha.getText().toString(),"");
                //Usuarios usuarios = new Usuarios(edtNome.getText().toString(),Integer.parseInt(edtSaldo.getText().toString()),edtPeso.getText().toString(),edtAltura.getText().toString(),edtSituacao.getText().toString(),
                user.child(UID).child("Dados").setValue(usuarios);
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(edtApelido.getText().toString().trim())
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TESTING", "User profile updated.");
                                    }
                                }
                            });
                }
                Toast.makeText(getActivity(),"Dados alterados com sucesso!",Toast.LENGTH_SHORT).show();
                Perfil home = new Perfil();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.contentMain,home);
                ft.commit();
            }
        });

            }

    }

