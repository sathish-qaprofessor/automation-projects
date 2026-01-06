
class ProductDetailsPage {

    get categoryTitle() {
        return cy.get('div.block-category.card-block h1');
    } 
} 

export const productDetailsPage = new ProductDetailsPage();