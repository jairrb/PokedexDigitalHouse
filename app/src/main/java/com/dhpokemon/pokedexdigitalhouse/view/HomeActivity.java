package com.dhpokemon.pokedexdigitalhouse.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.dhpokemon.pokedexdigitalhouse.adapters.RecyclerViewPokemonAdapter;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.PokemonViewModel;
import com.dhpokemon.pokedexdigitalhouse.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPokemon;
    private PokemonViewModel pokemonViewModel;
    private RecyclerViewPokemonAdapter adapter;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private int offset = 1;
    private int limit = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        pokemonViewModel.getPokemon(offset,limit);

        //Observable Update Recycler
        pokemonViewModel.getPokemonLiveData()
                .observe(this, pokemons -> adapter.update(pokemons));


        //Observable Loading
        pokemonViewModel.getLoadingLiveData()
                .observe(this, isLoading -> {
                    if (isLoading) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                });

        //Observable Error
        pokemonViewModel.getErrorLiveData()
                .observe(this, throwable -> {
                    Snackbar.make(recyclerViewPokemon, throwable.getMessage(),Snackbar.LENGTH_LONG).show();
                });
    }

    private void initViews() {
        //ViewModel
        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);

        //Views
        recyclerViewPokemon = findViewById(R.id.recyclerViewPokemon);
        progressBar = findViewById(R.id.progressBar);

        //Adapter
        adapter = new RecyclerViewPokemonAdapter(new ArrayList<>());

        recyclerViewPokemon.setHasFixedSize(true);
        recyclerViewPokemon.setItemViewCacheSize(20);
        recyclerViewPokemon.setAdapter(adapter);
        recyclerViewPokemon.setLayoutManager(new GridLayoutManager(this, 2));

        setScroolListener();
    }

    private void setScroolListener() {
        recyclerViewPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalitemCount = manager.getItemCount();
                int lastVisible = manager.findLastVisibleItemPosition();

                boolean endRecycler = lastVisible + 5 >= totalitemCount;

                if (totalitemCount > 0 && endRecycler){
                    offset = (offset + limit + 1);
                    pokemonViewModel.getPokemon(offset,limit);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            firebaseAuth.getInstance().signOut();

            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
