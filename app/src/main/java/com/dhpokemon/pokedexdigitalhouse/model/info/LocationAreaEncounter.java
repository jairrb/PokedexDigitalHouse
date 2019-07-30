
package com.dhpokemon.pokedexdigitalhouse.model.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationAreaEncounter {

    @SerializedName("location_area")
    private LocationArea locationArea;
    @SerializedName("version_details")
    private List<VersionDetail> versionDetails;

    public LocationArea getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(LocationArea locationArea) {
        this.locationArea = locationArea;
    }

    public List<VersionDetail> getVersionDetails() {
        return versionDetails;
    }

    public void setVersionDetails(List<VersionDetail> versionDetails) {
        this.versionDetails = versionDetails;
    }

}
