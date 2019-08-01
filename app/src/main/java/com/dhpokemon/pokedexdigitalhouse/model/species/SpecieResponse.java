package com.dhpokemon.pokedexdigitalhouse.model.species;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SpecieResponse {

    @Expose
    private Long count;
    @Expose
    private String next;
    @Expose
    private String previous;
    @Expose
    private Specie species;

    public SpecieResponse() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Specie getSpecies() {
        return species;
    }

    public void setSpecies(Specie species) {
        this.species = species;
    }
}
