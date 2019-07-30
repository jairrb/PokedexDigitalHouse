package com.dhpokemon.pokedexdigitalhouse.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon result);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Pokemon> pokemons);

    @Update
    void update(Pokemon pokemon);


    @Query("SELECT * FROM pokemon")
    List<Pokemon> getAll();

    @Query("SELECT * FROM  pokemon")
    Flowable<List<Pokemon>> getAllRxJava();
}