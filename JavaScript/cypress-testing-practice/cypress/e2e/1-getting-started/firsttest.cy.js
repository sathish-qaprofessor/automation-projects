/// reference types="cypress" />

describe("Automation Testing Suite", () => {

    it("Verify sidebar menu items and toggle functionality", () => {
        cy.visit("https://www.automationtesting.co.uk");
        cy.get('.toggle').click();
        cy.get('header.major h2').should('have.text', 'Menu');
        cy.get('.toggle').click();
        cy.get('header.major h2').should('not.be.visible');

        
        let expectedMenuItems = [
            'Homepage', 'Accordion', 'Actions', 'Browser Tabs', 'Buttons', 'Calculator (JS)', 
            'Contact Us Form Test', 'Date Picker', 'DropDown Checkbox Radio', 'File Upload',
            'Hidden Elements', 'iFrames', 'Loader','Loader Two', 'Login Portal Test', 'Mouse Movement',
            'Pop Ups & Alerts', 'Predictive Search', 'Tables', 'Test Store', 'About Me'
        ];

        cy.get('.toggle').click();
        cy.get('nav#menu ul li a').should('have.length', expectedMenuItems.length);

        cy.get('nav#menu ul li a').then($elem => {
            const actuaulMenuItems = [...$elem].map(item => item.text.trim());
            expect(actuaulMenuItems).to.deep.equal(expectedMenuItems);        
        });

        cy.get('.toggle').click();

        cy.get('a.logo').should('be.visible').and('have.text', 'Automation Testing Test Arena');
        cy.get('section#banner div.content header h1').should('have.text', 'Testing Arena');        
    });

    it("Second Test Case", () => {
        cy.visit("https://www.automationtesting.co.uk");
        cy.get('.toggle').click();
        cy.get('a[href="accordion.html"]').click();
        cy.get('h2#content').should('have.text', 'Accordion Test');
        cy.get('div#main div.inner p').first().should('be.visible')
            .and('have.text', 'Use this accordion test page to practise interacting with accordions.');
        
        cy.get('#main div.accordion-header').each($el => {
            cy.wrap($el).click();
            cy.log('Clicked on accordion header: ' + $el.text());
            cy.wrap($el).next('.accordion-content').should('be.visible');
        });
    });

});