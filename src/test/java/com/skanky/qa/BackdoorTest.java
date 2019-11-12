package com.skanky.qa;


import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackdoorTest {

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700000);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "espresso");
        capabilities.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir") + "/app-debug.apk");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        try {
            driver.quit();
        } catch (Exception ignored) {
        }
    }


    @Test
    public void mobileBackdoor() {
        ImmutableMap<String, Object> scriptArgs = ImmutableMap.of(
                "target", "activity",
                "methods", Arrays.asList(ImmutableMap.of(
                        "name", "raiseToast",
                        "args", Arrays.asList(ImmutableMap.of(
                                "value", "Hello from the test script!",
                                "type", "String"
                        ))
                ))
        );

        driver.executeScript("mobile: backdoor", scriptArgs);

        ImmutableMap<String, String> uiAutomatorArgs = ImmutableMap.of(
                "strategy", "textContains",
                "locator", "BOLD",
                "action", "click"
        );

        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {
        }

        List<Object> result = (List<Object>) driver.executeScript("mobile: uiautomator", uiAutomatorArgs);
        Map<String, Object> left = ((Map<String, Object>) result.get(0));
        left.get("left");
        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {
        }
    }

}
