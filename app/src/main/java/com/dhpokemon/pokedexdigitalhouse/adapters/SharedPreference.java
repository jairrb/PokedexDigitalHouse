package com.dhpokemon.pokedexdigitalhouse.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "POKE_APP";
    public static final String FAVORITES  = "POKEMONS";

    public SharedPreference() {
        super();
    }

    // THIS FOUR METHODS ARE USED FOR MAINTAINING FAVORITES.
    public void saveFavorite(Context context, List<Pokemon> giftItems) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(giftItems);

        editor.putString(FAVORITES, jsonFavorites);
        editor.apply();

    }

    public void addFavorite(Context context, Pokemon pokemon) {
        List<Pokemon> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<>();
            favorites.add(pokemon);
            saveFavorite(context, favorites);
        }
    }

    public void removeFavorite(Context context, Pokemon pokemon) {
        ArrayList<Pokemon> favorites = getFavorites(context);
        if (favorites != null) {
            favorites = new ArrayList<>();
            favorites.remove(pokemon);
            saveFavorite(context, favorites);
        }
    }

    public ArrayList<Pokemon> getFavorites(Context context) {
        SharedPreferences settings;
        List<Pokemon> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Pokemon[] favoriteItems = gson.fromJson(jsonFavorites,
                    Pokemon[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else {
            return null;
        }
        return (ArrayList<Pokemon>) favorites;
    }
}
