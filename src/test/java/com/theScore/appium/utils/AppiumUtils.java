package com.theScore.appium.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class AppiumUtils {

    PlatFormType pt;
    AppiumDriver driver;
    public WebDriverWait wait;

    public AppiumUtils(PlatFormType pt) {
        this.pt = pt;
    }

    public void closeApp() {
        if (driver != null) {
            driver.quit();
        }
    }

    public AppiumDriver launchApp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("app", System.getProperty("user.dir")+"/apps/theScoreAPKPure.apk");
        caps.setCapability("automationName", pt.automationName());
        caps.setCapability("deviceName", "theScore_Test");
        caps.setCapability("platformName", pt.toString());

        URL url = new URL("http://localhost:4723/");
        driver = new AppiumDriver(url, caps);

        wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.visibilityOf(getElementByText("WELCOME")));
        return driver;
    }

    public WebElement getElementById(String id) {
        try {
            return driver.findElement(By.id(id));
        } catch(NoSuchElementException e) {
            System.out.println("Element with ID " + id + " not found.");
            throw e;
        }
    }

    public WebElement getElementByText(String text) {
        try {
            return driver.findElement(By.xpath("//*[contains(@text,"+text+")]"));
        } catch (NoSuchElementException e) {
            System.out.println("Element with text " + text + " not found.");
            throw e;
        }
    }

    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void search(WebElement element, String text) {
        element.click();
        element.sendKeys(text);
    }

    public void swipe(SwipeDirection direction, double percentage) {
        Dimension size = driver.manage().window().getSize();
        int startX, endX, startY, endY;

        switch (direction) {
            case UP:
                startX = size.width / 2;
                startY = (int) (size.height * (1 - percentage));
                endX = startX;
                endY = (int) (size.height * percentage);
                break;
            case DOWN:
                startX = size.width / 2;
                startY = (int) (size.height * percentage);
                endX = startX;
                endY = (int) (size.height * (1 - percentage));
                break;
            case LEFT:
                startY = size.height / 2;
                startX = (int) (size.width * (1 - percentage));
                endY = startY;
                endX = (int) (size.width * percentage);
                break;
            case RIGHT:
                startY = size.height / 2;
                startX = (int) (size.width * percentage);
                endY = startY;
                endX = (int) (size.width * (1 - percentage));
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        swipeAction(startX, startY, endX, endY, 1000);
    }

    public void swipeAction(int startX, int startY, int endX, int endY, int duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(duration), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public void scrollUntilElementVisible(By locator, SwipeDirection direction, int maxScrolls, double swipePercentage) {
        int swipeCount = 0;
        while (swipeCount <= maxScrolls) {
            if (isElementVisible(locator)) {
                return;
            }
            swipe(direction, swipePercentage);
            swipeCount++;
        }
        throw new NoSuchElementException("Element with locator " + locator + " not found.");
    }
}
