package com.dhpokemon.pokedexdigitalhouse.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dhpokemon.pokedexdigitalhouse.R;
import com.dhpokemon.pokedexdigitalhouse.adapters.RecyclerViewPokemonAdapter;
import com.dhpokemon.pokedexdigitalhouse.adapters.SharedPreference;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public static final String ARG_ITEM_ID = "favorite_list";

    ListView favoriteList;
    Activity activity;
    RecyclerViewPokemonAdapter recyclerViewPokemonAdapter;
    SharedPreference sharedPreference;
    List<Pokemon> favorites;
    DetailFragment detailFragment;


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

        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);
        favoriteList = view.findViewById(R.id.list_favorite);

        sharedPreference.getFavorites(detailFragment.getActivity());


        return view;
    }

    
}

