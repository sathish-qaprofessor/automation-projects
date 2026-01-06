
class InteractionPage {

  // Home Page
  get homePageHeader() {
      return cy.get('section#banner div.content h1');
  }

  get sidebarToggleButton() {
      return cy.get('div#sidebar a.toggle');
  }

  get sidebarMenuItems() {
      return cy.get('div#sidebar div nav ul li a');
  }

  get copyRightText() {
      return cy.get('p.copyright');
  }

  // region Accordion Page
  get accordionPageHeader() {
      return cy.get('h2#content');
  }

  get accordionContent() {
    return cy.get('h2#content').next('p');
  }

  get accordionHeaders() {
    return cy.get('.accordion-header');
  }
  
  getAccordionContents(element) {
    return cy.wrap(element).next('.accordion-content');
  }

  // endregion methods

  // region Actions Page
  get ActionsPageHeader() {
      return cy.get('h2#content');
  }

  get clickAndHoldBox() {
      return cy.get('div#click-box');
  }
   
  get doubleClickBox() {
      return cy.get('p#doubClickStartText').first();
  }

  get holdshiftAndClickBox() {
      return cy.get('p#doubClickStartText').last();
  }

  get dragItem() {
      return cy.get('p#dragtarget');
  }

  dragItemToSourceArea() {
      // Use the custom drag helper to move the draggable paragraph into the second drop zone
      cy.alternativeDragElementToTargetArea('p#dragtarget', 'div.droptarget:first-of-type');
      return cy.get('div.droptarget:first-of-type');
  }

  dragItemToTargetArea() {
      // Use the custom drag helper to move the draggable paragraph into the second drop zone
      cy.alternativeDragElementToTargetArea('p#dragtarget', 'div.droptarget:last-of-type');
      return cy.get('div.droptarget:last-of-type');
  }

  // endregion Actions Page

  // region Contact Form Page
  get contactFormHeader() {
      return cy.get('h2#content');
  }

  get contactFormContent() {
      return cy.get('h2#content').next('p');
  }

  get firstNameInput() {
      return cy.get('input[name="first_name"]');
  }

  get lastNameInput() {
      return cy.get('input[name="last_name"]');
  }   

  get emailInput() {
      return cy.get('input[name="email"]');
  }

  get commentsInput() {
      return cy.get('textarea[name="message"]');
  }

  get submitButton() {
      return cy.get('input[type="submit"]');
  }

  get resetButton() {
      return cy.get('input[type="reset"]');
  }

  // endregion Contact Form Page

  // region Dropdown, Radio Button & Checkbox Page
  get drpdwnRadioCheckboxPageHeader() {
      return cy.get('h2#content');
  }

  get drpdwnRadioCheckboxContent() {
      return cy.get('h2#content').next('p');
  }

  get lowPriorityRadioButton() {
      return cy.get('input#demo-priority-low').next('label');
  }

  get normalPriorityRadioButton() {
      return cy.get('input#demo-priority-normal').next('label');
  }

  get highPriorityRadioButton() {
      return cy.get('input#demo-priority-high').next('label');
  }

  get redCheckbox() {
      return cy.get('input[value="Red"]');
  }

  get blueCheckbox() {
      return cy.get('input[value="Blue"]');
  }

  get greenCheckbox() {
      return cy.get('input[value="Green"]');
  }

  get navigationTopMenu() {
      return cy.get('nav#primary_nav_wrap ul li a');
  }

  getNavigationSubMenuItems(element) {
      return cy.wrap(element).next('ul li a');
  }

  get dropdownMenu() {
      return cy.get('select#cars');
  }


  // endregion Dropdown, Radio Button & Checkbox Page

}

export const interactionPage = new InteractionPage();