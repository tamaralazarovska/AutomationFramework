import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import resources.ExtentReportersNG;
import testComponents.BaseTest;
import testComponents.Retry;
import java.io.IOException;

@Listeners(testComponents.Listeners.class)
public class ErrorValidations extends BaseTest {
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setup() {
        extent = ExtentReportersNG.getReportObject();
    }

    @Test(groups = {"ErrorHandling"}, testName = "Negative scenario for log in", retryAnalyzer = Retry.class)
    public void errorLogInTest() throws IOException {
        test = extent.createTest("errorLogInTest");
        logInPage.login("tamara.lazarovska12@valtech.com", "Password");

        String errorMessage = logInPage.getErrorMessage();
        Assert.assertEquals("Invalid login or password—please try again. Please note the password is case sensitive.", errorMessage);

        // Log the result
        test.log(Status.INFO, "Login attempted with invalid credentials");
        if (errorMessage.equals("Invalid login or password—please try again. Please note the password is case sensitive.")) {
            test.log(Status.FAIL, "Error message displayed as expected");
        }
    }

    @Test(groups = {"ErrorHandling"}, testName = "Negative scenario for creating account", retryAnalyzer = Retry.class)
    public void errorCreateAccountTest() {
        test = extent.createTest("errorCreateAccountTest");
        createAccountPage.createAccount("Tamara", "Lazarovska", "+38976554321", "tamaral123", "tamaral123", "Password123!", "Password123!");

        String errorMessage = createAccountPage.getErrorMessage();
        Assert.assertEquals("Please enter a valid email address", errorMessage);

        // Log the result
        test.log(Status.INFO, "Account creation attempted with invalid email");
        if (errorMessage.equals("Please enter a valid email address")) {
            test.log(Status.PASS, "Error message displayed as expected");
        } else {
            test.log(Status.FAIL, "Error message not displayed as expected");
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush(); // Write the results to the report
    }
}


