package com.dhpokemon.pokedexdigitalhouse.data.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dhpokemon.pokedexdigitalhouse.data.database.dao.PokemonDao;
import com.dhpokemon.pokedexdigitalhouse.data.database.dao.SpecieDao;
import com.dhpokemon.pokedexdigitalhouse.model.pokemon.Pokemon;
import com.dhpokemon.pokedexdigitalhouse.model.species.Specie;

@androidx.room.Database(entities = {Pokemon.class, Specie.class}, version = 6, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DataBase extends RoomDatabase {
    private static volatile DataBase INSTANCE;

    public abstract PokemonDao pokemonDao();

    public abstract SpecieDao specieDao();

    public static DataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, DataBase.class, "my_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
