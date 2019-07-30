
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.Expose;

public class Item {

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

}
