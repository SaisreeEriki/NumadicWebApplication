package abstractBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Reporter;
import org.openqa.selenium.TakesScreenshot;
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

        //This initElements method will create all WebElements

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
	 
	 public static @FindBy (id = "firstName")
	 	WebElement firstName;
	 
	 public static @FindBy (id = "lastName")
	 	WebElement lastName;
	 
	 public static @FindBy (id = "email")
	 	WebElement emailId;
	 
	 public static @FindBy (id = "phone")
	 	WebElement contactNumber;
	 
	 public static @FindBy (id = "sports")
	 	WebElement sports;
	 
	 public static @FindBy (id = "pets")
	 	WebElement pets;
	 
	 public static @FindBy (id = "current_city")
	 	WebElement city;
	 
	 public static @FindBy (id = "hometown")
	 	WebElement homeTown;
	 
	 public static @FindBy (id = "dob")
	 	WebElement DOB;
	 
	 public static @FindBy (id = "careers_form_btn_step1")
	 	WebElement nextButton;
	 
	 public static @FindBy (xpath = "//ul/li[contains(text(),'Enter your first name')]")
	 	WebElement firstNameError;
	 
	 public static @FindBy (xpath = "//ul/li[contains(text(),'Enter your last name')]")
	 	WebElement lastNameError;
	 
	 public static @FindBy (xpath = "//ul/li[contains(text(),'Enter an email')]")
	 	WebElement emailIdError;
	 
	 public static @FindBy (xpath = "//ul/li[contains(text(),'Enter your mobile number')]")
	 	WebElement contactNumberError;
	 
	 public static @FindBy (id = "//label[@id='email_label']//../ul[@class='parsley-errors-list']")
	 	WebElement validEntryEmail;
	 
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
	 
	 public void hoveringOnElement(WebElement srcElement, WebElement targetElement) throws IOException {
		Actions action = new Actions(driver);
		logMessage("Hover on the Element. ");
		action.moveToElement(srcElement);
		logMessage("Move to the Apply Button");
		captureScreenShot("ApplyButton.png");
		action.moveToElement(targetElement)
			.build()
			.perform();
	 }

	public String captureScreenShot(String filePath) throws IOException {
		String srcPath = "D:\\New Project\\NumadicAssessment\\Screenshots\\";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(src,new File(srcPath + timestamp() + " " + filePath));
			} catch (IOException e)

			{
				System.out.println(e.getMessage());
			}
		String fileName = srcPath + timestamp() + " " + filePath;
		return fileName;
			
			
		}

		public static String timestamp() {
			return new SimpleDateFormat("yyyy-Mm-dd HH-mm-ss").format(new Date());
		}
		
		public void logMessage(String logMessage) {
			Reporter.log(logMessage);
		}
		
		public String getAttributeValues(WebElement element, String attribute) {
			String value = element.getAttribute(attribute);
			return value;
		}
		
		public boolean verifyElementEnable(WebElement element) {
			 boolean iseElementEnable = element.isEnabled();
			 return iseElementEnable;
		 }
		 
		 public boolean verifyElementSelected(WebElement element) {
			 boolean iseElementselected = element.isSelected();
			 return iseElementselected;
		 }

		 public boolean isEmpty(WebElement element) {
			 boolean isempty = element.getText().equalsIgnoreCase("");
			 return isempty;
		 }
		 
		 public String validateBlankTextErrorMessage(WebElement element, WebElement Message) {
			 String Msg = "";
			 if(isEmpty(element)) {
				 verifyElementPresent(Message);
				 Msg = Message.getText();
			 }
			 return Msg;
		 }
		
	 public void closeApp() {
		 driver.quit();
	 }
}
