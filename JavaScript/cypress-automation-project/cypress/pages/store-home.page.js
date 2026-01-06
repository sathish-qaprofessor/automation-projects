
class StoreHomepage {

    get pageLogo() {
        return cy.get('.logo');
    }

    get contactUsLink() {
        return cy.get('#contact-link > a');
    }

    get searchBox() {
        return cy.get('input[placeholder="Search our catalog"]');
    }

    get mainHeaderMenu() {
        return cy.get('#_desktop_top_menu ul li a[data-depth="0"]');
    }

    get submenuHeaders() {
        return cy.get('a.dropdown-item.dropdown-submenu[data-depth="1"]', { timeout: 10000 });
    }

    get signInLink() {
        return cy.get('div.user-info a');
    }

    get shoppingCartIcon() {
        return cy.get('div.blockcart.cart-preview');
    }

    get bannerImages() {
        return cy.get('ul.carousel-inner li.carousel-item a');
    }

    get popularProductsTitle() {
        return cy.get('h2.products-section-title.text-uppercase').first();
    }

    get featuredProductsSection() {
        return cy.get('article.product-miniature div.thumbnail-container div.product-description h3 a');
    }

   
    // region Methods

    getProcductPrice(element) {
        return element.parents('div.product-description')
            .find('div.product-price-and-shipping span.price')
    }

    // endregion

}

export const storeHomepage = new StoreHomepage();