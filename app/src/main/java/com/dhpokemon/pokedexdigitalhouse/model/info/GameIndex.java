
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameIndex {

    @SerializedName("game_index")
    private Long gameIndex;
    @Expose
    private Version version;

    public Long getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(Long gameIndex) {
        this.gameIndex = gameIndex;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}
