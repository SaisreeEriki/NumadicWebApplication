package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
		
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		String selectText = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "JobTypeFT");
		abstractBasePage.selectText(AbstractBasePage.jobType, selectText);
		
		
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
		String urlPreclickButton = abstractBasePage.getCurrentPageUrl();
		
		abstractBasePage.clickOnElement(AbstractBasePage.jobPosition);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		String pageLoadStatus = js.executeScript("return document.readyState").toString();
		
		while(!pageLoadStatus.equals("complete")) {
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'QA Engineer')]")));
		}
		
		
		
		String actualPage = abstractBasePage.getCurrentPageUrl();
		String expectedPage = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "expectedPageUrl");
		Assert.assertEquals(actualPage, expectedPage, "Validating the redirected url");
		
		boolean verifyElement = abstractBasePage.verifyElementPresent(AbstractBasePage.ApplyHereNowButton);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(verifyElement, "Verifying Apply here now button. ");
		abstractBasePage.clickOnElement(AbstractBasePage.ApplyHereNowButton);
		
		String actualPagePostClicking = abstractBasePage.getCurrentPageUrl();
		String expectedUrlPostClicking = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "expectedUrlPostClick");
		Assert.assertEquals(actualPagePostClicking, expectedUrlPostClicking, "Validating the redirected url");
		
		abstractBasePage.hoveringOnElement(AbstractBasePage.jobPosition, AbstractBasePage.ApplyButton);
		abstractBasePage.clickOnElement(AbstractBasePage.ApplyButton);
		
		
	}
	
	
	@AfterTest()
	public void closeApplication() {
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
