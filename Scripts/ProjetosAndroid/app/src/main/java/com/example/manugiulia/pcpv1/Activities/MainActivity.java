package com.example.manugiulia.pcpv1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.manugiulia.pcpv1.Fragments.EditarPerfil;
import com.example.manugiulia.pcpv1.Fragments.FragmentForum;
import com.example.manugiulia.pcpv1.Fragments.FragmentRelatos;
import com.example.manugiulia.pcpv1.Fragments.FragmentSobreNos;
import com.example.manugiulia.pcpv1.Fragments.Home;
import com.example.manugiulia.pcpv1.Fragments.Perfil;
import com.example.manugiulia.pcpv1.Modelos.Relatos;
import com.example.manugiulia.pcpv1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtHeaderNome;
    private FirebaseAuth mAuth;
    String nomeDoUsuario,UID;
    Home home = new Home();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("PCP");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contentMain,home);
        ft.commit();

        mAuth = FirebaseAuth.getInstance();
        nomeDoUsuario = mAuth.getCurrentUser().getDisplayName();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users=database.getReference("User");
        UID = mAuth.getCurrentUser().getUid();
        DatabaseReference info=users.child(UID);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtHeaderNome = (TextView)headerView.findViewById(R.id.headerNome);
        txtHeaderNome.setText(nomeDoUsuario);

        int fragment = getIntent().getIntExtra("fragment",1);
        switch (fragment)
        {
            case 1:
                Home home = new Home();
                FragmentTransaction ftHome = getSupportFragmentManager().beginTransaction();
                ftHome.replace(R.id.contentMain,home);
                ftHome.commit();
                break;
            case 2:
                Perfil perfil = new Perfil();
                FragmentTransaction ftPerfil = getSupportFragmentManager().beginTransaction();
                ftPerfil.replace(R.id.contentMain,perfil);
                ftPerfil.commit();
                break;
            case 3:
                FragmentRelatos relatos = new FragmentRelatos();
                FragmentTransaction ftRelatos = getSupportFragmentManager().beginTransaction();
                ftRelatos.replace(R.id.contentMain,relatos);
                ftRelatos.commit();
                break;
                case 4:
                FragmentForum forum = new FragmentForum();
                FragmentTransaction ftForum = getSupportFragmentManager().beginTransaction();
                ftForum.replace(R.id.contentMain,forum);
                ftForum.commit();
                break;
            default:
        }

        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtHeaderNome.setText(dataSnapshot.child("Dados").child("apelido").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deslogar) {
            mAuth.signOut();
            Intent inicio = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(inicio);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            return true;
        } else if(id == R.id.editarPerfil)
        {
            EditarPerfil editarPerfil = new EditarPerfil();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentMain,editarPerfil);
            ft.commit();
        } else if(id == R.id.infoSlides)
        {
            Intent infoSlides = new Intent(MainActivity.this,InfoSlidesActivity.class);
            startActivity(infoSlides);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_perfil) {
            fragment = new Perfil();
        } else if (id == R.id.nav_relatos) {
            fragment = new FragmentRelatos();
        } else if (id == R.id.nav_fórum) {
            fragment = new FragmentForum();
        } else if (id == R.id.nav_psicologosAmigos) {

        } else if (id == R.id.nav_depressão) {

        } else if (id == R.id.nav_sobreNós) {
            fragment = new FragmentSobreNos();
        } else if(id==R.id.nav_home)
        {
            fragment = new Home();
        }else if(id==R.id.nav_psicologosProximos)
        {
            Intent maps = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(maps);
            finish();
        }

        if(fragment!=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.contentMain,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
