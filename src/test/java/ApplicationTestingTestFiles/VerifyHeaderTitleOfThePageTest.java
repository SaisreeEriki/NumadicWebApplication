package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ApplicationTestingProcessFiles.NumadicCarrersProcess;
import abstractBase.AbstractBasePage;
import abstractBase.TestDataFile;

public class VerifyHeaderTitleOfThePageTest {
	
	WebDriver driver;
	
	public String ApplicationURL = null;
	
	public static final String excelDataFileName = "LaunchBrowserTestData.xlsx";
	public static final String excelDataSheetName = "AppBasicData";
	
	public static final String testDataFileName = "TestData.xlsx";
	public static final String testDataSheetName = "TestData";
	
	public static final String columnByText = "Data";
	
	TestDataFile testDataFile = new TestDataFile(driver);
//	NumadicCarrersProcess numadicCareersProcess = new NumadicCarrersProcess(driver);
	
	@BeforeTest
	public void brwsrLaunch() throws Exception {
		String webDriver = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriver");
		String webDriverPath = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriverPath");
		ApplicationURL = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "Url");
		
		Reporter.log("Launch Chrome WebDriver");
		System.setProperty(webDriver, webDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("Navigate to Numadic Careers Page.");
		abstractBasePage.launchApplication(ApplicationURL);
	}
	
	
	@Test
	public void test() throws Exception {
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("Validate the Header Text of the Careers Page.");
		String actualText = abstractBasePage.getWebElementText(AbstractBasePage.greetingText);
		String expectedText = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "HeaderText");
		Assert.assertEquals(expectedText, actualText, "Web Application has displayed the header text \"JOIN OUR CREW \". ");
		
		
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		Reporter.log("<h2>Verify Header Text (JOIN OUR CREW)</h2>");
		Reporter.log("The Header Text is displayed as: " + actualText);
		String screenshot=abstractBasePage.captureScreenShot("HeaderText.png");
		Reporter.log("<img src=\"" + screenshot+"\"/>");
		
		Reporter.log("Close the browser. ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
