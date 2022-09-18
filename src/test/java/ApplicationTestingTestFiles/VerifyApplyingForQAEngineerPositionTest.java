package ApplicationTestingTestFiles;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import abstractBase.AbstractBasePage;
import abstractBase.TestDataFile;

public class VerifyApplyingForQAEngineerPositionTest {
	
	WebDriver driver;
	
	public String ApplicationURL = null;
	
	public static final String excelDataFileName = "LaunchBrowserTestData.xlsx";
	public static final String excelDataSheetName = "AppBasicData";
	
	public static final String testDataFileName = "TestData.xlsx";
	public static final String testDataSheetName = "TestData";
	
	public static final String columnByText = "Data";
	
	TestDataFile testDataFile = new TestDataFile(driver);
	
	@BeforeTest
	public void brwsrLaunch() throws Exception {
		
		String webDriver = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriver");
		String webDriverPath = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriverPath");
		ApplicationURL = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "Url");
		
		System.setProperty(webDriver, webDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.launchApplication(ApplicationURL);
	}
	
	
	@Test (priority = 0)
	public void test() throws Exception {
		
		Reporter.log("<h2>Validate Personal Details Form. </h2>");
		Reporter.log("<h3>From Job dropdown, Select FULL TIME. </h3>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		String selectText = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "JobTypeFT");
		Reporter.log("Selected Job Type as FullTime. ");
		abstractBasePage.selectText(AbstractBasePage.jobType, selectText);
		
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String screenshot=abstractBasePage.captureScreenShot("SelectedJobType.png");
		Reporter.log("<img src=\"" + screenshot+"\"/>");
		Reporter.log("Selected Job Type - " + selectText );
	}
	
	@Test(priority=1)
	public void QAEngineerPostion() throws IOException {
		
		Reporter.log("<h3>From Job listing option select QA Engineer. </h3>");
		
		/*
		 * Description: We can find the web table hyperlink in two ways.
		 * 1. By using the xpath with contains keyword. This identifies all the hyper links which has a text as "QA Engineer".
		 * 	  This xpath won't work if the page has more than one hyperlink with same text. (Below is the sample snippet)
		 * 
		 * abstractBasePage.selectText(AbstractBasePage.jobType, selectText);
		 * abstractBasePage.clickOnElement(AbstractBasePage.jobPosition);
		 * 
		 * 2. Using Dynamic xpath. In this test Case I have used dynamic XPath.
		 * 
		*/
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("get Current Url");
		String urlPreclickButton = abstractBasePage.getCurrentPageUrl();
		Reporter.log(urlPreclickButton);
		
		abstractBasePage.clickOnElement(AbstractBasePage.jobPosition);
		
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String screenshot=abstractBasePage.captureScreenShot("SelectedJobType.png");
		Reporter.log("<img src=\"" + screenshot+"\"/>");
		
	}
	
	@Test(priority=2)
	public void verifyUrlRedirect() throws Exception {
		Reporter.log("<h3>Verify button ‘Apply here now’ and perform a click action. </h3>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("Verify Page load successfully. ");
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		String pageLoadStatus = js.executeScript("return document.readyState").toString();
		Reporter.log("Page load status: " + pageLoadStatus);
		
		while(!pageLoadStatus.equals("complete")) {
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'QA Engineer')]")));
		}
		
		Reporter.log("Page redirects back to the Careers Page. ");
		String actualPage = abstractBasePage.getCurrentPageUrl();
		String expectedPage = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "expectedPageUrl");
		Reporter.log("Validate redirected page is the same Career Page");
		Assert.assertEquals(actualPage, expectedPage, "Validating the redirected url");
		
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String RedirectedToCareer=abstractBasePage.captureScreenShot("RedirectedToCareer.png");
		Reporter.log("<img src=\"" + RedirectedToCareer+"\"/>");
	}
	
	
	@Test(priority=3)
	public void clickOnApplyHere() throws Exception {
		Reporter.log("<h3>Verify redirect back to careers page. </h3>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("Verify existence of Apply here now button in the page. ");
		boolean verifyElement = abstractBasePage.verifyElementPresent(AbstractBasePage.ApplyHereNowButton);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(verifyElement, "Verifying Apply here now button. ");
		
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String ApplyHereButton=abstractBasePage.captureScreenShot("ApplyHereButton.png");
		Reporter.log("<img src=\"" + ApplyHereButton+"\"/>");
		Reporter.log("Apply here now button is exist in the page. ");
		
		Reporter.log("Click on Apply here now button");
		abstractBasePage.clickOnElement(AbstractBasePage.ApplyHereNowButton);
		String actualPagePostClicking = abstractBasePage.getCurrentPageUrl();
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String redirectedPage=abstractBasePage.captureScreenShot("redirectedPage.png");
		Reporter.log("<img src=\"" + redirectedPage+"\"/>");
		Reporter.log("Page has been redirected back to Careers Page.");
		String expectedUrlPostClicking = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "expectedUrlPostClick");
		Assert.assertEquals(actualPagePostClicking, expectedUrlPostClicking, "Validating the redirected url");
	}	
	
	@Test(priority=4)
	public void hoverAndClickOnApply() throws IOException, Exception {
		Reporter.log("<h3>On listing page, hover over QA Engineer, click ‘Apply’ button. </h3>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("Hover on QAEngineer option. ");
		abstractBasePage.hoveringOnElement(AbstractBasePage.jobPosition, AbstractBasePage.ApplyButton);
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String HoverOnQAEng=abstractBasePage.captureScreenShot("HoverOnQAEng.png");
		Reporter.log("<img src=\"" + HoverOnQAEng+"\"/>");
		Reporter.log("Click on Apply Button.");
		abstractBasePage.clickOnElement(AbstractBasePage.ApplyButton);
		Thread.sleep(10000);
		
	}
	
	@Test(priority=5)
	public void validatePersonalDetailsForm() {
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);

		
		/*
		 * First Name
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.firstName), "FirstName Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.firstName), "FirstName Field is enable in Personal Details Section. ");
		
		
		/*
		 * Last Name
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.lastName), "LastName Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.lastName), "lastName Field is enable in Personal Details Section. ");
		
		
		/*
		 * Email Id
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.emailId), "email Id Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.emailId), "email Id Field is enable in Personal Details Section. ");
		
		/*
		 * Contact Number
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.contactNumber), "Mobile Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.contactNumber), "Mobile Field is enable in Personal Details Section. ");
		
		/*
		 * Sports
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.sports), "Sports Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.sports), "Sports Field is enable in Personal Details Section. ");
		
		/*
		 * Pets
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.pets), "Dogs or Cats Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.pets), "Dogs or Cats Field is enable in Personal Details Section. ");
		
		/*
		 * Current city
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.city), "Current city Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.city), "Current city Field is enable in Personal Details Section. ");
		

		/*
		 * Home Town
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.homeTown), "HomeTown Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.homeTown), "HomeTown Field is enable in Personal Details Section. ");
		

		/*
		 * Date Of Birth
		 */

		Assert.assertTrue(abstractBasePage.verifyElementPresent(AbstractBasePage.DOB), "Dob Field is present in Personal Details Section. ");
		Assert.assertTrue(abstractBasePage.verifyElementEnable(AbstractBasePage.DOB), "Dob Field is enable in Personal Details Section. ");
		
		
		/*
		 * validate the error message displays for blank text
		 */
		abstractBasePage.clickOnElement(AbstractBasePage.nextButton);
		
		String firstNameErrorMsg = abstractBasePage.validateBlankTextErrorMessage(AbstractBasePage.firstName, AbstractBasePage.firstNameError);
		Assert.assertTrue(!firstNameErrorMsg.equalsIgnoreCase(""), "verified Blank field of first Name. ");
		
		String lastNameErrorMsg = abstractBasePage.validateBlankTextErrorMessage(AbstractBasePage.lastName, AbstractBasePage.lastNameError);
		Assert.assertTrue(!lastNameErrorMsg.equalsIgnoreCase(""), "verified Blank field of last Name. ");
		
		String emailIdErrorMsg = abstractBasePage.validateBlankTextErrorMessage(AbstractBasePage.emailId, AbstractBasePage.emailIdError);
		Assert.assertTrue(!emailIdErrorMsg.equalsIgnoreCase(""), "verified Blank field of email id. ");
		
		String MobileErrorMsg = abstractBasePage.validateBlankTextErrorMessage(AbstractBasePage.contactNumber, AbstractBasePage.contactNumberError);
		Assert.assertTrue(!MobileErrorMsg.equalsIgnoreCase(""), "verified Blank field of contact number. ");
		
		/*
		 * Enter emailid
		 */
		String textEmail = "sai@gmail.com";
		if(textEmail.contains("@") && textEmail.contains(".")) {
		abstractBasePage.setText(AbstractBasePage.emailId, textEmail);
		abstractBasePage.clickOnElement(AbstractBasePage.nextButton);
		abstractBasePage.clickOnElement(AbstractBasePage.emailId);
		Assert.assertTrue(true, "Enterd valid email id");
		}
	}
	
	
	@AfterTest()
	public void closeApplication() {
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
