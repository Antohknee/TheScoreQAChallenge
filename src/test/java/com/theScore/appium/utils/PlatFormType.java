package com.theScore.appium.utils;

public enum PlatFormType {
    ANDROID,
    IOS;

    @Override
    public String toString() {
        switch(this) {
            case ANDROID: return "android";
            case IOS: return "ios";
            default: throw new IllegalArgumentException();
        }
    }

    public String automationName() {
        switch(this) {
            case ANDROID: return "UiAutomator2";
            case IOS: return "XCUITest";
            default: throw new IllegalArgumentException();
        }
    }
}
