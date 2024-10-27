package utils;
import configs.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static configs.ConfigReader.properties;

public class BrowserUtility {
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BrowserUtility.class);

    public void openBrowser(String browserType) {
        logger.info("Opening browser: {}", browserType);
        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--enable-notifications");
//            options.addArguments("--headless", properties.getProperty("headless"));
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            logger.info("Chrome browser opened successfully");
        }else if (browserType.equalsIgnoreCase("edge")){
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--enable-notifications");
           // options.addArguments("--headless", properties.getProperty("headless"));
            driver = new EdgeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            logger.info("Edge browser opened successfully");
        }else if (browserType.equalsIgnoreCase("firefox")){
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--enable-notifications");
            //options.addArguments("--headless", properties.getProperty("headless"));
            driver = new FirefoxDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            logger.info("Firefox browser opened successfully");
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



