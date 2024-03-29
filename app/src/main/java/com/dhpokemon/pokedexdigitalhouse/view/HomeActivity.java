package com.dhpokemon.pokedexdigitalhouse.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.fragments.AboutFragment;
import com.dhpokemon.pokedexdigitalhouse.fragments.DetailFragment;
import com.dhpokemon.pokedexdigitalhouse.fragments.FavoriteFragment;
import com.dhpokemon.pokedexdigitalhouse.fragments.GameFragment;
import com.dhpokemon.pokedexdigitalhouse.fragments.HomeFragment;
import com.dhpokemon.pokedexdigitalhouse.interfaces.IntegrationFragment;
import com.dhpokemon.pokedexdigitalhouse.interfaces.RecyclerViewClickListener;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements IntegrationFragment, RecyclerViewClickListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView textViewUser;
    private TextView textViewEmail;
    private CircleImageView circleImageViewProfile;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        replaceFragmentNoStack(new HomeFragment(), new Pokemon());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            textViewUser.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());
            Picasso
                    .get()
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.defaultpokemon)
                    .error(R.drawable.defaultpokemon)
                    .into(circleImageViewProfile);
        }

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Handle navigation view item clicks here.
            int id = menuItem.getItemId();

            if (id == R.id.nav_home) {
                replaceFragmentNoStack(new HomeFragment(), new Pokemon());
            } else if (id == R.id.nav_share) {
                replaceFragment(new FavoriteFragment());
            } else if (id == R.id.nav_info) {
                replaceFragment(new AboutFragment());
            } else if (id == R.id.nav_game) {
                replaceFragment(new GameFragment());
            } else if (id == R.id.nav_exit) {
                logoutOption();
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        View headerView = navigationView.getHeaderView(0);

        textViewUser = headerView.findViewById(R.id.textViewUser);
        textViewEmail = headerView.findViewById(R.id.textViewEmail);
        circleImageViewProfile = headerView.findViewById(R.id.circleImageViewProfile);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void replaceFragmentPokemon(Fragment fragment, Pokemon pokemon) {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("POKEMON", pokemon);
            fragment.setArguments(bundle);

            String TAG = fragment.getClass().toString();
            String backStackName = fragment.getClass().getName();

            boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStackName, 0);

            if (!fragmentPopped && getSupportFragmentManager().findFragmentByTag(TAG) == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, TAG)
                        .addToBackStack(backStackName)
                        .commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void replaceFragmentNoStack(Fragment fragment, Pokemon pokemon) {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("POKEMON", pokemon);
            fragment.setArguments(bundle);

            String TAG = fragment.getClass().toString();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, TAG)
                    .commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void replaceFragmentGame(Fragment fragment, Pokemon pokemon, Boolean ok) {
        try {

            Bundle bundle = new Bundle();
            bundle.putParcelable("POKEMON", pokemon);
            bundle.putBoolean("OK", ok);
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void logoutOption() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void integrationDefault(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void integrationPokemon(Fragment fragment, Pokemon pokemon) {
        replaceFragmentPokemon(fragment, pokemon);
    }

    @Override
    public void integrationNoStack(Fragment fragment, Pokemon pokemon) {
        replaceFragmentNoStack(fragment, pokemon);
    }


    @Override
    public void integrationGame(Fragment fragment, Pokemon pokemon, Boolean ok) {
        replaceFragmentGame(fragment, pokemon, ok);
    }

    @Override
    public void onItemClick(Pokemon pokemon) {
        replaceFragmentPokemon(new DetailFragment(), pokemon);
    }

}