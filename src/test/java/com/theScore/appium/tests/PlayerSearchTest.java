package com.theScore.appium.tests;

import com.theScore.appium.pages.TSHomePage;
import com.theScore.appium.pages.TSWelcomePage;
import com.theScore.appium.utils.AppiumUtils;
import com.theScore.appium.utils.PlatFormType;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.MalformedURLException;
import java.util.stream.Stream;

class PlayerSearchTest {

    private final AppiumUtils utils = new AppiumUtils(PlatFormType.ANDROID);
    private final TSHomePage homePage;
    private final TSWelcomePage welcomePage;

    private static Stream<Arguments> playersAndDOBs() {
        return Stream.of(
                Arguments.of("Jayson Tatum", "1998-03-03"),
                Arguments.of("LeBron James", "1984-12-30"),
                Arguments.of("Jamal Murray", "1997-02-23")
        );
    }
    
    @BeforeEach
    void setup() {
        welcomePage.completeTutorial();
    }

    @ParameterizedTest
    @MethodSource("playersAndDOBs")
    void SearchNBAPlayerTest(String player, String expectedDOB) {
        homePage.dismissModal.click();
        homePage.searchTeamPlayerNews(player);

        // Check that name of profile matches the desired player
        Assertions.assertEquals(homePage.playerName.getText(), player);

        // Check that clicking season tab contains stats for the 2023-24 season
        homePage.seasonTab.click();
        Assertions.assertTrue(utils.isElementVisible(homePage.seasonOfStats));

        // Check player date of birth matches up
        homePage.infoTab.click();
        Assertions.assertTrue(homePage.birthDate.getText().contains(expectedDOB));

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

    public PlayerSearchTest() throws MalformedURLException {
        AppiumDriver driver = utils.launchApp();
        welcomePage = new TSWelcomePage(driver, utils);
        homePage = new TSHomePage(driver, utils);
    }
}
