package org.basics.drivers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public final class ChromiumOptions {

    private ChromiumOptions() {
    }

    public static <T extends org.openqa.selenium.chromium.ChromiumOptions<T>> T configure(T options) {

        Path downloadDir = Paths.get(System.getProperty("user.dir"), "drivers");

        try {
            Files.createDirectories(downloadDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create download directory: " + downloadDir, e);
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir.toString());
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);
        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("profile.default_content_setting_values.popups", 0);
        prefs.put("safebrowsing.disable_download_protection", true);

        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        options.addArguments("--start-maximized", "--disable-notifications", "--disable-popup-blocking", "--disable-extensions", "--remote-allow-origins=*");

        options.setAcceptInsecureCerts(false);

//        if (ConfigResolver.resolveBoolean("headless")) {
//            options.addArguments("--headless=new", "--disable-gpu");
//        }

        return options;
    }
}
