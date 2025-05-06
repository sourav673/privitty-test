package pages;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class GroupPage {
    AndroidDriver driver;

    public GroupPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void createGroup(String groupName) throws InterruptedException {
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/fab")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"New Group\")")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/group_name")).sendKeys(groupName);
        driver.findElement(AppiumBy.accessibilityId("Create Group")).click();
        Thread.sleep(1000);
    }

    public void sendMessages(String[] messages) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        for (String msg : messages) {
            driver.findElement(AppiumBy.accessibilityId("Message")).sendKeys(msg);
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Send"))).click();
            Thread.sleep(500);
        }
    }

    public void navigateBackToMain() throws InterruptedException {
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/up_button")).click();
        Thread.sleep(1000);
    }
}
