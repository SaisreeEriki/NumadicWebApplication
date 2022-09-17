package abstractBase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AbstractBasePage {
	
	WebDriver driver;
	
	 public AbstractBasePage(WebDriver driver){
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
	
	 public static @FindBy (xpath = "//*[@class='animated fadeIn mb-md']")
		WebElement greetingText;
	 
	 public static @FindBy (id = "job_type")
	 	WebElement jobType;
	 
	 public static @FindBy (className = "dataTables_empty")
	 	WebElement message;
	 
	 /*
	  * Description: Xpath using contains text example
	  * 
	  * public static @FindBy (xpath = "//a[contains(text(), 'QA Engineer')]")
	 	WebElement jobPosition;
	  */
	 
	 public static @FindBy (xpath = "//*[@id='job-posts-table']/tbody//td[2]/a[contains(text(),'QA Engineer')]")
	 	WebElement jobPosition;
	 
	 public static @FindBy (xpath = "//*[@class='btn btn-lg btn-red']")
	 	WebElement ApplyHereNowButton;
	 
	 
	 public static @FindBy (xpath = "//*[@id='job-posts-table']/tbody//td[2]/a[contains(text(),'QA Engineer')]/../../td[5]/button")
	 	WebElement ApplyButton;
	 
	 
	 
	 
	 public void launchApplication(String url) {
			driver.manage().window().maximize();
			driver.get(url);
		 }
	 
	 public String getWebElementText(WebElement element) {
		String text = element.getText();
		return text;
	 }
	 
	 public void clickOnElement(WebElement element) {
		 element.click();
	 }
	 
	 public void setText(WebElement element, String text) {
		 element.sendKeys(text);
	 }
	 
	 public void selectText(WebElement element, String text) {
		 Select select = new Select(element);
		 select.selectByVisibleText(text);
	 }
	 
	 public boolean verifyElementPresent(WebElement element) {
		 boolean iseElementPresent = element.isDisplayed();
		 return iseElementPresent;
		 
	 }
	 
	 public String getCurrentPageUrl() {
		String currentUrl = driver.getCurrentUrl();
		 return currentUrl;
	 }
	 
	 public void hoveringOnElement(WebElement srcElement, WebElement targetElement) {
		Actions action = new Actions(driver);
		action.moveToElement(srcElement)
			.moveToElement(targetElement)
			.build()
			.perform();
	 }
	 
	 public void closeApp() {
		 driver.quit();
	 }
}
