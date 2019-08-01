package com.dhpokemon.pokedexdigitalhouse.model.species;

import com.google.gson.annotations.Expose;

public class SpecieResponse {
    private Specie specie;

    public SpecieResponse() {
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }
}
