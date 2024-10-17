package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.AbstractComponent;

public class ProductPage extends AbstractComponent {
    WebDriver driver;
    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "(//button[@class='c-add-to-cart button--block-mobile'])[1]")
    private WebElement addToCartBtn;
    private By addToCartSuccessMessage = By.xpath("//div[@class='added-to-cart-modal__content']/h5");
    private By searchIcon = By.xpath("//button[@aria-label='Show search']");
    private By searchInput = By.id("navigation-search");
    private By dragonFly = By.xpath("//h4[@class='search-suggestions__title' and text()='Dragonfly']");

    public void searchProduct() {
        driver.findElement(searchIcon).click();
        driver.findElement(searchInput).sendKeys("Dragonfly");
        driver.findElement(dragonFly).click();
    }

    public void addToCart(){
//        Actions action = new Actions(driver);
//        action.moveToElement(addToCartBtn).build().perform();
        addToCartBtn.click();
    }
}
