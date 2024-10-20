package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage {
    WebDriver driver;

    public WishlistPage(WebDriver driver){
        this.driver=driver;
       PageFactory.initElements(driver, this);
}
    @FindBy(xpath="//button[@aria-label='Show search']")
    private WebElement searchIcon;
    @FindBy(id="navigation-search")
    private WebElement searchInput;
    @FindBy(xpath="//h4[@class='search-suggestions__title' and text()='Dragonfly']")
    private WebElement dragonFly;

     public void searchProduct(){
         searchIcon.click();
         searchInput.sendKeys("Dragonfly");
         dragonFly.click();

     }
     public void addToWishlist(){
         WebElement svgElement = driver.findElement(By.xpath("//button[contains(@class, 'c-add-to-wishlist__button') and .//*[name()='svg' and contains(@id, 'icon')]]"));
         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", svgElement);
     }
}

