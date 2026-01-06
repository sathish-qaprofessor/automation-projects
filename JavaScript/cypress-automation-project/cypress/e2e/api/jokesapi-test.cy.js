describe('Jokes API Tests', () => {

    beforeEach(() => {
        cy.task('clearSoftAsserts', Cypress.currentTest.id, { log: false });
    });
    
    it('should fetch a random joke', () => {
        const jokeUrl = Cypress.env('apiUrl');
        cy.api(`${jokeUrl}joke/Pun?blacklistFlags=nsfw,religious,political,racist,sexist,explicit`)
            .then((response) => {
                cy.wrap(response.status).asserty('eq', 200);
                cy.wrap(response.body).its('type').asserty('to.be.oneOf', ['single', 'twopart']);
                cy.wrap(response.body).its('category').asserty('eq', 'Pun');
                cy.wrap(response.body).its('safe').asserty('eq', true);
                cy.wrap(response.body).its('flags').asserty('deep.equal', {
                    nsfw: false,
                    religious: false,
                    political: false,
                    racist: false,
                    sexist: false,
                    explicit: true
                });
                cy.log(JSON.stringify(response.body, null, 2));
            });
        cy.assertAll();
    });

    it('should fetch a joke by ID', () => {
        const jokeUrl = Cypress.env('apiUrl');
        const jokeId = 10;
        cy.api('GET', `${jokeUrl}joke/Any?idRange=${jokeId}`).then((response) => {
            cy.wrap(response.status).asserty('eq', 200);
            cy.wrap(response.body).its('id').asserty('eq', jokeId);
            cy.log(JSON.stringify(response.body, null, 2));
        });

        cy.assertAll();
    });

});