import com.aventstack.extentreports.Status;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import testComponents.BaseTest;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import static testComponents.Listeners.extentTest;
import static utils.HelpersMethods.generateRandomEmail;

@Listeners(testComponents.Listeners.class)
public class CreateAccountTest extends BaseTest {
    WebDriverWait wait;

    @Test(priority = 1, dataProvider = "getData")
    @Description("Verify that the account is successfully made")
    public void createAccountTest(HashMap<String, String> input) throws IOException {
        String email = generateRandomEmail();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allowCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
        allowCookies.click();
        extentTest.get().log(Status.INFO, "Creating account");
        Severity severityLevel = Severity.CRITICAL;
        extentTest.get().assignCategory(severityLevel.name());
        createAccountPage.createAccount(input.get("userName"),
                input.get("userLastName"),
                input.get("userPhoneNumber"),
                email, email,
                input.get("userPassword"), input.get("userConfirmPassword"));
        Assert.assertEquals("Account", createAccountPage.getSuccessfulMessage());
        if ("Account".equalsIgnoreCase(createAccountPage.getSuccessfulMessage()))
            extentTest.get().log(Status.PASS, "Account created successfully!");
        else extentTest.get().log(Status.FAIL, "Account was not created!");
        restartBrowser(); // Restart the browser on failure
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> accountDataList = loadCreateAccountData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\CreateAccount.json");
        return new Object[][]{{accountDataList.get(0)}, {accountDataList.get(1)}};
    }
}

