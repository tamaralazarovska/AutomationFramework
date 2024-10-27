package testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import configs.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObjects.CreateAccountPage;
import pageObjects.LogInPage;
import utils.BrowserUtility;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static configs.ConfigReader.properties;

public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    public static WebDriver driver;
    public LogInPage logInPage;
    public CreateAccountPage createAccountPage;
    public BrowserUtility browserUtility;
    public ConfigReader configReader;

    public BaseTest() {
        browserUtility = new BrowserUtility();
        // Initialize the ConfigReader with the path to your configuration file
        configReader = new ConfigReader(System.getProperty("user.dir") + "/src/main/resources/config_chrome.properties");
    }

    public WebDriver initializeDriver(String browser) throws IOException {
        logger.info("Initializing the WebDriver");

        // Get browser name from ConfigReader
        String browserName = browser;
//        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        logger.info("Browser selected: {}", browserName);

        // Initialize browser using BrowserUtility
        switch (browserName.toLowerCase()) {
            case "chrome":
                browserUtility.openBrowser("chrome");
                break;
            case "firefox":
                browserUtility.openBrowser("firefox");
                break;
            case "edge":
                browserUtility.openBrowser("edge");
                break;
            default:
                logger.error("Browser not supported: {}", browserName);
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver = browserUtility.getDriver();
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        String windowSize = properties.getProperty("windowSize", "maximize");
        if ("maximize".equalsIgnoreCase(windowSize)) {
            driver.manage().window().maximize();
        }
        logger.info("WebDriver initialized and window maximized");
        return driver;
    }

    public List<HashMap<String, String>> loadLogInData(String filePath) throws IOException {
        logger.info("Loading login data from: {}", filePath);
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public List<HashMap<String, String>> loadCreateAccountData(String filePath) throws IOException {
        logger.info("Loading account creation data from: {}", filePath);
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        logger.info("Taking screenshot for test case: {}", testCaseName);
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        logger.info("Screenshot saved at: {}", file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    @BeforeMethod(alwaysRun = true)
    public LogInPage launchApplication() throws IOException {
        logger.info("Launching application");
        driver = initializeDriver(ConfigReader.getChromeBrowser());
        logInPage = new LogInPage(driver);
        createAccountPage = new CreateAccountPage(driver);
        logInPage.goTo();
        logger.info("Application launched successfully");
        return logInPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Tearing down the WebDriver");
        if (driver != null) {
            browserUtility.closeBrowser(); // Use BrowserUtility to close the browser
            logger.info("Browser closed successfully");
        }
    }

    public void restartBrowser() {
        logger.info("Restarting the browser");
        browserUtility.restartBrowser();
        driver = browserUtility.getDriver(); // Reassign driver after restart
        logger.info("Browser restarted successfully");
    }
}

