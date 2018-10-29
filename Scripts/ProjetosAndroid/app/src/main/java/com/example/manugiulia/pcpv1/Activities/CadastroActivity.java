package com.example.manugiulia.pcpv1.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.Modelos.Usuario;
import com.example.manugiulia.pcpv1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseDatabase database;

    private DatabaseReference info,users;

    String UID;

    static String LoggedIn_User_Email;

    ImageView logo;

    RelativeLayout layout;

    MaterialEditText txtNome, txtApelido, txtDataDeNasc, txtEmail, txtSenha;

    FButton btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        users=database.getReference("Usuarios");

        layout = (RelativeLayout) findViewById(R.id.activityCadastroFundo);
        layout.setBackgroundResource(R.drawable.fundo_1);

        logo = (ImageView) findViewById(R.id.cadastroLogo);
        Picasso.with(this).load(R.drawable.logo_manu).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(logo);

        txtNome = (MaterialEditText)findViewById(R.id.txt_Nome);
        txtApelido = (MaterialEditText)findViewById(R.id.txt_Apelido);
        txtDataDeNasc = (MaterialEditText)findViewById(R.id.txt_DataDeNasc);
        txtEmail = (MaterialEditText)findViewById(R.id.txt_Email);
        txtSenha = (MaterialEditText)findViewById(R.id.txt_Senha);

        btnCadastrar = (FButton)findViewById(R.id.btn_Cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getnome = txtApelido.getText().toString();
                String getemail = txtEmail.getText().toString().trim();
                String getpassword = txtSenha.getText().toString().trim();
                if (getemail.matches("")) {
                    Toast.makeText(CadastroActivity.this, "Insira seu email!", Toast.LENGTH_SHORT).show();
                } else if (getpassword.matches("")) {
                    Toast.makeText(CadastroActivity.this, "Insira sua senha!", Toast.LENGTH_SHORT).show();
                } else if (getnome.matches("")) {
                    Toast.makeText(CadastroActivity.this, "Insira seu nome!", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(getemail, getpassword);
                }
            }
        });


    }

    private void signUp(String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Aguarde...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(CadastroActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                        } else {
                            userProfile();
                            Toast.makeText(CadastroActivity.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            database = FirebaseDatabase.getInstance();
                            users = database.getReference("User");
                            if(mAuth.getCurrentUser() != null)
                            {
                                UID = mAuth.getCurrentUser().getUid();
                            }
                            info = users.child(UID);
                            Usuario usuario = new Usuario(txtNome.getText().toString(),txtApelido.getText().toString(),txtDataDeNasc.getText().toString(),
                                    txtEmail.getText().toString(),txtSenha.getText().toString(),"");
                            users.child(UID).child("Dados").setValue(usuario);
                            /*user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CadastroActivity.this, "Verifique seu email", Toast.LENGTH_SHORT).show();
                                }
                            });*/
                            Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(login);
                            finish();
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(txtApelido.getText().toString().trim())
//.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
