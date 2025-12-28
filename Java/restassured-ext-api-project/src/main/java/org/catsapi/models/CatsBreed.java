package org.catsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatsBreed {
    public Weight weight;
    public String id;
    public String name;
    @JsonProperty("breed_group")
    public String breedGroup;
    @JsonProperty("cfa_url")
    public String cfaUrl;
    @JsonProperty("vetstreet_url")
    public String vetstreetUrl;
    @JsonProperty("vcahospitals_url")
    public String vcahospitalsUrl;
    public String temperament;
    public String origin;
    @JsonProperty("country_codes")
    public String countryCodes;
    @JsonProperty("country_code")
    public String countryCode;
    public String description;
    @JsonProperty("life_span")
    public String lifeSpan;
    public Integer indoor;
    public Integer lap;
    @JsonProperty("alt_names")
    public String altNames;
    public Integer adaptability;
    @JsonProperty("affection_level")
    public Integer affectionLevel;
    @JsonProperty("child_friendly")
    public Integer childFriendly;
    @JsonProperty("dog_friendly")
    public Integer dogFriendly;
    @JsonProperty("cat_friendly")
    public Integer catFriendly;
    @JsonProperty("energy_level")
    public Integer energyLevel;
    public Integer bidability;
    public Integer grooming;
    @JsonProperty("health_issues")
    public Integer healthIssues;
    public Integer intelligence;
    @JsonProperty("shedding_level")
    public Integer sheddingLevel;
    @JsonProperty("social_needs")
    public Integer socialNeeds;
    @JsonProperty("stranger_friendly")
    public Integer strangerFriendly;
    public Integer vocalisation;
    public Integer experimental;
    public Integer hairless;
    public Integer natural;
    public Integer rare;
    public Integer rex;
    @JsonProperty("suppressed_tail")
    public Integer suppressedTail;
    @JsonProperty("short_legs")
    public Integer shortLegs;
    @JsonProperty("wikipedia_url")
    public String wikipediaUrl;
    public Integer hypoallergenic;
    @JsonProperty("reference_image_id")
    public String referenceImageId;

    // Getters and setters omitted for brevity

    public static class Weight {
        public String imperial;
        public String metric;

        // Getters and setters omitted for brevity
    }
}
