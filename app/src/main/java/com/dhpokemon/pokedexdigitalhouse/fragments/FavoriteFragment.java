package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.adapters.RecyclerViewFavoriteAdapter;
import com.dhpokemon.pokedexdigitalhouse.adapters.RecyclerViewPokemonAdapter;
import com.dhpokemon.pokedexdigitalhouse.interfaces.IntegrationFragment;
import com.dhpokemon.pokedexdigitalhouse.interfaces.RecyclerViewClickListener;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.viewmodel.PokemonViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements RecyclerViewClickListener {
    private IntegrationFragment integration;
    private ProgressBar progressBarFav;
    private RecyclerView recyclerViewFavorite;
    private RecyclerViewFavoriteAdapter adapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        //Views
        recyclerViewFavorite = view.findViewById(R.id.recyclerViewFavorite);
        progressBarFav = view.findViewById(R.id.progressBarFav);
        //Adapter
        adapter = new RecyclerViewFavoriteAdapter(new ArrayList<>(), this);
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setItemViewCacheSize(20);
        recyclerViewFavorite.setAdapter(adapter);
        recyclerViewFavorite.setLayoutManager(new GridLayoutManager(view.getContext(), 2));


        // Pegamos a instancia do firebase, objeto necessario para salvar no banco de dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // pegamos a referencia para onde no firebase queremos salvar nossos dados
        DatabaseReference reference = database.getReference("pokefavorites");


        // Adicionamos o loistener par pegar os resultados do firebase
        reference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Pokemon> pokemons = new ArrayList<>();
                // Quando retornar algo do firebase percorremos os dados e colocamos na lista
                for (DataSnapshot resultSnapshot: dataSnapshot.getChildren()) {
                    Pokemon pokemon = resultSnapshot.getValue(Pokemon.class);
                    pokemons.add(pokemon);
                }

                // por fim atualizamos o adpter com os favoritos resgatados do firebase
                adapter.update(pokemons);
                progressBarFav.setVisibility(View.GONE);
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBarFav.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onItemClick(Pokemon pokemon) {

    }
}
