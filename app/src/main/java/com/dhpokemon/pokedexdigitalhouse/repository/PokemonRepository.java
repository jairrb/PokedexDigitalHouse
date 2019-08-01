package com.dhpokemon.pokedexdigitalhouse.repository;

import android.content.Context;

import com.dhpokemon.pokedexdigitalhouse.data.database.DataBase;
import com.dhpokemon.pokedexdigitalhouse.data.database.dao.PokemonDao;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.PokemonResponse;
import com.dhpokemon.pokedexdigitalhouse.model.species.SpecieResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static com.dhpokemon.pokedexdigitalhouse.data.network.ApiService.getApiService;

public class PokemonRepository {

    //Select Pokemon Database
    public Flowable<List<Pokemon>> getPokemonDatabase(Context context){
        DataBase databasePokemon = DataBase.getDatabase(context);
        PokemonDao pokemonDao = databasePokemon.pokemonDao();
        return pokemonDao.getAllRxJava();
    }

    //Insert Pokemon Database
    public void insertItems(Context context, List<Pokemon> pokemons){
        DataBase databasePokemon = DataBase.getDatabase(context);
        PokemonDao pokemonDao = databasePokemon.pokemonDao();
        pokemonDao.insertAll(pokemons);
    }

    public Single<PokemonResponse> getPokemonApi(int offset, int limit){
        Single<PokemonResponse> pokemonResponseSingle = getApiService()
                .getPokemons(offset,limit);
        return pokemonResponseSingle;
    }


    public Single<SpecieResponse> getSpeciesApi(int id){
        Single<SpecieResponse> speciesResponseSingle = getApiService()
                .getPokemonSpecies(id);
        return speciesResponseSingle;
    }
}