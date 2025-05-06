package pages;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String name) throws InterruptedException {
        System.out.println("Waiting for signup button...");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("chat.delta.privitty:id/signup_button"))).click();

        System.out.println("Waiting for name input...");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("chat.delta.privitty:id/name_text"))).sendKeys(name);

        System.out.println("Submitting signup...");
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();

        Thread.sleep(2000);  // Optional small wait after signup
    }
}
