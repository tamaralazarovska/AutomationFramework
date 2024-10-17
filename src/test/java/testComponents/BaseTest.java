package testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import configs.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageObjects.CreateAccountPage;
import pageObjects.LogInPage;
import utils.BrowserUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    public WebDriver driver;
    public LogInPage logInPage;
    public CreateAccountPage createAccountPage;
    public BrowserUtility browserUtility;
    public ConfigReader configReader;

    public BaseTest() {
        browserUtility = new BrowserUtility();
        // Initialize the ConfigReader with the path to your configuration file
        configReader = new ConfigReader(System.getProperty("user.dir") + "/src/main/resources/config_chrome.properties");
    }

    public WebDriver initializeDriver() throws IOException {
        logger.info("Initializing the WebDriver");

        // Get browser name from ConfigReader
        String browserName = configReader.getBrowser();
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
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
        driver = initializeDriver();
        logInPage = new LogInPage(driver);
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
//public class BaseTest {
//    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
//    public WebDriver driver;
//    public LogInPage logInPage;
//    public CreateAccountPage createAccountPage;
//    public BrowserUtility browserUtility;
//    public ConfigReader configReader;
//
//    public BaseTest() {
//        browserUtility = new BrowserUtility();
//        // Initialize the ConfigReader with the path to your configuration file
//        configReader = new ConfigReader(System.getProperty("user.dir") + "/src/main/resources/config.properties");
//    }
//
//    public WebDriver initializeDriver() throws IOException {
//        logger.info("Initializing the WebDriver");
//
//        // Get browser name from ConfigReader
//        String browserName = configReader.getBrowser();
//        logger.info("Browser selected: {}", browserName);
//
//        // Initialize browser using BrowserUtility
//        switch (browserName.toLowerCase()) {
//            case "chrome":
//                browserUtility.openBrowser("chrome");
//                break;
//            case "firefox":
//                browserUtility.openBrowser("firefox");
//                break;
//            case "edge":
//                browserUtility.openBrowser("edge");
//                break;
//            default:
//                logger.error("Browser not supported: {}", browserName);
//                throw new IllegalArgumentException("Browser not supported: " + browserName);
//        }
//
//        driver = browserUtility.getDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        logger.info("WebDriver initialized and window maximized");
//        return driver;
//    }
//
//    public List<HashMap<String, String>> loadLogInData(String filePath) throws IOException {
//        logger.info("Loading login data from: {}", filePath);
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public List<HashMap<String, String>> loadCreateAccountData(String filePath) throws IOException {
//        logger.info("Loading account creation data from: {}", filePath);
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        logger.info("Taking screenshot for test case: {}", testCaseName);
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
//        FileUtils.copyFile(source, file);
//        logger.info("Screenshot saved at: {}", file.getAbsolutePath());
//        return file.getAbsolutePath();
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public LogInPage launchApplication() throws IOException {
//        logger.info("Launching application");
//        driver = initializeDriver();
//        logInPage = new LogInPage(driver);
//        logInPage.goTo();
//        logger.info("Application launched successfully");
//        return logInPage;
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        logger.info("Tearing down the WebDriver");
//        if (driver != null) {
//            browserUtility.closeBrowser(); // Use BrowserUtility to close the browser
//            logger.info("Browser closed successfully");
//        }
//    }
//
//    public void restartBrowser() {
//        logger.info("Restarting the browser");
//        browserUtility.restartBrowser();
//        driver = browserUtility.getDriver(); // Reassign driver after restart
//        logger.info("Browser restarted successfully");
//    }
//}

//public class BaseTest {
//    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
//    public static WebDriver driver;
//    public LogInPage logInPage;
//    public CreateAccountPage createAccountPage;
//    public BrowserUtility browserUtility;
//    public ConfigReader configReader;
//
//    public BaseTest() {
//        browserUtility = new BrowserUtility();
//    }
//
//    public WebDriver initializeDriver() throws IOException {
//        logger.info("Initializing the WebDriver");
//        Properties prop = new Properties();
//        FileInputStream fis = new FileInputStream(System.getProperty("config_chrome.properties"));
//        prop.load(fis);
//
//        String browserName = prop.getProperty("browser");
//        logger.info("Browser selected: {" + browserName + "}", browserName);
//
//        // Initialize browser using BrowserUtility
//        if (browserName.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            ChromeOptions chromeOptions = new ChromeOptions();
//            driver = new ChromeDriver(chromeOptions);
//        } else if (browserName.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            FirefoxOptions firefoxOptions = new FirefoxOptions();
//            driver = new FirefoxDriver(firefoxOptions);
//        } else if (browserName.equalsIgnoreCase("edge")) {
//            WebDriverManager.edgedriver().setup();
//            EdgeOptions edgeOptions = new EdgeOptions();
//            driver = new EdgeDriver(edgeOptions);
//        } else {
//            logger.error("Browser not supported: {}", browserName);
//            throw new IllegalArgumentException("Browser not supported: " + browserName);
//        }
//
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        logger.info("WebDriver initialized and window maximized");
//        return  driver;
//    }
//
//    public List<HashMap<String, String>> loadLogInData(String filePath) throws IOException {
//        logger.info("Loading login data from: {}", filePath);
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public List<HashMap<String, String>> loadCreateAccountData(String filePath) throws IOException {
//        logger.info("Loading account creation data from: {}", filePath);
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        logger.info("Taking screenshot for test case: {}", testCaseName);
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
//        FileUtils.copyFile(source, file);
//        logger.info("Screenshot saved at: {}", file.getAbsolutePath());
//        return file.getAbsolutePath();
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public LogInPage launchApplication() throws IOException {
//        logger.info("Launching application");
//        driver = initializeDriver();
//        logInPage = new LogInPage(driver);
//        logInPage.goTo();
//        logger.info("Application launched successfully");
//        return logInPage;
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        logger.info("Tearing down the WebDriver");
//        if (driver != null) {
//            browserUtility.closeBrowser(); // Use BrowserUtility to close the browser
//            logger.info("Browser closed successfully");
//        }
//    }
//
//    public void restartBrowser() {
//        logger.info("Restarting the browser");
//        browserUtility.restartBrowser();
//        driver = browserUtility.getDriver(); // Reassign driver after restart
//        logger.info("Browser restarted successfully");
//    }
//}


//public class BaseTest {
//    public WebDriver driver;
//    public LogInPage logInPage;
//    public CreateAccountPage createAccountPage;
//    public BrowserUtility browserUtility;
//    public ConfigReader configReader;
//
//    public BaseTest() {
//        browserUtility = new BrowserUtility();
//    }
//
//    public WebDriver initializeDriver() throws IOException {
//        Properties prop = new Properties();
//        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\GlobalData.properties");
//        prop.load(fis);
//
//        String browserName = prop.getProperty("browser");
//
//        // Initialize browser using BrowserUtility
//        if (browserName.equalsIgnoreCase("chrome")) {
//            browserUtility.openBrowser("chrome");
//            driver = browserUtility.getDriver();
//        } else if (browserName.equalsIgnoreCase("firefox")){
//            browserUtility.openBrowser("firefox");
//            driver = browserUtility.getDriver();
//        } else if (browserName.equalsIgnoreCase("edge")){
//            browserUtility.openBrowser(("edge"));
//            driver = browserUtility.getDriver();
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        return driver;
//    }
//
//    public List<HashMap<String, String>> loadLogInData(String filePath) throws IOException {
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public List<HashMap<String, String>> loadCreateAccountData(String filePath) throws IOException {
//        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
//    }
//
//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
//        FileUtils.copyFile(source, file);
//        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public LogInPage launchApplication() throws IOException {
//        driver = initializeDriver();
//        logInPage = new LogInPage(driver);
//        logInPage.goTo();
//        return logInPage;
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            browserUtility.closeBrowser(); // Use BrowserUtility to close the browser
//        }
//    }
//    public void restartBrowser() {
//        browserUtility.restartBrowser();
//        driver = browserUtility.getDriver(); // Reassign driver after restart
//    }
//}





//package testComponents;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import pageObjects.CreateAccountPage;
//import pageObjects.LogInPage;
//import utils.BrowserUtility;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//
//public class BaseTest {
//    public WebDriver driver;
//    public LogInPage logInPage;
//    public CreateAccountPage createAccountPage;
//    public BrowserUtility browserUtility;
//
//
//    public WebDriver initializeDriver() throws IOException {
//    Properties prop = new Properties();
//        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\GlobalData.properties");
//    prop.load(fis);
//
////        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
//        //Ova e za preku Maven commands
//    String browserName = prop.getProperty("browser");
//
//
//    if (browserName.equalsIgnoreCase("chrome")) {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//    }
//
////    } else if(browserName.equalsIgnoreCase("firefox")){
////        //Firefox
////        System.setProperty("webdriver.firefox.driver","firefox.exe");
////         driver = new EdgeDriver();
////    } else if (browserName.equalsIgnoreCase("edge")){
////        //Edge
////        System.setProperty("webdriver.edge.driver","edge.exe");
////         driver = new EdgeDriver();
////    }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        return driver;
//    }
//    public List<HashMap<String, String>> loadLogInData(String filePath) throws IOException {
//        //read Json to String
//        String jsonContent = FileUtils.readFileToString(new File(filePath));
//
//        //String to HashMap
//        ObjectMapper mapper = new ObjectMapper();
//        List<HashMap<String, String>> logInDataList = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
//        });
//        return logInDataList;
//    }
//    public List<HashMap<String, String>> loadCreateAccountData(String filePath) throws IOException {
//        //read Json to String
//        String jsonContent = FileUtils.readFileToString(new File(filePath));
//
//        //String to HashMap
//        ObjectMapper mapper = new ObjectMapper();
//        List<HashMap<String, String>> accountDataList = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
//        });
//        return accountDataList;
//    }
//    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
//        TakesScreenshot ts = (TakesScreenshot)driver;
//        File source = ts.getScreenshotAs(OutputType.FILE);
//        File file = new File(System.getProperty("user.dir")+"//reports//" + testCaseName + ".png");
//        FileUtils.copyFile(source, file);
//        return System.getProperty("user.dir")+"//reports//" + testCaseName + ".png";
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public LogInPage launchApplication() throws IOException {
//        driver = initializeDriver();
//        logInPage = new LogInPage(driver);
//        logInPage.goTo();
//        return logInPage;
//
//    }
//    @AfterMethod(alwaysRun = true)
//    public void tearDown(){
//        driver.close();
//    }
//}
