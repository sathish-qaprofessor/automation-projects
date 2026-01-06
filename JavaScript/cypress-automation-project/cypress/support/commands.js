// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

// Import assertion helper functions
import {
    parseArguments,
    formatSubject,
    formatExpected,
    buildAssertionMessage,
    validateAssertion,
    logFailure,
    logSuccess
} from './utils/assertion-helpers.js';

////
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

Cypress.on('uncaught:exception', (err, runnable) => {
    return false;
});

Cypress.Commands.add('paste', { prevSubject: true }, function (subject, pasteOptions) {
    const { pastePayload, pasteType } = pasteOptions;
    const data = pasteType === 'application/json' ? JSON.stringify(pastePayload) : pastePayload;
    const clipboardData = new DataTransfer();
    clipboardData.setData(pasteType, data);
    const pasteEvent = new ClipboardEvent('paste', {
        bubbles: true,
        cancelable: true,
        dataType: pasteType,
        data,
        clipboardData,
    });
    subject[0].dispatchEvent(pasteEvent);
    return subject;
});

Cypress.Commands.add('dragElementToTargetArea', (dragElment, dropElement) => {
    cy.get(dragElment).then((el) => {
        const draggable = el[0];
        cy.get(dropElement).then((el) => {
            const droppable = el[0];
            const coords = droppable.getBoundingClientRect();
            draggable.dispatchEvent(new MouseEvent('mousemove'));
            draggable.dispatchEvent(new MouseEvent('mousedown'));
            draggable.dispatchEvent(new MouseEvent('mousemove', { clientX: 10, clientY: 0 }));
            draggable.dispatchEvent(
                new MouseEvent('mousemove', {
                    clientX: coords.x + 10,
                    clientY: coords.y + 10,
                })
            );
            draggable.dispatchEvent(new MouseEvent('mouseup'));
        });
    });
});

Cypress.Commands.add('alternativeDragElementToTargetArea', (dragSelector, dropSelector) => {
    cy.get(dragSelector).then($drag => {
        const dataTransfer = new DataTransfer();

        cy.wrap($drag)
            .trigger('dragstart', { dataTransfer });

        cy.get(dropSelector)
            .trigger('dragover', { dataTransfer })
            .trigger('drop', { dataTransfer });

        cy.wrap($drag)
            .trigger('dragend', { dataTransfer });
    });
});

Cypress.Commands.add('fill', { prevSubject: true }, (subject, value) => {
    cy.wrap(subject)
        .should('be.visible')
        .invoke('val', value)
        .trigger('input')
        .trigger('change');
    return cy.wrap(subject);
});

Cypress.Commands.add('clearAndFill', { prevSubject: true }, (subject, value) => {
    cy.wrap(subject)
        .should('be.visible')
        .clear()
        .invoke('val', value)
        .trigger('input')
        .trigger('change');
    return cy.wrap(subject);
});


Cypress.Commands.add('softAssert', (fn, message) => {
    cy.then(() => {
        try {
            fn();
        } catch (e) {
            cy.task('addSoftAssert', {
                testId: Cypress.currentTest.id,
                message: message || e.message
            });
        }
    });
});

Cypress.Commands.add('assertAll', () => {
    cy.then(() => {
        cy.task('getSoftAsserts', Cypress.currentTest.id, { log: false })
            .then(errors => {
                if (errors && errors.length) {
                    cy.task('clearSoftAsserts', Cypress.currentTest.id);
                    const formattedErrors = errors.map((err, index) => `${index + 1}. ${err}`).join('\n');
                    throw new Error(`\n\nSoft Assertion Failures (${errors.length} total):\n\n${formattedErrors}\n`);
                }
            });
    });
});

// Alias 'asserty' as an alternative to 'verify'
Cypress.Commands.add('asserty',
    { prevSubject: true },
    function (subject, assertion, expectedOrMessage, optionalMessage) {
        return cy.wrap(subject, { log: false }).verify(assertion, expectedOrMessage, optionalMessage);
    }
);

Cypress.Commands.add('verify',
    { prevSubject: true },
    function (subject, assertion, expectedOrMessage, optionalMessage) {
        // ========== PARSE ARGUMENTS ==========
        // State assertions that don't require an expected value
        const stateAssertions = [
            'be.visible', 'not.be.visible',
            'exist', 'not.exist',
            'be.empty', 'not.be.empty',
            'be.enabled', 'not.be.enabled',
            'be.clickable', 'not.be.clickable',
            'be.checked', 'not.be.checked',
            'be.selected', 'not.be.selected',
            'be.disabled', 'not.be.disabled',
            'be.focused', 'not.be.focused',
            'be.readonly', 'not.be.readonly',
            'be.required', 'not.be.required'
        ];

        // Value assertions that require an expected value
        const valueAssertions = [
            'eq', 'equal', 'contain', 'include', 'match', 'have.length', 'deep.equal',
            'have.length.greaterThan', 'have.length.less', 'greaterThan', 'less than',
            'to.be.oneOf', 'not.to.be.oneOf', 'have.attr', 'not.have.attr', 'have.text', 'not.have.text',
            'have.innerText', 'not.have.innerText', 'have.value', 'not.have.value'
        ];

        const requiresExpected = valueAssertions.includes(assertion);
        const { hasExpected, expected, message } = parseArguments(arguments, requiresExpected);

        // ========== INITIALIZE ==========
        const testId = Cypress.currentTest.id;
        let subj = subject;
        let failed = false;
        let errorMsg = '';

        // ========== EXTRACT SUBJECT ==========
        if (subject && typeof subject.text === 'function' && Cypress.dom.isElement(subject[0])) {
            subj = subject;
        }

        // ========== VALIDATE ASSERTION ==========
        try {
            validateAssertion(assertion, subj, expected, message, (error) => {
                failed = true;
                errorMsg = error;
            });
        } catch (error) {
            failed = true;
            errorMsg = message || error.message;
        }

        // ========== FORMAT DISPLAY VALUES ==========
        const subjDisplay = formatSubject(subj);
        const expectedDisplay = formatExpected(expected, hasExpected);

        // ========== BUILD ASSERTION MESSAGE ==========
        const assertionMsg = buildAssertionMessage(subjDisplay, assertion, expectedDisplay, hasExpected);

        // ========== LOG RESULT ==========
        if (failed) {
            logFailure(assertionMsg, message, testId, errorMsg, expected, hasExpected);
        } else {
            logSuccess(assertionMsg);
        }

        // ========== RETURN FOR CHAINING ==========
        return cy.wrap(subject, { log: false });
    }
);
