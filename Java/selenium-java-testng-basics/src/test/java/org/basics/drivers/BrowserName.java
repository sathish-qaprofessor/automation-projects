package org.basics.drivers;

import java.util.Locale;

public enum BrowserName {

    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserName getBrowser(String name) {
        try {
            return BrowserName.valueOf(name.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unsupported browser: %s", name));
        }
    }
}
