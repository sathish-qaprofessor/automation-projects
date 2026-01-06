/// reference types="cypress" />

import { storeHomepage } from "../../pages/store-home.page";
import { searchResultsPage } from "../../pages/search-results.page";
import { contactUsPage } from "../../pages/contact-us.page";
import { signInPage } from "../../pages/sign-in.page";
import { productDetailsPage } from "../../pages/product-details.page";

describe("Store Homepage Tests", () => {

    beforeEach(() => {
        cy.visit("https://teststore.automationtesting.co.uk/index.php");
    });

    it("verify store home page contents and components", { tags: ['@smoke'] } , () => {
        let submenuItems = {
            'CLOTHES': ['MEN', 'WOMEN'],
            'ACCESSORIES': ['STATIONERY', 'HOME ACCESSORIES'],
            'ART': []
        }

        storeHomepage.pageLogo.should("be.visible", 'Logo is not visible');
        storeHomepage.signInLink.should("be.visible", 'Sign In link is not visible');
        storeHomepage.shoppingCartIcon.should("be.visible", 'Shopping cart icon is not visible');
        storeHomepage.searchBox.should("be.visible", 'Search box is not visible');
        storeHomepage.contactUsLink.should("be.visible", 'Contact Us link is not visible');
        storeHomepage.mainHeaderMenu.should("have.length", 3)
            .then(elem => {
                const mainMenuTexts = [...elem].map(el => el.innerText.trim());
                expect(mainMenuTexts).to.deep.equal(['CLOTHES', 'ACCESSORIES', 'ART']);
            });

        storeHomepage.mainHeaderMenu.each(element => {
            let header = element.clone().children().remove().end().text().trim();
            cy.wrap(element).realHover();
            cy.wait(500, { log: false });

            if (header.toUpperCase() !== 'ART') {
                storeHomepage.submenuHeaders.filter(':visible').invoke('show').and('have.length', 2)
                    .then(subElem => {
                        const submenuTexts = [...subElem].map(el => el.innerText.trim());
                        expect(submenuTexts).to.deep.equal(submenuItems[header.toUpperCase()]);
                        cy.wrap(element).trigger("mouseout", { force: true });
                    });
            }
        });

        storeHomepage.bannerImages.should('be.visible', 'Banner images are not visible');
        storeHomepage.popularProductsTitle
            .should('be.visible', 'Popular Products title is not visible')
            .text().should('eq', 'Popular Products', 'Popular Products title text is incorrect');
        storeHomepage.featuredProductsSection.should('have.length.greaterThan', 0, 'No featured products are displayed');
    });

    it("Verify products in featured products section", () => {
        storeHomepage.featuredProductsSection.should('have.length.greaterThan', 0, 'No featured products are displayed')
            .each(product => {
                cy.wrap(product).should('be.visible');
                cy.wrap(product).text().then(text => {
                    storeHomepage.getProcductPrice(cy.wrap(product)).should('be.visible').text().then(priceText => {
                        cy.log(`The Product - ${text.trim()} price is : ${priceText.trim()}`);
                    });
                });
            });
    });

    it("Verify search functionality from homepage", () => {
        const searchTerm = "shirt";
        storeHomepage.searchBox.should('be.visible', 'Search box is not visible').type(`${searchTerm}{enter}`);

        searchResultsPage.searchBreadCrumbTitle
            .should('be.visible', 'Breadcrumb title is not visible')
            .text().should('deep.equal', ['Home', 'Search results'], 'Breadcrumb title text is incorrect');

        searchResultsPage.productListItems.should('be.visible', 'Product list items are not visible')
            .its('length').then(productCount => {
                searchResultsPage.searchResultsTitle
                    .should('be.visible', 'Search results title is not visible')
                    .and('contain.text', `Search results`, 'Search results title text is incorrect');

                searchResultsPage.totalProductsCountContent
                    .should('be.visible', 'Total products count is not visible')
                    .text().should('eq', `There is ${productCount} product.`, 'Total products count text is incorrect');

                searchResultsPage.showItemsContent.text()
                    .should('eq', `Showing ${productCount}-${productCount} of ${productCount} item(s)`, 'Show items content text is incorrect');
            });

        searchResultsPage.productListItems.each(product => {
            cy.wrap(product).should('be.visible');
            cy.wrap(product).text().then(text => {
                searchResultsPage.getProductPrice(cy.wrap(product)).should('be.visible').text().then(priceText => {
                    cy.log(`The Product - ${text.trim()} price is : ${priceText.trim()}`);
                });
            });
        });

        let expectedSortOptions = ['Relevance', 'Name, A to Z', 'Name, Z to A', 'Price, low to high', 'Price, high to low'];

        searchResultsPage.sortByDropdown.should('be.visible', 'Sort By dropdown is not visible').click();
        searchResultsPage.sortByOptionsList.should('be.visible', 'Sort By options list is not visible')
            .then(options => {
                const optionTexts = [...options].map(el => el.innerText.trim());
                expect(optionTexts).to.deep.equal(expectedSortOptions);
            });

        searchResultsPage.sortByOptionsList.should('be.visible', 'Sort By options list is not visible')
            .text().should('be.deep.equal', expectedSortOptions, 'Default selected sort option is incorrect');
    });

    it('Verify navigation to Contact Us and Sign In pages from homepage', () => {
        storeHomepage.contactUsLink.should('be.visible', 'Contact Us link is not visible').click();
        contactUsPage.contactUsForm.should('be.visible', 'Contact Us form is not visible');
        contactUsPage.contactUsFormTitle.text().should('eq', 'Contact us', 'Not navigated to Contact Us page');
        cy.go('back');
        cy.waitUntil(() => storeHomepage.popularProductsTitle.should('be.visible'), { timeout: 10000, interval: 500 });

        storeHomepage.signInLink.should('be.visible', 'Sign In link is not visible').click();
        cy.waitUntil(() => signInPage.signInPageTitle.should('be.visible'), { timeout: 10000, interval: 500 });
        signInPage.signInPageTitle.text().should('eq', 'Log in to your account', 'Not navigated to Sign In page');
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
                productDetailsPage.categoryTitle.text().should('eq', header, `Not navigated to ${header} category page`);
                cy.go('back');
                cy.waitUntil(() => storeHomepage.popularProductsTitle.should('be.visible'), { timeout: 10000, interval: 500 });
            });
        });
    });

});
