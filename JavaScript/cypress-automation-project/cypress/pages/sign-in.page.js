
class SignInPage {
    visit() {
        cy.visit('/sign-in');
    }   

    get breadcrumbTitle() {
        return cy.get('nav.breadcrumb ol li');
    }

    get signInPageTitle() {
        return cy.get('header.page-header h1');
    }

    get emailInput() {
        return cy.get('input#field-email');
    }     

    get passwordInput() {
        return cy.get('input#field-password');
    }     

    get showPasswordButton() {
        return cy.get('button[data-action="show-password"]');
    }

    get signInButton() {    
        return cy.get('button#submit-login');    
    }    

    get forgotPasswordLink() {
        return cy.get('div.forgot-password a');
    }

    get createAccountLink() {
        return cy.get('div.no-account a');
    }
    
    get errorMessage() {
        return cy.get('div.error-message');
    }

    // region Methods

    signIn(email, password) {
        this.emailInput.type(email);
        this.passwordInput.type(password);
        this.signInButton.click();
    } 
    
    // endregion
}

export const signInPage = new SignInPage();