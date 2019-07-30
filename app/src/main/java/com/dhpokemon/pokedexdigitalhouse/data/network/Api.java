package com.dhpokemon.pokedexdigitalhouse.data.network;

import com.dhpokemon.pokedexdigitalhouse.model.info.InfoResponse;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.PokemonResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("pokemon")
    Single<PokemonResponse> getPokemons(@Query("offset") int offset , @Query("limit") int limit);

    @GET("pokemon")
    Single<InfoResponse> getInfoPokemon(Long id);
}
