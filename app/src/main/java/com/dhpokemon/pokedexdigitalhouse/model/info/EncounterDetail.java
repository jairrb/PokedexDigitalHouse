
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EncounterDetail {

    @Expose
    private Long chance;
    @SerializedName("condition_values")
    private List<ConditionValue> conditionValues;
    @SerializedName("max_level")
    private Long maxLevel;
    @Expose
    private Method method;
    @SerializedName("min_level")
    private Long minLevel;

    public Long getChance() {
        return chance;
    }

    public void setChance(Long chance) {
        this.chance = chance;
    }

    public List<ConditionValue> getConditionValues() {
        return conditionValues;
    }

    public void setConditionValues(List<ConditionValue> conditionValues) {
        this.conditionValues = conditionValues;
    }

    public Long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Long maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Long getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Long minLevel) {
        this.minLevel = minLevel;
    }

}
