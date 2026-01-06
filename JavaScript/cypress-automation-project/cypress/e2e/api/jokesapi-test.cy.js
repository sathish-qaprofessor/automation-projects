describe('Jokes API Tests', () => {

    it('should fetch a random joke', () => {
        cy.api('https://v2.jokeapi.dev/joke/Pun?blacklistFlags=nsfw,religious,political,racist,sexist,explicit')
            .then((response) => {
                cy.wrap(response.status).asserty('eq', 200);
                cy.wrap(response.body).its('type').asserty('be.oneOf', ['single', 'twopart']);
                cy.wrap(response.body).its('setup').asserty('be.a', 'string');
                cy.wrap(response.body).its('delivery').asserty('be.a', 'string');
                cy.wrap(response.body).its('category').asserty('eq', 'Pun');
                cy.wrap(response.body).its('safe').asserty('eq', true);
                cy.wrap(response.body).its('flags').asserty('deep.equal', {
                    nsfw: false,
                    religious: false,
                    political: false,
                    racist: false,
                    sexist: false,
                    explicit: false
                });
                cy.log(JSON.stringify(response.body, null, 2));
            });
    });

    it('should fetch a joke by ID', () => {
        const jokeId = 10;
        cy.api('GET', `https://v2.jokeapi.dev/joke/Any?idRange=${jokeId}`).then((response) => {
            cy.wrap(response.status).asserty('eq', 200);
            cy.wrap(response.body).its('id').asserty('eq', jokeId);
            cy.log(JSON.stringify(response.body, null, 2));
        });
    });

});