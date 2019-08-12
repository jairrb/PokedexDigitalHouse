package com.dhpokemon.pokedexdigitalhouse.interfaces;

import androidx.fragment.app.Fragment;

import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;

public interface IntegrationFragment {
    void integrationDefault(Fragment fragment);
    void integrationPokemon(Fragment fragment, Pokemon pokemon);
    void integrationNoStack(Fragment fragment, Pokemon pokemon);
    void integrationGame(Fragment fragment, Pokemon pokemon,Boolean ok);
}
