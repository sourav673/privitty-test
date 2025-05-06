package pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.clipboard.HasClipboard;

public class InvitePage {
    AndroidDriver driver;

    public InvitePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public String generateInviteLink() throws InterruptedException {
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/share_link_button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement inviteCode = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("chat.delta.privitty:id/invite_link")
        ));
        return inviteCode.getText();
    }

    public void joinViaInviteLink(String link) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
        Thread.sleep(3000);
        driver.findElement(AppiumBy.accessibilityId("More options")).click();
        Thread.sleep(2000);
        while (link == null) {
            System.out.println("⏳ Waiting for User1 to generate the invite link...");
            Thread.sleep(2000);
        }
        // Use User1's generated invite link
        System.out.println("📥 User2 got invite link: " + link);
        ((HasClipboard) driver).setClipboardText(link);
        Thread.sleep(3000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Paste from Clipboard\")")).click();
        Thread.sleep(2000);
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        Thread.sleep(3000);
    }
}
