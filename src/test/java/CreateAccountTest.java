import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.CreateAccountPage;
import testComponents.BaseTest;
import utils.AbstractComponent;
import utils.HelpersMethods;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static utils.HelpersMethods.generateRandomEmail;

@Listeners(testComponents.Listeners.class)
public class CreateAccountTest extends BaseTest {

    @BeforeMethod
    public void setUp() throws IOException {
        WebDriver driver = initializeDriver();
        // Initialize the HomePage object after the browser is set up
        createAccountPage = new CreateAccountPage(driver);
        AbstractComponent ac = new AbstractComponent(driver);
        ac.declineCookies();

    }

    @Parameters({"URL"})
    @Test(priority = 1, dataProvider = "getData")
    @Description("Verify that the account is successfully made")
    public void createAccountTest(HashMap<String, String> input, String urlname) throws IOException {
        String email = generateRandomEmail();
        try {
            createAccountPage.createAccount(input.get("name"), input.get("lastName"),
                    input.get("phoneNumber"), email,
                    email, input.get("password"),
                    input.get("confirmPassword"));
            Assert.assertEquals("Account", createAccountPage.getSuccessfulMessage());
        } catch (Exception e) {
            System.out.println("Account creation failed, restarting the browser...");
            restartBrowser(); // Restart the browser on failure

            // Attempt account creation again or perform any necessary steps
            createAccountPage.createAccount(input.get("name"), input.get("lastName"),
                    input.get("phoneNumber"), email,
                    email, input.get("password"),
                    input.get("confirmPassword"));
            Assert.assertEquals("Account", createAccountPage.getSuccessfulMessage());
        }
        System.out.println(urlname);
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> accountDataList = loadCreateAccountData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\CreateAccount.json");
        return new Object[][]{{accountDataList.get(0)}, {accountDataList.get(1)}};
    }
}




//import org.testng.Assert;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import testComponents.BaseTest;
//import utils.HelpersMethods;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//public class CreateAccountTest extends BaseTest {
//    String newEmail = HelpersMethods.generateRandomEmail();
////    @Test
////    public void createAccountTest(){
////        createAccountPage.createAccount("Tamara","Lazarovska","+38976554321",newEmail,newEmail,"Password123!","Password123!");
////        Assert.assertEquals("Account", createAccountPage.getSuccessfulMessage());
////    }
//@Test(dataProvider = "getData")
//public void createAccountTest(HashMap<String, String> input) throws IOException {
//    createAccountPage.createAccount(input.get("name"), input.get("lastName"),input.get("phoneNumber"),
//            input.get("email"),input.get("confirmEmail"),input.get("password"),input.get("confirmPassword"));
//    Assert.assertEquals("Account", createAccountPage.getSuccessfulMessage());
//}
//
//    @DataProvider
//    public Object[][] getData() throws IOException {
//        List<HashMap<String, String>> accountDataList = loadCreateAccountData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\CreateAccount.json");
//        return new Object[][]{{accountDataList.get(0)}, {accountDataList.get(1)}};
//    }
//}

