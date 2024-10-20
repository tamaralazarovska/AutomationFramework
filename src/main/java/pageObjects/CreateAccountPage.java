package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.AbstractComponent;
import utils.HelpersMethods;

public class CreateAccountPage extends AbstractComponent {

    WebDriver driver;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="dwfrm_profile_customer_firstname")
    private WebElement name;
    @FindBy(id="dwfrm_profile_customer_lastname")
    private WebElement lastName;
    @FindBy(id="dwfrm_profile_customer_phone")
    private WebElement phoneNumber;
    @FindBy(id="dwfrm_profile_customer_email")
    private WebElement email;
    @FindBy(id="dwfrm_profile_customer_emailconfirm")
    private WebElement confirmEmail;
    @FindBy(id="dwfrm_profile_login_password")
    private WebElement password;
    @FindBy(id="dwfrm_profile_login_passwordconfirm")
    private WebElement confirmPassword;
    @FindBy(xpath="//label[@class='checkbox__label' and @for='privacyTerms']")
    private WebElement checkbox;
    @FindBy(className="register-form__submit")
    private WebElement createButton;
    @FindBy(className="account-header__title")
    private WebElement createdAccountMessage;
    @FindBy(id="err_dwfrm_profile_customer_emailconfirm")
    private WebElement errorMessage;

    public void createAccount(String userName, String userLastName, String userPhoneNumber, String userEmail,
                              String userConfirmEmail, String userPassword, String userConfirmPassword){

        name.sendKeys(userName);
        lastName.sendKeys(userLastName);
        phoneNumber.sendKeys(userPhoneNumber);
        email.sendKeys(userEmail);
        confirmEmail.sendKeys(userConfirmEmail);
        password.sendKeys(userPassword);
        confirmPassword.sendKeys(userConfirmPassword);
        checkbox.click();
        createButton.click();
    }
    public String getSuccessfulMessage(){
        waitForElementVisible(createdAccountMessage);
        return createdAccountMessage.getText();
    }
    public String getErrorMessage(){
        waitForElementVisible(errorMessage);
        return errorMessage.getText();
    }
}

