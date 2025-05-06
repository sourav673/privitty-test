import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class AppTest {

    static AppiumDriver driver;

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        openMobileApp();
        loginTest();
    }

    public static void openMobileApp() throws MalformedURLException {

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("platformName", "Android");
        cap.setCapability("appium:platformVersion", "14.0");
        cap.setCapability("appium:deviceName", "ZD2226SZNF");
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("appium:appPackage", "chat.delta.privitty");
        cap.setCapability("appium:appActivity", "org.thoughtcrime.securesms.RoutingActivity");

        URL url = new URL("http://127.0.0.1:4723/");
//        driver1 = new RemoteWebDriver(new URL("http://192.168.1.100:4723/wd/hub")
        driver = new AppiumDriver(url, cap);
    }

    public static void loginTest() throws InterruptedException {
        System.out.println("Step 1: Signup");
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();
        System.out.println("Step 2: typing user");
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/name_text")).sendKeys("SouravTest");
        System.out.println("Step 3: btn click");
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/signup_button")).click();
//        driver.findElement(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button")).click();

        Thread.sleep(2000);
        try {
            String[] groups = {"Group 1", "Group 2"};
            String[] messages = {
                    "Hello Group Members",
                    "Hello Everyone Group Members",
                    "Hello.. How are you Group Members",
                    "Hello.. I Hope Everyone is fine Group Members"
            };
            for (String group : groups) {
                createGroup(group);
                sendMessages(messages);
                navigateBackToMain(); // Go back to group list
            }

            System.out.println("All groups created and messages sent.");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createGroup(String groupName) throws InterruptedException {
        System.out.println("👥 Creating group: " + groupName);
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/fab")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"New Group\")")).click();
        Thread.sleep(1000);
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/group_name")).sendKeys(groupName);
        driver.findElement(AppiumBy.accessibilityId("Create Group")).click();
        Thread.sleep(1000);
    }

    public static void sendMessages(String[] messages) throws InterruptedException {
        System.out.println("💬 Sending messages...");
        for (String msg : messages) {
            driver.findElement(AppiumBy.accessibilityId("Message")).sendKeys(msg);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Send"))).click();
            Thread.sleep(500);
        }
    }

    public static void navigateBackToMain() throws InterruptedException {
        driver.findElement(AppiumBy.id("chat.delta.privitty:id/up_button")).click();
        Thread.sleep(1000);
    }
}
