package org.catsapi.endpoints;

import org.framework.config.ConfigResolver;

public class CatsApiEndpoints {

    public static final String CATS_BASE_URI = ConfigResolver.resolve("catsBaseUrl");

    public static final String CAT_BREEDS = "/breeds";

    public static final String CAT_IMAGES_SEARCH = "/images/search";
}
