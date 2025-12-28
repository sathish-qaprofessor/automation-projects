package org.jokesapi.endpoints;

import org.framework.config.ConfigResolver;

public class JokesApiEndpoints {

    public static final String JOKES_BASE_URI = ConfigResolver.resolve("jokesBaseUrl");

    public static final String JOKE_DEV_INFO = "/info";

    public static final String JOKE_CATEGORIES = "/categories";
}
