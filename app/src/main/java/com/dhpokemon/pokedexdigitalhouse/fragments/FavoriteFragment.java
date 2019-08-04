package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.adapters.FavoritesViewAdapter;
import com.dhpokemon.pokedexdigitalhouse.adapters.RecyclerViewPokemonAdapter;
import com.dhpokemon.pokedexdigitalhouse.adapters.SharedPreference;
import com.dhpokemon.pokedexdigitalhouse.interfaces.FavoriteItemClick;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
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
public class FavoriteFragment extends Fragment implements FavoriteItemClick {

    public static final String ARG_ITEM_ID = "favorite_list";
    private RecyclerView recyclerView;
    private FavoritesViewAdapter adapter;
    Activity activity;
    RecyclerViewPokemonAdapter recyclerViewPokemonAdapter;
    SharedPreference sharedPreference;
    List<Pokemon> favorites;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.list_favorite);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);
        adapter = new FavoritesViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);


        // Pegamos a instancia do firebase, objeto necessario para salvar no banco de dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // pegamos a referencia para onde no firebase queremos salvar nossos dados
        DatabaseReference reference = database.getReference("favorites");


        reference.orderByKey().addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Lista vazia pra pegar os resultados do firebase
                List<Pokemon> pokemons = new ArrayList<>();


                // Quando retornar algo do firebase percorremos os dados e colocamos na lista
                for (DataSnapshot pokemonSnapshot: dataSnapshot.getChildren()) {
                    Pokemon pokemon = pokemonSnapshot.getValue(Pokemon.class);
                    pokemons.add(pokemon);
                }

                // por fim atualizamos o adpter com os favoritos resgatados do firebase
                adapter.update(pokemons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


    @Override
    public void removeFavoriteClickListener(Pokemon pokemon) {

        // Pegamos a instancia do firebase, objeto necessario para salvar no banco de dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // pegamos a referencia para onde no firebase queremos salvar nossos dados
        DatabaseReference reference = database.getReference("favorites");

        // Adicionamos o listener par pegar os resultados do firebase
        reference.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Quando retornar algo do firebase percorremos os dados e colocamos na lista
                for (DataSnapshot resultSnapshot: dataSnapshot.getChildren()) {
                    Pokemon pokemonFirebase = resultSnapshot.getValue(Pokemon.class);

                    // Se acho o mesmo id removemos o item
                    if (pokemon.getId().equals(pokemonFirebase.getId())) {
                        resultSnapshot.getRef().removeValue();
                        adapter.removeItem(pokemon);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

