package com.theScore.appium.tests;

import com.theScore.appium.pages.TSHomePage;
import com.theScore.appium.pages.TSWelcomePage;
import com.theScore.appium.utils.AppiumUtils;
import com.theScore.appium.utils.PlatFormType;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.MalformedURLException;

class PlayerSearchTest {

    private final AppiumUtils utils = new AppiumUtils(PlatFormType.ANDROID);
    private TSHomePage homePage;

    @BeforeEach
    void setup() throws MalformedURLException {
        AppiumDriver driver = utils.launchApp();
        TSWelcomePage welcomePage = new TSWelcomePage(driver, utils);
        homePage = new TSHomePage(driver, utils);
        welcomePage.completeTutorial();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jayson Tatum", "LeBron James", "Jamal Murray"})
    void SearchPlayerTest(String player) {
        homePage.dismissModal.click();
        homePage.searchTeamPlayerNews(player);

        // Check that name of profile matches the desired player
        Assertions.assertEquals(homePage.playerName.getText(), player);

        // Check that clicking season tab contains stats for the 2023-24 season
        homePage.seasonTab.click();
        Assertions.assertTrue(utils.isElementVisible(homePage.seasonOfStats));

        // Check that when going back shows the search input
        homePage.backButton.click();
        Assertions.assertTrue(utils.isElementVisible(homePage.teamsPlayersNewsInput));

        // Check going back again shows the homepage with the profile button on top left
        homePage.backButton.click();
        Assertions.assertTrue(utils.isElementVisible(homePage.myAccount));

    }

    @AfterEach
    void tearDown() {
        utils.closeApp();
    }
}
