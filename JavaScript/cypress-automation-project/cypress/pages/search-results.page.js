
class SearchResultsPage {

    get searchBreadCrumbTitle() {
        return cy.get('nav.breadcrumb ol li span');
    }

    get searchResultsTitle() {
        return cy.get("h1#js-product-list-header");
    }  

    get sortByDropdown() {
        return cy.contains('Sort by:').parent('div.sort-by-row').find('button.select-title');
    }

    get sortByOptionsList() {
        return cy.get('div.dropdown-menu a.select-list.js-search-link');
    }
    
    get totalProductsCountContent() {
        return cy.get('div.hidden-sm-down.total-products p');
    }

    get productListItems() {
        return cy.get('h2.h3.product-title a');
    }

    get showItemsContent() {
        return cy.get('nav.pagination div').first();
    }

    // region Methods

    getProductPrice(element) {
        return element.parents('div.product-description').find('div span.price');
    }

    // endregion

}

export const searchResultsPage = new SearchResultsPage();