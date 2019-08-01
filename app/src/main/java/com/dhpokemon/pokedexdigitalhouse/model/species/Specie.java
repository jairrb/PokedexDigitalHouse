
package com.dhpokemon.pokedexdigitalhouse.model.species;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity (tableName = "specie")
public class Specie {
    @SerializedName("base_happiness")
    private Long baseHappiness;
    @SerializedName("capture_rate")
    private Long captureRate;
    @Expose
    private Color color;
    @SerializedName("egg_groups")
    private List<EggGroup> eggGroups;
    @SerializedName("evolution_chain")
    private EvolutionChain evolutionChain;
    @SerializedName("evolves_from_species")
    private Object evolvesFromSpecies;
    @SerializedName("flavor_text_entries")
    private List<FlavorTextEntry> flavorTextEntries;
    @SerializedName("form_descriptions")
    private List<Object> formDescriptions;
    @SerializedName("forms_switchable")
    private Boolean formsSwitchable;
    @SerializedName("gender_rate")
    private Long genderRate;
    @Expose
    private List<Genera> genera;
    @Expose
    private Generation generation;
    @SerializedName("growth_rate")
    private GrowthRate growthRate;
    @Expose
    private Habitat habitat;
    @SerializedName("has_gender_differences")
    private Boolean hasGenderDifferences;
    @SerializedName("hatch_counter")
    private Long hatchCounter;
    @Expose
    private Long id;
    @SerializedName("is_baby")
    private Boolean isBaby;
    @Expose
    private String name;
    @Expose
    private List<Name> names;
    @Expose
    private Long order;
    @SerializedName("pal_park_encounters")
    private List<PalParkEncounter> palParkEncounters;
    @SerializedName("pokedex_numbers")
    private List<PokedexNumber> pokedexNumbers;
    @Expose
    private Shape shape;
    @Expose
    private List<Variety> varieties;

    public Specie() {
    }

    public Long getBaseHappiness() {
        return baseHappiness;
    }

    public void setBaseHappiness(Long baseHappiness) {
        this.baseHappiness = baseHappiness;
    }

    public Long getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(Long captureRate) {
        this.captureRate = captureRate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<EggGroup> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(List<EggGroup> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public EvolutionChain getEvolutionChain() {
        return evolutionChain;
    }

    public void setEvolutionChain(EvolutionChain evolutionChain) {
        this.evolutionChain = evolutionChain;
    }

    public Object getEvolvesFromSpecies() {
        return evolvesFromSpecies;
    }

    public void setEvolvesFromSpecies(Object evolvesFromSpecies) {
        this.evolvesFromSpecies = evolvesFromSpecies;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }

    public List<Object> getFormDescriptions() {
        return formDescriptions;
    }

    public void setFormDescriptions(List<Object> formDescriptions) {
        this.formDescriptions = formDescriptions;
    }

    public Boolean getFormsSwitchable() {
        return formsSwitchable;
    }

    public void setFormsSwitchable(Boolean formsSwitchable) {
        this.formsSwitchable = formsSwitchable;
    }

    public Long getGenderRate() {
        return genderRate;
    }

    public void setGenderRate(Long genderRate) {
        this.genderRate = genderRate;
    }

    public List<Genera> getGenera() {
        return genera;
    }

    public void setGenera(List<Genera> genera) {
        this.genera = genera;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public GrowthRate getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(GrowthRate growthRate) {
        this.growthRate = growthRate;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public Boolean getHasGenderDifferences() {
        return hasGenderDifferences;
    }

    public void setHasGenderDifferences(Boolean hasGenderDifferences) {
        this.hasGenderDifferences = hasGenderDifferences;
    }

    public Long getHatchCounter() {
        return hatchCounter;
    }

    public void setHatchCounter(Long hatchCounter) {
        this.hatchCounter = hatchCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(Boolean isBaby) {
        this.isBaby = isBaby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public List<PalParkEncounter> getPalParkEncounters() {
        return palParkEncounters;
    }

    public void setPalParkEncounters(List<PalParkEncounter> palParkEncounters) {
        this.palParkEncounters = palParkEncounters;
    }

    public List<PokedexNumber> getPokedexNumbers() {
        return pokedexNumbers;
    }

    public void setPokedexNumbers(List<PokedexNumber> pokedexNumbers) {
        this.pokedexNumbers = pokedexNumbers;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public List<Variety> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<Variety> varieties) {
        this.varieties = varieties;
    }
}
