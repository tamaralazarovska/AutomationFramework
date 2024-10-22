
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import com.aventstack.extentreports.Status;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testComponents.BaseTest;


import static testComponents.Listeners.extentTest;

//Trying ngrok
@Listeners(testComponents.Listeners.class)
public class LogInTest extends BaseTest {
    WebDriverWait wait;
    @Test(priority = 1, dataProvider = "getData")
    @Description("Verify that the account is successfully logged in")
    public void logInTest(HashMap<String, String> input) throws IOException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased wait time
        try {
            WebElement declineCookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyButtonDecline")));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", declineCookies);

            // Wait for the element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(declineCookies));

            declineCookies.click();
            extentTest.get().log(Status.INFO, "Declined cookie consent");

            // Proceed to login
            extentTest.get().log(Status.INFO, "Login account");
            logInPage.login(input.get("email"), input.get("password"));

            String loginMessage = logInPage.getLoginMessage();
            Assert.assertEquals("Account", loginMessage);

            if ("Account".equalsIgnoreCase(loginMessage)) {
                extentTest.get().log(Status.PASS, "Account logged in successfully!");
            } else {
                extentTest.get().log(Status.FAIL, "Account is not logged in");
            }
        } catch (ElementNotInteractableException e) {
            extentTest.get().log(Status.FAIL, "Element not interactable: " + e.getMessage());
        } finally {
            restartBrowser();
        }
    }

//    @Test(priority = 1, dataProvider = "getData")
//    @Description("Verify that the account is successfully login")
//    public void logInTest(HashMap<String, String> input) throws IOException {
//        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
//        WebElement declineCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyButtonDecline")));
//        declineCookies.click();
//        extentTest.get().log(Status.INFO, "Login account");
//        logInPage.login(input.get("email"), input.get("password"));
//        Assert.assertEquals("Account", logInPage.getLoginMessage());
//        if ("Account".equalsIgnoreCase(logInPage.getLoginMessage()))
//            extentTest.get().log(Status.PASS, "Account login successfully!");
//        else extentTest.get().log(Status.FAIL, "Account is not logged in");
//        restartBrowser();
//    }
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> logInDataList = loadLogInData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\LogIn.json");
        return new Object[][]{{logInDataList.get(0)}, {logInDataList.get(1)}};
    }
}




