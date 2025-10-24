package java_test.java_test;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class SimpleAppiumTest {

    static AndroidDriver<AndroidElement> driver;

    public static void main(String[] args) throws Exception {
        // Set Android SDK path
//        System.setProperty("ANDROID_HOME", "C:\\Users\\tewod\\AppData\\Local\\Android\\Sdk");
//        System.setProperty("ANDROID_SDK_ROOT", "C:\\Users\\tewod\\AppData\\Local\\Android\\Sdk");
        
        DesiredCapabilities caps = new DesiredCapabilities();
        
        // Device capabilities - UPDATED to match your emulator
        caps.setCapability("deviceName", "sdk_gphone64_x86_64");
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformVersion", "16"); // CHANGED from 13 to 16
        caps.setCapability("udid", "emulator-5554"); // Added UDID to be specific

        // FIXED: Use correct app package and activity
        // Option 1: Calculator app (more reliable)
        caps.setCapability("appPackage", "com.android.camera2");
        caps.setCapability("appActivity", "com.android.camera.CameraLauncher");
        
        // Option 2: If you prefer camera app, use this instead:
        // caps.setCapability("appPackage", "com.android.camera2");
        // caps.setCapability("appActivity", "com.android.camera.CameraLauncher");
        
        // Option 3: Settings app (most reliable for testing)
        // caps.setCapability("appPackage", "com.android.settings");
        // caps.setCapability("appActivity", "com.android.settings.Settings");

        // Optional capabilities
        caps.setCapability("noReset", true);
        caps.setCapability("autoGrantPermissions", true);

        // Use the correct endpoint for Appium 2.x
        URL url = new URL("http://127.0.0.1:4723/");
        
        try {
            System.out.println("Connecting to Appium server...");
            System.out.println("Device: emulator-5554 (Android 16)");
            System.out.println("App: Calculator");
            
            driver = new AndroidDriver<>(url, caps);
            
            System.out.println("✅ Application started successfully!");
            System.out.println("Session ID: " + driver.getSessionId());
            System.out.println("Device OS: " + driver.getCapabilities().getCapability("platformVersion"));
            System.out.println("App Package: " + driver.getCapabilities().getCapability("appPackage"));
            
            // Wait for 10 seconds to see the app
            System.out.println("Waiting for 10 seconds...");
            Thread.sleep(10000);
            
            System.out.println("Test completed successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            
            // More specific error handling
            if (e.getMessage().contains("Unable to find an active device")) {
                System.out.println("💡 Tip: Check if your emulator is running with 'adb devices'");
            }
            
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("Driver closed.");
            }
        }
    }
}