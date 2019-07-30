
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VersionDetail {

    @SerializedName("encounter_details")
    private List<EncounterDetail> encounterDetails;
    @SerializedName("max_chance")
    private Long maxChance;
    @Expose
    private Long rarity;
    @Expose
    private Version version;

    public List<EncounterDetail> getEncounterDetails() {
        return encounterDetails;
    }

    public void setEncounterDetails(List<EncounterDetail> encounterDetails) {
        this.encounterDetails = encounterDetails;
    }

    public Long getMaxChance() {
        return maxChance;
    }

    public void setMaxChance(Long maxChance) {
        this.maxChance = maxChance;
    }

    public Long getRarity() {
        return rarity;
    }

    public void setRarity(Long rarity) {
        this.rarity = rarity;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}
