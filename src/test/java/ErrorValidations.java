import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ExtentReportersNG;
import testComponents.BaseTest;
import testComponents.Retry;
import java.io.IOException;
import java.time.Duration;

import static testComponents.Listeners.extentTest;

@Listeners(testComponents.Listeners.class)
public class ErrorValidations extends BaseTest {
    private ExtentReports extent;
    WebDriverWait wait;

    @Test(priority = 1, groups = {"ErrorHandling"}, testName = "Negative scenario for log in", retryAnalyzer = Retry.class)
    public void errorLogInTest() throws IOException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allowCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
        allowCookies.click();

        extentTest.get().log(Status.INFO, "Login account");
        logInPage.login("tamara.lazarovska12@valtech.com", "Password");

        String errorMessage = logInPage.getErrorMessage();
        int position = errorMessage.length();
        System.out.println(position);
        String errorMessage1 = errorMessage.substring(0,position-2);
        System.out.println(errorMessage1);
        Assert.assertEquals("Invalid login or password—please try again. Please note the password is case sensitive.", errorMessage1);

        // Log the result
        extentTest.get().log(Status.INFO, "Account login attempted with invalid password");
        if (errorMessage1.equalsIgnoreCase("Invalid login or password—please try again. Please note the password is case sensitive.")) {
            extentTest.get().log(Status.PASS, "Error message displayed as expected");
        } else {
            extentTest.get().log(Status.FAIL, "Error message not displayed!");
        }
        browserUtility.closeBrowser();
    }

    @Test(priority= 2,  groups = {"ErrorHandling"}, testName = "Negative scenario for creating account", retryAnalyzer = Retry.class)
    public void errorCreateAccountTest() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement declineCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
        declineCookies.click();
        extentTest.get().log(Status.INFO, "Creating account");

        createAccountPage.createAccount("Tamara", "Lazarovska", "+38976554321", "tamaral123", "tamaral123", "Password123!", "Password123!");

        String errorMessage = createAccountPage.getErrorMessage();
        Assert.assertEquals("Please enter a valid email address", errorMessage);

        extentTest.get().log(Status.INFO, "Account creation attempted with invalid email");
        if (errorMessage.equals("Please enter a valid email address")) {
            extentTest.get().log(Status.PASS, "Error message displayed as expected");
        } else {
            extentTest.get().log(Status.FAIL, "Error message not displayed as expected");
        }
    }
}


