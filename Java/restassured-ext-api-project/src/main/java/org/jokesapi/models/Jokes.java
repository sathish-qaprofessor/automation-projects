package org.jokesapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Jokes {
    public int totalCount;

    public List<String> categories;

    public List<String> flags;

    public List<String> types;

    @JsonProperty("submissionURL")
    public String submissionUrl;

    public Map<String, List<Integer>> idRange;

    public List<SafeJoke> safeJokes;
}

