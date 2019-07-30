
package com.dhpokemon.pokedexdigitalhouse.model.pokemon;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import static com.dhpokemon.pokedexdigitalhouse.util.AppUtil.getIntUrlPokemon;

@Entity(tableName = "pokemon")
public class Pokemon {
    @PrimaryKey
    @NonNull
    private Long id;
    @Expose
    private String name;
    @Expose
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return getIntUrlPokemon(this.getUrl());
    }

    public void setId(@NonNull Long id) {
        this.id = id;//getIntUrlPokemon(this.getUrl());
    }
}