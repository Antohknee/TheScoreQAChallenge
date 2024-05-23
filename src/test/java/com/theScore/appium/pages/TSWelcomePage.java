package com.theScore.appium.pages;

import com.theScore.appium.utils.AppiumUtils;
import com.theScore.appium.utils.SwipeDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TSWelcomePage {

    AppiumDriver driver;
    AppiumUtils utils;

    @FindBy(xpath = "//android.widget.TextView[@text='Continue']")
    public WebElement continueButton;

    @FindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.fivemobile.thescore:id/recyclerView\"]//android.widget.LinearLayout[1]")
    public WebElement firstOptionOfSearch;

    @FindBy(xpath = "//android.widget.TextView[@text='Get Started']")
    public WebElement getStartedButton;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id=\"com.fivemobile.thescore:id/pullToRefresh\"]")
    public WebElement leagueCatalog;

    @FindBy(xpath = "//android.widget.TextView[@text='Maybe Later']")
    public WebElement maybeLaterButton;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/search_bar_placeholder\"]")
    public WebElement searchBarButton;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.fivemobile.thescore:id/search_src_text\"]")
    public WebElement searchInputField;


    public void completeTutorial() {
        getStartedButton.click();
        selectFavoriteLeague("NBA Basketball");
        continueButton.click();
        maybeLaterButton.click();
        searchBarButton.click();
        searchFavoriteTeam("Canada");
        firstOptionOfSearch.click();
        continueButton.click();
        continueButton.click();
        maybeLaterButton.click();
    }

    public void selectFavoriteLeague(String league) {
        Point location = leagueCatalog.getLocation();
        Dimension size = leagueCatalog.getSize();
        int x = size.getWidth()/2;
        SwipeDirection directions = new SwipeDirection(x, (int)(location.getY()+size.getHeight() * 0.75), x, location.getY());

        String formatedLeagueBy = String.format("//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/txt_name\" and @text=\"%s\"]", league);
        By leagueLocator = By.xpath(formatedLeagueBy);
        utils.scrollUntilElementVisible(leagueLocator, directions, 10);
        driver.findElement(leagueLocator).click();
    }

    public void searchFavoriteTeam(String team) {
        utils.search(searchInputField, team);
    }

    public TSWelcomePage(AppiumDriver driver, AppiumUtils utils) {
        this.driver = driver;
        this.utils = utils;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}