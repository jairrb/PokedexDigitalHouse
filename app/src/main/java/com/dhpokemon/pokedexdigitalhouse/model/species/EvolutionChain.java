
package com.dhpokemon.pokedexdigitalhouse.model.species;

import com.google.gson.annotations.Expose;


public class EvolutionChain {

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
