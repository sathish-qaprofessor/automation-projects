/**
 * Helper functions for assertion validation and logging
 */

/**
 * Parse command arguments to extract expected value and message
 * @param {IArguments} args - Function arguments object
 * @param {boolean} requiresExpected - Whether the assertion requires an expected value
 * @returns {Object} - Object with hasExpected, expected, and message properties
 */
export function parseArguments(args, requiresExpected) {
    let hasExpected, expected, message;

    // Count actual arguments (excluding undefined ones at the end)
    let actualArgCount = 0;
    for (let i = args.length - 1; i >= 0; i--) {
        if (args[i] !== undefined) {
            actualArgCount = i + 1;
            break;
        }
    }

    if (actualArgCount === 4) {
        // 4 args: subject, assertion, expected, message
        hasExpected = true;
        expected = args[2];
        message = args[3];
    } else if (actualArgCount === 3) {
        if (requiresExpected) {
            // 3 args with expected: subject, assertion, expected
            hasExpected = true;
            expected = args[2];
            message = undefined;
        } else {
            // 3 args without expected: subject, assertion, message
            hasExpected = false;
            expected = undefined;
            message = args[2];
        }
    } else {
        // 2 or fewer args: subject, assertion
        hasExpected = false;
        expected = undefined;
        message = undefined;
    }

    return { hasExpected, expected, message };
}

/**
 * Format subject value for display based on its type
 * @param {*} subj - Subject value to format
 * @returns {string} - Formatted subject display string
 */
export function formatSubject(subj) {
    if (Array.isArray(subj) || (subj && subj.length !== undefined && typeof subj !== 'string')) {
        if (Cypress.dom.isElement(subj) || (subj && subj.jquery)) {
            return formatDomElement(subj);
        } else {
            return formatArray(subj);
        }
    }
    return typeof subj === 'string' ? subj : JSON.stringify(subj);
}

/**
 * Format DOM element selector string
 * @param {Element|jQuery} subj - DOM element or jQuery wrapped element
 * @returns {string} - Formatted selector string
 */
function formatDomElement(subj) {
    let selector = '';
    const el = subj.jquery ? subj[0] : subj;
    
    if (el.tagName) {
        selector = el.tagName.toLowerCase();
        if (el.className) selector += '.' + el.className.split(' ').join('.');
        if (el.id) selector = el.tagName.toLowerCase() + '#' + el.id;
    }
    return selector || `<${el.tagName.toLowerCase()}>`;
}

/**
 * Format array values with proper spacing
 * @param {Array} arr - Array to format
 * @returns {string} - Formatted array string
 */
function formatArray(arr) {
    const arrayValues = Array.from(arr).map(item => 
        typeof item === 'string' ? item : JSON.stringify(item)
    );
    return `[ ${arrayValues.join(', ')} ]`;
}

/**
 * Format expected value for display
 * @param {*} expected - Expected value
 * @param {boolean} hasExpected - Whether expected value exists
 * @returns {string} - Formatted expected display string
 */
export function formatExpected(expected, hasExpected) {
    if (!hasExpected) return '';
    
    if (Array.isArray(expected) || (expected && expected.length !== undefined && typeof expected !== 'string')) {
        return formatArray(expected);
    }
    return typeof expected === 'string' ? expected : JSON.stringify(expected);
}

/**
 * Build assertion message
 * @param {string} subjDisplay - Formatted subject
 * @param {string} assertion - Assertion type
 * @param {string} expectedDisplay - Formatted expected value
 * @param {boolean} hasExpected - Whether assertion has expected value
 * @returns {string} - Complete assertion message
 */
export function buildAssertionMessage(subjDisplay, assertion, expectedDisplay, hasExpected) {
    const assertionText = assertion.replace(/\./g, ' ');
    if (hasExpected) {
        return `expected ${subjDisplay} to ${assertionText} ${expectedDisplay}`;
    }
    return `expected ${subjDisplay} to ${assertionText}`;
}

/**
 * Validate assertion and call error handler if validation fails
 * @param {string} assertion - Assertion type
 * @param {*} subj - Subject to validate
 * @param {*} expected - Expected value
 * @param {string} message - Custom error message
 * @param {Function} onError - Error callback function
 */
export function validateAssertion(assertion, subj, expected, message, onError) {
    console.log('=== validateAssertion called ===');
    console.log('Assertion:', assertion);
    console.log('Subject type:', typeof subj);
    console.log('Expected:', expected);
    
    // Null/undefined check
    if (subj === null || subj === undefined) {
        onError(message || 'Target cannot be null or undefined');
        return;
    }

    const isElement = Cypress.dom.isElement(subj) || subj.jquery;
    const el = subj.jquery ? subj[0] : subj;
    Cypress.log('I am here in validateAssertion');

    // Define assertion validators with clear logic
    const assertionValidators = {

        // ===== TEXT ASSERTIONS =====
        'have.text': () => {
            Cypress.log('I am here in have.text assertion');
            const actualText = isElement ? el.textContent.trim() : String(subj).trim();
            const expectedText = String(expected).trim();
            const textMismatch = actualText !== expectedText;
            return textMismatch && `expected text '${actualText}' to equal '${expectedText}'`;
        },
        'not.have.text': () => {
            const actualText = isElement ? el.textContent.trim() : String(subj).trim();
            const expectedText = String(expected).trim();
            const textsMatch = actualText === expectedText;
            return textsMatch && `expected text '${actualText}' to not equal '${expectedText}'`;
        },
        'have.innerText': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            const actualText = el.innerText.trim();
            const expectedText = String(expected).trim();
            const textMismatch = actualText !== expectedText;
            return textMismatch && `expected innerText '${actualText}' to equal '${expectedText}'`;
        },
        'not.have.innerText': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            const actualText = el.innerText.trim();
            const expectedText = String(expected).trim();
            const textsMatch = actualText === expectedText;
            return textsMatch && `expected innerText '${actualText}' to not equal '${expectedText}'`;
        },

        // ===== VALUE ASSERTIONS =====
        'have.value': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            const actualValue = el.value !== undefined ? String(el.value).trim() : '';
            const expectedValue = String(expected).trim();
            const valueMismatch = actualValue !== expectedValue;
            return valueMismatch && `expected element value '${actualValue}' to equal '${expectedValue}'`;
        },
        'not.have.value': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            const actualValue = el.value !== undefined ? String(el.value).trim() : '';
            const expectedValue = String(expected).trim();
            const valuesMatch = actualValue === expectedValue;
            return valuesMatch && `expected element value '${actualValue}' to not equal '${expectedValue}'`;
        },

        // ===== VISIBILITY ASSERTIONS =====
        'be.visible': () => {
            const isNotVisible = isElement && !Cypress.dom.isVisible(el);
            return isNotVisible && 'expected element to be visible';
        },
        'not.be.visible': () => {
            const isVisible = isElement && Cypress.dom.isVisible(el);
            return isVisible && 'expected element to not be visible';
        },

        // ===== EXISTENCE ASSERTIONS =====
        'exist': () => {
            const doesNotExist = subj === null || subj === undefined;
            return doesNotExist && 'expected element to exist';
        },
        'not.exist': () => {
            const exists = subj !== null && subj !== undefined;
            return exists && 'expected element to not exist';
        },

        // ===== EMPTY ASSERTIONS =====
        'be.empty': () => {
            const isNotEmpty = subj && subj.length > 0;
            return isNotEmpty && 'expected element to be empty';
        },
        'not.be.empty': () => {
            const isEmpty = !subj || subj.length === 0;
            return isEmpty && 'expected element to not be empty';
        },

        // ===== LENGTH ASSERTIONS =====
        'have.length': () => {
            const currentLength = subj ? subj.length : 0;
            const lengthMismatch = !subj || currentLength !== expected;
            return lengthMismatch && `expected length ${currentLength} to equal ${expected}`;
        },
        'have.length.greaterThan': () => {
            const currentLength = subj ? subj.length : 0;
            const isNotGreater = !subj || !subj.length || currentLength <= expected;
            return isNotGreater && `expected length ${currentLength} to be greater than ${expected}`;
        },
        'have.length.less': () => {
            const currentLength = subj ? subj.length : 0;
            const isNotLess = !subj || !subj.length || currentLength >= expected;
            return isNotLess && `expected length ${currentLength} to be less than ${expected}`;
        },

        // ===== EQUALITY ASSERTIONS =====
        'deep.equal': () => {
            const notDeepEqual = JSON.stringify(subj) !== JSON.stringify(expected);
            return notDeepEqual && `expected ${JSON.stringify(subj)} to deep equal ${JSON.stringify(expected)}`;
        },
        'eq': () => {
            const notEqual = String(subj).trim() !== String(expected).trim();
            return notEqual && `expected '${subj}' to equal '${expected}'`;
        },
        'equal': () => {
            const notEqual = String(subj).trim() !== String(expected).trim();
            return notEqual && `expected '${subj}' to equal '${expected}'`;
        },

        // ===== INCLUSION ASSERTIONS =====
        'contain': () => {
            const doesNotContain = !String(subj).includes(String(expected));
            return doesNotContain && `expected '${subj}' to include '${expected}'`;
        },
        'include': () => {
            const doesNotInclude = !String(subj).includes(String(expected));
            return doesNotInclude && `expected '${subj}' to include '${expected}'`;
        },

        // ===== PATTERN MATCHING ASSERTIONS =====
        'match': () => {
            const doesNotMatch = !expected.test(String(subj));
            return doesNotMatch && `expected '${subj}' to match ${expected}`;
        },

        // ===== ENABLED/DISABLED ASSERTIONS =====
        'be.enabled': () => {
            const isDisabled = isElement && el.disabled;
            return isDisabled && 'expected element to be enabled';
        },
        'not.be.enabled': () => {
            const isEnabled = isElement && !el.disabled;
            return isEnabled && 'expected element to not be enabled';
        },
        'be.disabled': () => {
            const isEnabled = isElement && !el.disabled;
            return isEnabled && 'expected element to be disabled';
        },
        'not.be.disabled': () => {
            const isDisabled = isElement && el.disabled;
            return isDisabled && 'expected element to not be disabled';
        },

        // ===== CLICKABLE ASSERTIONS =====
        'be.clickable': () => {
            const isNotClickable = isElement && (el.disabled || !Cypress.dom.isVisible(el));
            return isNotClickable && 'expected element to be clickable';
        },
        'not.be.clickable': () => {
            const isClickable = isElement && !el.disabled && Cypress.dom.isVisible(el);
            return isClickable && 'expected element to not be clickable';
        },

        // ===== CHECKED ASSERTIONS =====
        'be.checked': () => {
            const isNotChecked = isElement && !el.checked;
            return isNotChecked && 'expected element to be checked';
        },
        'not.be.checked': () => {
            const isChecked = isElement && el.checked;
            return isChecked && 'expected element to not be checked';
        },

        // ===== SELECTED ASSERTIONS =====
        'be.selected': () => {
            const isNotSelected = isElement && !el.selected;
            return isNotSelected && 'expected element to be selected';
        },
        'not.be.selected': () => {
            const isSelected = isElement && el.selected;
            return isSelected && 'expected element to not be selected';
        },

        // ===== FOCUSED ASSERTIONS =====
        'be.focused': () => {
            const isNotFocused = isElement && el !== document.activeElement;
            return isNotFocused && 'expected element to be focused';
        },
        'not.be.focused': () => {
            const isFocused = isElement && el === document.activeElement;
            return isFocused && 'expected element to not be focused';
        },

        // ===== READONLY ASSERTIONS =====
        'be.readonly': () => {
            const isNotReadonly = isElement && !el.readOnly;
            return isNotReadonly && 'expected element to be readonly';
        },
        'not.be.readonly': () => {
            const isReadonly = isElement && el.readOnly;
            return isReadonly && 'expected element to not be readonly';
        },

        // ===== REQUIRED ASSERTIONS =====
        'be.required': () => {
            const isNotRequired = isElement && !el.required;
            return isNotRequired && 'expected element to be required';
        },
        'not.be.required': () => {
            const isRequired = isElement && el.required;
            return isRequired && 'expected element to not be required';
        },

        // ===== ONE OF ASSERTIONS =====
        'to.be.oneOf': () => {
            const valueArray = Array.isArray(expected) ? expected : [expected];
            const isNotOneOf = !valueArray.includes(subj);
            return isNotOneOf && `expected '${subj}' to be one of ${JSON.stringify(valueArray)}`;
        },
        'not.to.be.oneOf': () => {
            const valueArray = Array.isArray(expected) ? expected : [expected];
            const isOneOf = valueArray.includes(subj);
            return isOneOf && `expected '${subj}' to not be one of ${JSON.stringify(valueArray)}`;
        },

        // ===== ATTRIBUTE ASSERTIONS =====
        'have.attr': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            // expected should be [attributeName, expectedValue] or an object
            let attrName, expectedValue;
            
            if (Array.isArray(expected) && expected.length >= 2) {
                [attrName, expectedValue] = expected;
            } else if (typeof expected === 'object' && expected !== null) {
                // If expected is an object like {attrName: 'attrValue'}
                attrName = Object.keys(expected)[0];
                expectedValue = expected[attrName];
            } else {
                return 'expected value should be [attributeName, expectedValue] or {attributeName: expectedValue}';
            }

            const actualValue = el.getAttribute(attrName);
            if (actualValue === null && expectedValue !== null) {
                return `expected element to have attribute '${attrName}' with value '${expectedValue}', but attribute does not exist`;
            }
            if (actualValue !== String(expectedValue)) {
                return `expected element attribute '${attrName}' to be '${expectedValue}', but found '${actualValue}'`;
            }
        },
        'not.have.attr': () => {
            if (!isElement) {
                return 'target is not a valid element';
            }
            let attrName, expectedValue;
            
            if (Array.isArray(expected) && expected.length >= 2) {
                [attrName, expectedValue] = expected;
            } else if (typeof expected === 'object' && expected !== null) {
                attrName = Object.keys(expected)[0];
                expectedValue = expected[attrName];
            } else {
                return 'expected value should be [attributeName, expectedValue] or {attributeName: expectedValue}';
            }

            const actualValue = el.getAttribute(attrName);
            if (actualValue === String(expectedValue)) {
                return `expected element attribute '${attrName}' to not be '${expectedValue}'`;
            }
        }        
    };

    // Validate the assertion and call error handler if it fails
    const errorMsg = assertionValidators[assertion]?.();
    if (errorMsg) {
        // If custom message provided, append it to the error message
        const finalMsg = message ? `${errorMsg} | ${message}` : errorMsg;
        onError(finalMsg);
    } else if (!assertionValidators[assertion]) {
        // Log available assertions for debugging
        console.log('Available assertions:', Object.keys(assertionValidators));
        console.log('Requested assertion:', assertion);
        onError(`Assertion "${assertion}" is not supported`);
    }
}

/**
 * Log failed assertion to Cypress and console
 * @param {string} assertionMsg - Assertion message
 * @param {string} message - Custom error message
 * @param {string} testId - Current test ID
 * @param {string} errorMsg - Error message for soft assertion (includes custom message if provided)
 * @param {*} expected - Expected value for the assertion
 * @param {boolean} hasExpected - Whether the assertion has an expected value
 */
export function logFailure(assertionMsg, message, testId, errorMsg, expected, hasExpected) {
    // Build the full message with expected value if applicable
    let fullMessage = (message || errorMsg);
    if (hasExpected && expected !== undefined) {
        const expectedDisplay = typeof expected === 'string' ? expected : JSON.stringify(expected);
        fullMessage += ` | Expected Value: ${expectedDisplay}`;
    }
    
    // Log the warning with yellow background
    cy.then(() => {
        Cypress.log({
            name: '⚠️ WARNING :: Assertion Failed |',            
            message: fullMessage,
            state: 'pending',
            type: 'assertion'
        });
    });
    
    // Store for soft assertions report
    cy.task('addSoftAssert', {
        testId: testId,
        message: errorMsg
    }, { log: false });
}

/**
 * Log passed assertion to Cypress and console
 * @param {string} assertionMsg - Assertion message
 */
export function logSuccess(assertionMsg) {
    console.log(`✓ ${assertionMsg}`);
    
    cy.then(() => {
        Cypress.log({
            name: 'assert',
            message: assertionMsg,
            state: 'passed',
            type: 'assertion'
        });
    });
}
