import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.clipboard.HasClipboard;

public class TwoDeviceChatVerification {


        public static void main(String[] args) {
            Thread user1 = new Thread(() -> runUser1("ZD2226SZNF", 4723));
            Thread user2 = new Thread(() -> runUser2("emulator-5554", 4725));

            user1.start();
            user2.start();
        }
        private static volatile String sharedInviteLink = null;

        // USER 1 (SENDER)
        public static void runUser1(String udid, int port) {
            AndroidDriver driver = null;
            try {
                driver = createDriver(udid, port);
                login(driver, "User1");

                Thread.sleep(1000);
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
                Thread.sleep(2000);
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/share_link_button")).click();


                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                WebElement inviteCode = wait.until(ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.id("chat.delta.privitty:id/invite_link")
                ));

                String generatedInviteCode = inviteCode.getText();  // e.g., "user123@privitty.com"
                sharedInviteLink = generatedInviteCode;
//                System.out.println("📩 User1 Invite Link: " + sharedInviteCode);

            } catch (Exception e) {
                System.out.println("User1 error: " + e.getMessage());
            } finally {


            }
        }

        // USER 2 (RECEIVER + VERIFY)
        public static void runUser2(String udid, int port) {
            AndroidDriver driver = null;
            try {
                driver = createDriver(udid, port);
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();
                Thread.sleep(2000);
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/name_text")).sendKeys("User2");
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();

                Thread.sleep(1000);
                driver.findElement(AppiumBy.id("chat.delta.privitty:id/menu_qr")).click();
                Thread.sleep(3000);
                driver.findElement(AppiumBy.accessibilityId("More options")).click();
                Thread.sleep(2000);
                while (sharedInviteLink == null) {
                    System.out.println("⏳ Waiting for User1 to generate the invite link...");
                    Thread.sleep(2000);
                }

                // Use User1's generated invite link
                System.out.println("User2 got invite link: " + sharedInviteLink);
                ((HasClipboard) driver).setClipboardText(sharedInviteLink);
                Thread.sleep(2000);
                driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Paste from Clipboard\")")).click();
                Thread.sleep(2000);
                driver.findElement(AppiumBy.id("android:id/button1")).click();
                Thread.sleep(4000);
                driver.findElement(AppiumBy.accessibilityId("Message")).sendKeys("Hello");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
                wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Send"))).click();
                Thread.sleep(500);
                driver.findElement(AppiumBy.accessibilityId("Message")).sendKeys("Helloooooooo User 1");
                WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(200));
                wait2.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Send"))).click();

            } catch (Exception e) {
                System.out.println("User2 error: " + e.getMessage());
            } finally {
                if (driver != null) driver.quit();
            }
        }

        // COMMON UTILITIES

        public static AndroidDriver createDriver(String udid, int port) throws Exception {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("appium:autoGrantPermissions", true);
            cap.setCapability("platformName", "Android");
            cap.setCapability("appium:udid", udid);
            cap.setCapability("appium:deviceName", udid);
            cap.setCapability("appium:systemPort", port == 4723 ? 8200 : 8201);
            cap.setCapability("appium:automationName", "UiAutomator2");
            cap.setCapability("appium:appPackage", "chat.delta.privitty");
            cap.setCapability("appium:appActivity", "org.thoughtcrime.securesms.RoutingActivity");

            return new AndroidDriver(new URL("http://127.0.0.1:" + port + "/"), cap);
        }

        public static void login(AndroidDriver driver, String userName) throws InterruptedException {
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/name_text")).sendKeys(userName);
            driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();
            Thread.sleep(2000);
        }


}
