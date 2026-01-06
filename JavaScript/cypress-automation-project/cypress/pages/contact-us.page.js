
class ContactUsPage {

    get contactUsPageTitle() {
        return cy.get('div.contact-rich h4');
    }

    get breadcrumbTitle() {
        return cy.get('nav.breadcrumb ol li');
    }

    get storeLocationInfo() {
        return cy.get('div.contact-rich div div.data');
    }    

    get emailUsText() {
        return cy.get('div.data.email');
    }

    get emailIdInfo() {
        return cy.get('div.data.email a');
    }

    get contactUsForm() {
        return cy.get('section.contact-form form');
    }    
    
    get contactUsFormTitle() {
        return cy.get('div.form-group.row div h3');
    }

    get subjectDropdown() {
        return cy.get('select#id_contact');
    }

    get emailInput() {  
        return cy.get('input#email');           
    }     

    get messageTextarea() {
        return cy.get('textarea#contactform-message');
    }

    get sendButton() {
        return cy.get('button[name="submitMessage"]');
    }   

    get successAlert() {
        return cy.get('p.alert-success');
    }   

    get errorAlert() {
        return cy.get('div.alert-danger');
    }
}

export const contactUsPage = new ContactUsPage();