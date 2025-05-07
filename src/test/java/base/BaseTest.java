package base;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest {
    public AndroidDriver driver;

    public void setupDriver(String udid, int port) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:udid", udid);
        cap.setCapability("appium:deviceName", udid);
        cap.setCapability("appium:systemPort", port == 4723 ? 8200 : 8201);
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:appPackage", "chat.delta.privitty");
        cap.setCapability("appium:appActivity", "org.thoughtcrime.securesms.RoutingActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/"), cap);
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void teardownDriver() {

    }
}
