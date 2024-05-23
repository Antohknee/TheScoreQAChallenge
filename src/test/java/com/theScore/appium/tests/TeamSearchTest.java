package com.theScore.appium.tests;

import com.theScore.appium.pages.TSHomePage;
import com.theScore.appium.pages.TSProfilePage;
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

class TeamSearchTest {

    private final AppiumUtils utils = new AppiumUtils(PlatFormType.ANDROID);
    private final TSHomePage homePage;
    private final TSProfilePage profilePage;
    private final TSWelcomePage welcomePage;

    private static Stream<Arguments> teams() {
        return Stream.of(
                Arguments.of("Boston Celtics", "Boston", "TD Garden"),
                Arguments.of("Los Angeles Lakers", "Los Angeles", "Crypto.com Arena"),
                Arguments.of("Denver Nuggets", "Denver", "Ball Arena")
        );
    }

    @BeforeEach
    void setup() {
        welcomePage.completeTutorial();
    }

    @ParameterizedTest
    @MethodSource("teams")
    void SearchNBATeamTest(String team, String location, String arenaName) {
        homePage.dismissModal.click();
        homePage.searchTeamPlayerNews(team);

        // Check that name of profile matches the desired team
        Assertions.assertEquals(profilePage.teamName.getText(), team);

        // Check that clicking season tab contains stats such as Point-per-game (PPG)
        profilePage.teamStatsTab.click();
        Assertions.assertTrue(utils.isElementVisible(utils.getElementByText("PPG")));

        // Check information of location and arena name is correct
        profilePage.selectTab("Info");
        profilePage.verifyInfo(location, arenaName);

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

    public TeamSearchTest() throws MalformedURLException {
        AppiumDriver driver = utils.launchApp();
        homePage = new TSHomePage(driver, utils);
        profilePage = new TSProfilePage(driver, utils);
        welcomePage = new TSWelcomePage(driver, utils);
    }
}
