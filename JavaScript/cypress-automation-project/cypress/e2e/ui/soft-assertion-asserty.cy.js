/// reference types="cypress" />

import { storeHomepage } from "../../pages/store-home.page";
import { searchResultsPage } from "../../pages/search-results.page";
import { contactUsPage } from "../../pages/contact-us.page";
import { signInPage } from "../../pages/sign-in.page";
import { productDetailsPage } from "../../pages/product-details.page";

describe("Store Homepage Tests using Asserty", () => {

    beforeEach(() => {
        // Clear any soft assertions from previous tests
        cy.task('clearSoftAsserts', Cypress.currentTest.id, { log: false });
        cy.visit("https://teststore.automationtesting.co.uk/index.php");
    });  

    it("TEST-11290 | verify store home page contents and components", { tags: ['@smoke'] } , () => {
        let submenuItems = {
            'CLOTHES': ['MEN', 'WOMEN'],
            'ACCESSORIES': ['STATIONERY', 'HOME ACCESSORIES'],
            'ART': []
        }

        let expectedMainMenu = Object.keys(submenuItems);

        storeHomepage.pageLogo.asserty('be.visible', 'Logo is not visible');
        storeHomepage.signInLink.asserty('be.visible', 'Sign In link is not visible');
        storeHomepage.shoppingCartIcon.asserty('be.visible', 'Shopping cart icon is not visible');   
        storeHomepage.searchBox.asserty('be.visible', 'Search box is not visible');
        storeHomepage.contactUsLink.asserty('be.visible', 'Contact Us link is not visible')  ;
        storeHomepage.mainHeaderMenu.asserty("have.length", 3)
            .then(elem => {
                const mainMenuTexts = [...elem].map(el => el.innerText.trim());
                cy.wrap(mainMenuTexts).asserty('deep.equal', expectedMainMenu);
            });

        storeHomepage.mainHeaderMenu.each(element => {
            let header = element.clone().children().remove().end().text().trim();
            cy.wrap(element).realHover();
            cy.wait(500, { log: false });

            if (header.toUpperCase() !== 'ART') {
                storeHomepage.submenuHeaders.filter(':visible').invoke('show')
                    .then(subElem => {
                        cy.wrap(subElem).asserty('have.length', 2, 'Submenu should have 2 items');
                        const submenuTexts = [...subElem].map(el => el.innerText.trim());
                        cy.wrap(submenuTexts).asserty('deep.equal', submenuItems[header.toUpperCase()], 'Submenu items do not match expected');
                        cy.wrap(element).trigger("mouseout", { force: true });
                    });
            }                               
        });

        storeHomepage.bannerImages.asserty('be.visible', 'Banner images are not visible');
        storeHomepage.popularProductsTitle.asserty('be.visible', 'Popular Products title is not visible');
        storeHomepage.popularProductsTitle.text().asserty('eq', 'Popular Products', 'Popular Products title text is incorrect');
        storeHomepage.featuredProductsSection.asserty('have.length.greaterThan', 0, 'No featured products are displayed');       
        cy.assertAll(); 
    });

    it("Verify products in featured products section", { tags: ['@regression'] }, () => {
        storeHomepage.featuredProductsSection.asserty('have.length.greaterThan', 0, 'No featured products are displayed')
            .each(product => {
                cy.wrap(product).asserty('be.visible');
                cy.wrap(product).text().then(text => {
                    storeHomepage.getProcductPrice(cy.wrap(product)).asserty('be.visible').text().then(priceText => {
                        cy.log(`The Product - ${text.trim()} price is : ${priceText.trim()}`);
                    });
                });
            });
        cy.assertAll();    
    });

    it("Verify search functionality from homepage", { tags: ['@regression', '@smoke'] }, () => {
        const searchTerm = "shirt";
        storeHomepage.searchBox.asserty('be.visible', 'Search box is not visible').type(`${searchTerm}{enter}`);

        cy.waitUntil(_ =>
            searchResultsPage.searchBreadCrumbTitle.should('be.visible', 'Breadcrumb title is not visible'),
            { timeout: 10000, interval: 500 }
        );

        searchResultsPage.searchBreadCrumbTitle.text().asserty('deep.equal', ['Home', 'Search results'], 'Breadcrumb title text is incorrect'); 
        searchResultsPage.productListItems.should('be.visible', 'Product list items are not visible')
            .its('length').then(productCount => {
                searchResultsPage.searchResultsTitle
                    .asserty('be.visible', 'Search results title is not visible')
                    .and('contain.text', `Search results`, 'Search results title text is incorrect');

                searchResultsPage.totalProductsCountContent
                    .asserty('be.visible', 'Total products count is not visible')
                    .invoke('text').asserty('eq', `There is ${productCount} product.`, 'Total products count text is incorrect');

                searchResultsPage.showItemsContent.invoke('text')
                    .asserty('eq', `Showing ${productCount}-${productCount} of ${productCount} item(s)`, 'Show items content text is incorrect');
            });

        searchResultsPage.productListItems.each(product => {
            cy.wrap(product).asserty('be.visible');
            cy.wrap(product).text().then(text => {
                searchResultsPage.getProductPrice(cy.wrap(product)).asserty('be.visible').text().then(priceText => {
                    cy.log(`The Product - ${text.trim()} price is : ${priceText.trim()}`);
                });
            });
        });

        let expectedSortOptions = ['Relevance', 'Name, A to Z', 'Name, Z to A', 'Price, low to high', 'Price, high to low'];

        searchResultsPage.sortByDropdown.asserty('be.visible', 'Sort By dropdown is not visible').click();
        searchResultsPage.sortByOptionsList.asserty('be.visible', 'Sort By options list is not visible')
            .then(options => {
                const optionTexts = [...options].map(el => el.innerText.trim());
                cy.wrap(optionTexts).asserty('deep.equal', expectedSortOptions); 
            });

        searchResultsPage.sortByOptionsList.asserty('be.visible', 'Sort By options list is not visible')
            .text().asserty('deep.equal', expectedSortOptions, 'Default selected sort option is incorrect');
        cy.assertAll();
    });

    it('Verify navigation to Contact Us and Sign In pages from homepage', { tags: ['@regression'] }, () => {
        storeHomepage.contactUsLink.asserty('be.visible', 'Contact Us link is not visible').click();
        contactUsPage.contactUsForm.asserty('be.visible', 'Contact Us form is not visible');
        contactUsPage.contactUsFormTitle.invoke('text').asserty('eq', 'Contact us', 'Not navigated to Contact Us page');
        cy.go('back');
        cy.waitUntil(() => storeHomepage.popularProductsTitle.should('be.visible'), { timeout: 10000, interval: 500 });

        storeHomepage.signInLink.asserty('be.visible', 'Sign In link is not visible').click();
        cy.waitUntil(() => signInPage.signInPageTitle.should('be.visible'), { timeout: 10000, interval: 500 });
        signInPage.signInPageTitle.invoke('text').asserty('eq', 'Log in to your account', 'Not navigated to Sign In page');
        cy.go('back');
        cy.waitUntil(() => storeHomepage.popularProductsTitle.should('be.visible'), { timeout: 10000, interval: 500 });

        storeHomepage.mainHeaderMenu.each((_$elem, index) => {

            storeHomepage.mainHeaderMenu.eq(index).then(element => {
                let header = element.clone().children().remove().end().text().trim();
                cy.wrap(element).text().then(text => {
                    cy.log(`Navigating to category: ${text.trim()}`);
                });
                cy.wrap(element).click({ force: true });
                cy.waitUntil(() => productDetailsPage.categoryTitle.should('be.visible'), { timeout: 10000, interval: 500 });
                productDetailsPage.categoryTitle.invoke('text').asserty('eq', header, `Not navigated to ${header} category page`);
                cy.go('back');
                cy.waitUntil(() => storeHomepage.popularProductsTitle.should('be.visible'), { timeout: 10000, interval: 500 });
            });
        });
        cy.assertAll();
    });

});
