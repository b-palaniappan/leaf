package io.c12.bala.web.leaf.login;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("/")
public class HomePage extends FluentPage {

    @FindBy(css = "#usersListLink")
    private FluentWebElement usersListLink;

    @FindBy(css = "#auditUserLink")
    private FluentWebElement auditUsersLink;

    @FindBy(css = "#logoutBtn")
    private FluentWebElement logoutLink;

    public String getLogoutButtonText() {
        return logoutLink.text();
    }
}
