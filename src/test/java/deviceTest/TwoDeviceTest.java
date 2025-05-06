package deviceTest;

import base.BaseTest;
import pages.InvitePage;
import pages.LoginPage;

public class TwoDeviceTest {
    private static volatile String sharedInviteLink = null;

    public static void main(String[] args) {
        Thread user1 = new Thread(() -> runUser1("ZD2226SZNF", 4723));
        Thread user2 = new Thread(() -> runUser2("emulator-5554", 4725));

        user1.start();
        user2.start();
    }

    public static void runUser1(String udid, int port) {
        BaseTest base = new BaseTest();
        try {
            base.setupDriver(udid, port);
            LoginPage login = new LoginPage(base.driver);
            InvitePage invite = new InvitePage(base.driver);

            login.login("User1");
            sharedInviteLink = invite.generateInviteLink();
        } catch (Exception e) {
            System.out.println("User1 error: " + e.getMessage());
        }
    }

    public static void runUser2(String udid, int port) {
        BaseTest base = new BaseTest();
        try {
            base.setupDriver(udid, port);
            LoginPage login = new LoginPage(base.driver);
            InvitePage invite = new InvitePage(base.driver);

            login.login("User2");

            while (sharedInviteLink == null) {
                System.out.println("⏳ Waiting for invite link...");
                Thread.sleep(2000);
            }

            invite.joinViaInviteLink(sharedInviteLink);
        } catch (Exception e) {
            System.out.println("User2 error: " + e.getMessage());
        }
    }
}
