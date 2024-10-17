package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserUtility {
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BrowserUtility.class);

    public void openBrowser(String browserType) {
        logger.info("Opening browser: {}", browserType);
        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--enable-notifications");
            // options.addArguments("--headless");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            logger.info("Chrome browser opened successfully");
        } else {
            logger.error("Unsupported browser type: {}", browserType);
            throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }

    public void closeBrowser() {
        if (driver != null) {
            logger.info("Closing the browser");
            driver.quit();
            logger.info("Browser closed successfully");
        } else {
            logger.warn("Attempted to close the browser, but no browser is currently open");
        }
    }

    public void restartBrowser() {
        logger.info("Restarting the browser");
        closeBrowser();
        openBrowser("chrome"); // or any other browser type
        logger.info("Browser restarted successfully");
    }

    public WebDriver getDriver() {
        if (driver == null) {
            logger.warn("WebDriver is not initialized. Returning null.");
        }
        return driver;
    }

    public void setImplicitTimeout(int seconds) {
        if (driver != null) {
            logger.info("Setting implicit wait timeout to: {} seconds", seconds);
            driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        } else {
            logger.warn("Attempted to set implicit timeout, but no browser is currently open");
        }
    }
}

//public class BrowserUtility {
//    private WebDriver driver;
//
//    public void openBrowser(String browserType) {
//        if (browserType.equalsIgnoreCase("chrome")) {
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--disable-notifications");
//            options.addArguments("--enable-notifications");
//            // Uncomment the following line to run in headless mode
//            // options.addArguments("--headless");
//
//            driver = new ChromeDriver(options);
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        }
//        // You can add more browser types as needed
//    }
//
//    public void closeBrowser() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    public void restartBrowser() {
//        closeBrowser();
//        openBrowser("chrome"); // or any other browser type
//    }
//
//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    public void setImplicitTimeout(int seconds) {
//        if (driver != null) {
//            driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
//        }
//    }
//
//    // Additional methods for configuring browser settings can be added here
//}

