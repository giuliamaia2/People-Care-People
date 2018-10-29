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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manugiulia.pcpv1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;

public class LoginActivity extends AppCompatActivity {

    ImageView logo;
    RelativeLayout layout;
    TextView txtCadastro;
    private FirebaseAuth mAuth;
    MaterialEditText edtSenha, edtEmail;
    FButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layout = (RelativeLayout)findViewById(R.id.activityLoginFundo);
        layout.setBackgroundResource(R.drawable.fundo_1);

        logo = (ImageView)findViewById(R.id.loginLogo);
        Picasso.with(this).load(R.drawable.logo_manu).resize(800,800).networkPolicy(NetworkPolicy.OFFLINE).into(logo);

        txtCadastro = (TextView)findViewById(R.id.activityLogin_txtCadastro);
        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(LoginActivity.this,CadastroActivity.class);
                startActivity(cadastro);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        edtEmail = (MaterialEditText)findViewById(R.id.activityLogin_edtEmail);
        edtSenha = (MaterialEditText)findViewById(R.id.activityLogin_edtSenha);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (FButton)findViewById(R.id.activityLogin_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = edtEmail.getText().toString().trim();
                String getpassword = edtSenha.getText().toString().trim();
                if (getemail.matches("")) {
                    Toast.makeText(LoginActivity.this, "Insira seu email!", Toast.LENGTH_SHORT).show();
                } else if (getpassword.matches("")) {
                    Toast.makeText(LoginActivity.this, "Insira sua senha!", Toast.LENGTH_SHORT).show();
                } else {
                    fazerLogin(getemail, getpassword);
                }
            }
        });

    }

    private void fazerLogin(String email, String password)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Aguarde...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());
                        FirebaseUser user = mAuth.getCurrentUser();

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Senha ou email incorretos!", Toast.LENGTH_SHORT).show();
                        } else {
                            //if (user.isEmailVerified()) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                progressDialog.dismiss();
                            } /*else {
                                progressDialog.dismiss();
                                Toast.makeText(SignIn.this, "Email ainda não verificado.", Toast.LENGTH_SHORT).show();
                            }*/
                        }
                });
    }


}
