package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.AbstractComponent;

import static configs.ConfigReader.getBaseURL;


public class LogInPage extends AbstractComponent {

    WebDriver driver;

    public LogInPage(WebDriver driver)
    {
        super(driver);
        //initialization
        this.driver=driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath="//input[@id='loginEmail']")
    private WebElement userEmail;
    @FindBy(xpath="//input[@id='loginPassword']")
    private WebElement userPassword;
    @FindBy(xpath="//label[@for='loginRememberMe']")
    private WebElement rememberMeBtn;
    @FindBy(xpath="//button[@type='submit' and @data-cy-button='signinSubmit']")
    private WebElement logInBtn;
    @FindBy(className="account-header__title")
    private WebElement loginMessage;
    @FindBy(className = "c-alert")
    private WebElement errorMessage;

    public void login(String email, String password){
       userEmail.sendKeys(email);
       userPassword.sendKeys(password);
       rememberMeBtn.click();
       logInBtn.click();
}
public String getLoginMessage(){
        waitForElementVisible(loginMessage);
        return loginMessage.getText();
}
public String getErrorMessage(){
        waitForElementVisible(errorMessage);
        return errorMessage.getText();
}

    public void goTo()
    {
        driver.get(getBaseURL());
    }
















}
