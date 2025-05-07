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

    public String generateInviteLink() {
        try {
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
            Thread.sleep(2000);
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/share_link_button")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement inviteCode = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.id("chat.delta.privitty:id/invite_link")
            ));
            return inviteCode.getText();
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to generate invite link: " + e.getMessage());
        }
    }

    public void joinViaInviteLink(String link) {
        if (link == null || link.trim().isEmpty()) {
            throw new IllegalArgumentException("❗ Invite link is null or empty.");
        }

        try {
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
            Thread.sleep(3000);

            driver.findElement(AppiumBy.accessibilityId("More options")).click();
            Thread.sleep(2000);

            System.out.println("📥 User2 got invite link: " + link);
            ((HasClipboard) driver).setClipboardText(link);
            Thread.sleep(3000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement pasteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Paste from Clipboard\")")
            ));
            pasteButton.click();

            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.id("android:id/button1")
            ));
            confirmButton.click();

            Thread.sleep(4000);

            // Send message 1
            WebElement messageBox = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("Message")
            ));
            messageBox.sendKeys("Hello");

            WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("Send")
            ));
            sendBtn.click();

            // Send message 2
            messageBox.sendKeys("Helloooooooo User 1");
            sendBtn.click();

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to join via invite link: " + e.getMessage());
        }
    }
}
