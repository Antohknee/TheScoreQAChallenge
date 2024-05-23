package com.theScore.appium.pages;

import com.theScore.appium.utils.AppiumUtils;
import com.theScore.appium.utils.SwipeDirection;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TSProfilePage {

    AppiumDriver driver;
    AppiumUtils utils;

    @FindBy(xpath = "//android.widget.LinearLayout[@content-desc=\"Info\"]")
    public WebElement infoTab;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/txt_player_name\"]")
    public WebElement playerName;

    @FindBy(xpath = "//android.widget.HorizontalScrollView[@resource-id=\"com.fivemobile.thescore:id/tabLayout\"]")
    public WebElement scrollTab;

    @FindBy(xpath = "//android.widget.TextView[@text,\"2023-24 SEASON\"]")
    public WebElement seasonOfStats;

    @FindBy(xpath = "//android.widget.LinearLayout[@content-desc=\"Season\"]")
    public WebElement seasonTab;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/team_name\"]")
    public WebElement teamName;

    @FindBy(xpath = "//android.widget.LinearLayout[@content-desc=\"Team Stats\"]")
    public WebElement teamStatsTab;


    public void selectTab(String tabName) {
        Point location = scrollTab.getLocation();
        Dimension size = scrollTab.getSize();
        int y = (2*location.getY() + size.height) / 2;
        SwipeDirection directions = new SwipeDirection((int)(0.9*size.getWidth()), y, (int)(size.getWidth() * 0.1), y);

        String formatedTabBy = String.format("//android.widget.LinearLayout[@content-desc=\"%s\"]", tabName);
        By tabBy = By.xpath(formatedTabBy);
        utils.scrollUntilElementVisible(tabBy, directions, 1);
        driver.findElement(tabBy).click();
    }

    public void verifyInfo(String location, String arena) {
        utils.wait.until(ExpectedConditions.visibilityOf(utils.getElementByText("Location")));
        Assertions.assertTrue(utils.isElementVisible(driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + location + "\"]"))));
        Assertions.assertTrue(utils.isElementVisible(driver.findElement(By.xpath("//android.widget.TextView[@text=\""+ arena + "\"]"))));
    }
    public TSProfilePage(AppiumDriver driver, AppiumUtils utils) {
        this.driver = driver;
        this.utils = utils;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
