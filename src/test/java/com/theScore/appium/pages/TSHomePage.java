package com.theScore.appium.pages;

import com.theScore.appium.utils.AppiumUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TSHomePage {

    AppiumDriver driver;
    AppiumUtils utils;

    @FindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    public WebElement backButton;

    @FindBy(xpath = "//android.widget.ImageView[@resource-id=\"com.fivemobile.thescore:id/dismiss_modal\"]")
    public WebElement dismissModal;

    @FindBy(xpath = "//android.widget.ImageButton")
    public WebElement myAccount;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/txt_player_name\"]")
    public WebElement playerName;

    @FindBy(xpath = "//android.widget.TextView[@text,\"2023-24 SEASON\"]")
    public WebElement seasonOfStats;

    @FindBy(xpath = "//android.widget.LinearLayout[@content-desc=\"Season\"]")
    public WebElement seasonTab;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"com.fivemobile.thescore:id/search_bar_text_view\"]")
    public WebElement teamsPlayersNewsPlaceHolder;

    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.fivemobile.thescore:id/search_src_text\"]")
    public WebElement teamsPlayersNewsInput;

    public void searchTeamPlayerNews(String tpn) {
        teamsPlayersNewsPlaceHolder.click();
        utils.search(teamsPlayersNewsInput, tpn);
        String tpnXPath = "//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '"+tpn+"')]";
        utils.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tpnXPath)));
        driver.findElement(By.xpath(tpnXPath)).click();

    }
    public TSHomePage(AppiumDriver driver, AppiumUtils utils) {
        this.driver = driver;
        this.utils = utils;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
