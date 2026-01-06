const { defineConfig } = require("cypress");
import { plugin as cypressGrepPlugin } from '@cypress/grep/plugin'

const softFailures = new Map();

module.exports = defineConfig({
  reporter: "cypress-mochawesome-reporter",

  reporterOptions: {
    reportDir: "cypress/reports/mochawesome",
    overwrite: false,
    html: false,
    json: true,
    embeddedScreenshots: true,
    inlineAssets: true
  },

  e2e: {
    specPattern: "cypress/e2e/**/*.cy.js",

    setupNodeEvents(on, config) {
      // implement node event listeners here
      require("cypress-mochawesome-reporter/plugin")(on);

      cypressGrepPlugin(config);
      
      on('before:browser:launch', (browser, launchOptions) => {
        if (browser.family === 'chromium') {
          launchOptions.args.push('--ignore-certificate-errors');
          launchOptions.args.push('--allow-insecure-localhost');
        }

        if (browser.family === 'firefox') {
          launchOptions.preferences['network.stricttransportsecurity.preloadlist'] = false;
          launchOptions.preferences['security.cert_pinning.enforcement_level'] = 0;
        }

        return launchOptions;
      });

      on('task', {
        addSoftAssert({ testId, message }) {
          if (!softFailures.has(testId)) {
            softFailures.set(testId, []);
          }
          softFailures.get(testId).push(message);
          return null;
        },
        getSoftAsserts(testId) {
          return softFailures.get(testId) || [];
        },
        clearSoftAsserts(testId) {
          softFailures.delete(testId);
          return null;
        }
      })
      
      return config;
    },
  },

  env: {
    grepFilterSpecs: true,
    grepOmitFiltered: true,
  },

  viewportHeight: 900,
  viewportWidth: 1440,
  watchForFileChanges: false,
  screenshotsFolder: "cypress/screenshots",
});
