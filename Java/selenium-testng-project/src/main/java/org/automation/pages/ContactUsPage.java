package org.automation.pages;

import org.framework.base.PageBase;
import org.framework.htmls.impl.*;
import org.openqa.selenium.By;

public class ContactUsPage extends PageBase {

    public HtmlElement contactUsHeader = new HtmlElement(By.cssSelector("div.form-group.row div h3"));
    public HtmlSelect subjectDropdown = new HtmlSelect(By.cssSelector("select#id_contact"));
    public HtmlInput emailAddressInput = new HtmlInput(By.cssSelector("input#email"));
    public HtmlTextArea messageBox = new HtmlTextArea(By.cssSelector("textarea#contactform-message"));
    public HtmlButton sendButton = new HtmlButton(By.name("submitMessage"));
    public HtmlElement validationMessage = new HtmlElement(By.cssSelector("div.alert.alert-danger"));

}
