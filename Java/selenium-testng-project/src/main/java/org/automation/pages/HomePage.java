package org.automation.pages;

import org.framework.base.PageBase;
import org.framework.htmls.impl.HtmlButton;
import org.framework.htmls.impl.HtmlElement;
import org.framework.htmls.impl.HtmlInput;
import org.framework.htmls.impl.HtmlLink;
import org.openqa.selenium.By;

import java.util.List;

public class HomePage extends PageBase {

    public HomePage() {}

    public HtmlElement myStoreLogo = new HtmlElement(By.cssSelector("img.logo.img-fluid"));
    public HtmlLink contactUsLink = new HtmlLink(By.cssSelector("div#contact-link a"));
    public HtmlLink signInLink = new HtmlLink(By.cssSelector("div#_desktop_user_info div a"));
    public HtmlElement disabledCartIcon = new HtmlElement(By.cssSelector("div#_desktop_cart div.blockcart.cart-preview.inactive"));
    public HtmlElement enabledCartIcon = new HtmlElement(By.cssSelector("div#_desktop_cart div.blockcart.cart-preview.active"));
    public HtmlElement topHeaderMenu = new HtmlElement(By.cssSelector("ul.top-menu li.category a:not(.dropdown-submenu)"));
    public HtmlElement menuHeader = new HtmlElement(By.xpath("//a[normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')= '%s')][@class='dropdown-item']"));
    public HtmlElement subMenuItems = new HtmlElement(By.cssSelector("ul.top-menu[data-depth='1'] li.category a.dropdown-submenu"));
    public HtmlInput yourEmailAddressInput = new HtmlInput(By.cssSelector("input[placeholder='Your email address']"));
    public HtmlButton subscribeButton = new HtmlButton(By.name("submitNewsletter"));

    // Page Methods

    public List<String> getTopHeaderMenuItems() {
        return topHeaderMenu.getAllVisibleElementsText();
    }

    public List<String> getSubMenuItems() {
        return subMenuItems.getAllElementsText().stream().map(String::toUpperCase).toList();
    }
}
