package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.LogInPage;
import pageObjects.WishlistPage;
import testComponents.BaseTest;

import java.io.IOException;

public class ImplStepDefinitions extends BaseTest {
    public LogInPage logInPage;
    public WishlistPage wishlistPage;

    @Given("I landed on Bugaboo page")
    public void I_landed_on_Bugaboo_page() throws IOException {
        launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username, String password) {
        logInPage.login(username, password);
    }

    @When("^I add product (.+) to cart$")
    public void I_add_product_to_cart(String productName) {
        wishlistPage.searchProduct();
        wishlistPage.addToWishlist();
    }
    @Then("{string} message is displayed")
    public void message_displayed(String string){

    }
}
