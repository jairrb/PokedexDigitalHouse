
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.SerializedName;

public class Sprites {

    @SerializedName("back_default")
    private String backDefault;
    @SerializedName("back_female")
    private String backFemale;
    @SerializedName("back_shiny")
    private String backShiny;
    @SerializedName("back_shiny_female")
    private String backShinyFemale;
    @SerializedName("front_default")
    private String frontDefault;
    @SerializedName("front_female")
    private String frontFemale;
    @SerializedName("front_shiny")
    private String frontShiny;
    @SerializedName("front_shiny_female")
    private String frontShinyFemale;

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackFemale() {
        return backFemale;
    }

    public void setBackFemale(String backFemale) {
        this.backFemale = backFemale;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    public void setBackShinyFemale(String backShinyFemale) {
        this.backShinyFemale = backShinyFemale;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public void setFrontFemale(String frontFemale) {
        this.frontFemale = frontFemale;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getFrontShinyFemale() {
        return frontShinyFemale;
    }

    public void setFrontShinyFemale(String frontShinyFemale) {
        this.frontShinyFemale = frontShinyFemale;
    }

}
