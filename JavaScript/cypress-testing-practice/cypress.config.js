const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    specPattern: "cypress/e2e/**/*.cy.js",
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },    
  },
  viewportHeight: 720,
  viewportWidth: 1280,
  watchForFileChanges: false,

});
