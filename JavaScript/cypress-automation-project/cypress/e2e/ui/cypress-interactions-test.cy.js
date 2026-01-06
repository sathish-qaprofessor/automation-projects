/// reference types="cypress" />

import { interactionPage } from "../../pages/interaction-page";
import { toCamelCase } from "../../support/utils/string-helpers";

describe("Cypress All Interactions Test Suite", () => {
    beforeEach(() => {
        cy.task('clearSoftAsserts', Cypress.currentTest.id, { log: false });
    });

    it('Verify home page content and sidebar menu items', () => {
        cy.fixture('homePageData').as('homeData');
        cy.visit('https://www.automationtesting.co.uk/');

        interactionPage.homePageHeader.should('be.visible')
            .text().asserty('eq', 'Testing Arena', 'Home page header text is incorrect');
        interactionPage.sidebarToggleButton.should('be.visible').click();
        cy.get('@homeData').then((data) => {
            interactionPage.sidebarMenuItems.text()
                .asserty('have.length', data.sidebarMenuItems.length, 'Sidebar menu items count is incorrect')
                .asserty('deep.equal', data.sidebarMenuItems, 'Sidebar menu items text do not match expected values');
        });
        interactionPage.sidebarToggleButton.click();
        interactionPage.copyRightText.asserty('have.text', '© automationtesting.co.uk. All rights reserved.', 'Copyright text is incorrect');

        cy.assertAll();
    });

    it('Verify Actions page content and functionality', () => {
        cy.visit('https://www.automationtesting.co.uk/actions.html');

        interactionPage.ActionsPageHeader.asserty('be.visible')
            .text().asserty('eq', 'Actions', 'Actions page header text is incorrect');

        interactionPage.dragItemToTargetArea().within(() => {
            interactionPage.dragItem.asserty('have.text', 'Drag me!');
        });

        interactionPage.doubleClickBox.asserty('be.visible').dblclick();
        interactionPage.doubleClickBox.asserty('have.text', 'Well Done!', 'Double click action failed');
        interactionPage.dragItemToSourceArea().within(() => {
            interactionPage.dragItem.asserty('have.text', 'Drag me!');
        });

        interactionPage.holdshiftAndClickBox.asserty('be.visible');
        cy.realPress('Shift');
        interactionPage.holdshiftAndClickBox.click();
        interactionPage.holdshiftAndClickBox.asserty('have.text', 'Hold Shift & Click Here', 'Shift + Click action failed');

        interactionPage.clickAndHoldBox.trigger('mousedown');
        interactionPage.clickAndHoldBox.asserty('have.text', 'Keep holding down!', 'Click and Hold action failed');
        interactionPage.clickAndHoldBox.trigger('mouseup');
        interactionPage.clickAndHoldBox.asserty('have.text', `No, don't let go :(`, 'Click and Hold release action failed');

        cy.assertAll();
    });

    it('Verify accordion page content and its functionality', () => {
        cy.fixture('accordionPageData').as('accordionData');

        cy.visit('https://www.automationtesting.co.uk/accordion.html');

        interactionPage.accordionPageHeader.asserty('be.visible')
            .text().asserty('eq', 'Accordion Test', 'Accordion page header text is incorrect');

        interactionPage.accordionContent.asserty('be.visible')
            .text().asserty('contain', 'Use this accordion test page to practise interacting with accordions.', 'Accordion content is incorrect');

        cy.get('@accordionData').then((data) => {
            interactionPage.accordionHeaders.text()
                .asserty('have.length', 3, 'There should be 3 accordion headers')
                .asserty('deep.equal', data.headers, 'Accordion headers text do not match expected values');
        });

        interactionPage.accordionHeaders.each((element, index) => {
            const headerText = element.text().trim();
            cy.wrap(element).click();
            cy.get('@accordionData').then((data) => {
                interactionPage.getAccordionContents(element)
                    .asserty('be.visible')
                    .asserty('not.be.empty', `Accordion content for header index '${index}' should not be empty`)
                    .text().asserty('eq', data.contents[toCamelCase(headerText)], `Accordion content text for header '${headerText}' is incorrect`);
            });
        });

        cy.assertAll();
    });

    it('Verify Contact Form contents and submission', () => {
        cy.visit('https://www.automationtesting.co.uk/contactform.html');
        interactionPage.contactFormHeader.asserty('be.visible')
            .text().asserty('eq', 'Contact Form Test', 'Contact Form page header text is incorrect');

        interactionPage.contactFormContent.asserty('be.visible')
            .text().asserty('contain', 'Use this contact us form to practise form input combinations (all fields are mandatory):', 'Contact Form content is incorrect');

        interactionPage.firstNameInput.asserty('be.visible', 'First Name input field should be visible');
        interactionPage.lastNameInput.asserty('be.visible', 'Last Name input field should be visible');
        interactionPage.emailInput.asserty('be.visible', 'Email input field should be visible');
        interactionPage.commentsInput.asserty('be.visible', 'Comments input field should be visible');
        interactionPage.submitButton.asserty('be.visible', 'Submit button should be visible');
        interactionPage.resetButton.asserty('be.visible', 'Reset button should be visible');

        interactionPage.firstNameInput.type('John');
        interactionPage.lastNameInput.type('Doe');
        interactionPage.emailInput.type('john.doe@example.com');
        interactionPage.commentsInput.fill('This is a test comment. Use this contact us form to practise form input combinations (all fields are mandatory).');

        interactionPage.resetButton.click();
        interactionPage.firstNameInput.asserty('have.value', '', 'First Name input field should be cleared after reset');
        interactionPage.lastNameInput.asserty('have.value', '', 'Last Name input field should be cleared after reset');
        interactionPage.emailInput.asserty('have.value', '', 'Email input field should be cleared after reset');
        interactionPage.commentsInput.asserty('have.value', '', 'Comments input field should be cleared after reset');

        cy.assertAll();
    });

    it.only('Verify Dropdown, Radio Button & Checkbox page content and selection', () => {
        cy.visit('https://www.automationtesting.co.uk/dropdown.html');
        interactionPage.drpdwnRadioCheckboxPageHeader.asserty('be.visible')
            .text().asserty('eq', 'Dropdown Menus, Radio Buttons & Checkboxes', 'Dropdown, Radio Button & Checkbox page header text is incorrect');

        interactionPage.drpdwnRadioCheckboxContent.asserty('be.visible')
            .text().asserty('contain', 'Use this webpage to interact with dropdown menus, radio buttons and checboxes. Try to instruct your Selenium tests to interact with these elements.', 'Dropdown, Radio Button & Checkbox content is incorrect');

        interactionPage.lowPriorityRadioButton.asserty('be.visible').click()
            .prev('input').asserty('be.checked', 'Low Priority radio button should be selected');
        interactionPage.normalPriorityRadioButton.asserty('be.visible').click()
            .prev('input').asserty('be.checked', 'Normal Priority radio button should be selected');
        interactionPage.highPriorityRadioButton.asserty('be.visible').click()
            .prev('input').asserty('be.checked', 'High Priority radio button should be selected');

        interactionPage.redCheckbox.check({ force: true })
            .asserty('be.checked', 'Red checkbox should be selected');
        interactionPage.greenCheckbox.check({ force: true })
            .asserty('be.checked', 'Green checkbox should be selected');
        interactionPage.blueCheckbox.check({ force: true })
            .asserty('be.checked', 'Blue checkbox should be selected');

        // interactionPage.dropdownMenu.select('Honda').invoke('text').asserty('have.text', 'Honda', 'Dropdown menu should have Honda selected');
        // interactionPage.dropdownMenu.select('BMW').invoke('text').asserty('have.text', 'BMW', 'Dropdown menu should have BMW selected');
        // interactionPage.dropdownMenu.select('Audi').invoke('text').asserty('have.text', 'Audi', 'Dropdown menu should have Audi selected');
        // interactionPage.dropdownMenu.invoke('text').asserty('have.text', 'Audi', 'Dropdown menu should have Audi selected');

        interactionPage.navigationTopMenu.filter(':visible').text()
            .asserty('deep.equal', ['Home', 'Animals', 'Sports'], 'Top navigation menu items do not match expected values');

        cy.assertAll();
    });

});

