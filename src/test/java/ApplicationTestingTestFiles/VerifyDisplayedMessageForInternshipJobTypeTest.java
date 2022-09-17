package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import abstractBase.AbstractBasePage;
import abstractBase.TestDataFile;

public class VerifyDisplayedMessageForInternshipJobTypeTest {
	
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
		String selectText = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "JobType");
		abstractBasePage.selectText(AbstractBasePage.jobType, selectText);
		String actualText = abstractBasePage.getWebElementText(AbstractBasePage.message);
		String expectedText = testDataFile.readData(testDataFileName, testDataSheetName, columnByText, "MessageDisplayed");
		Assert.assertEquals(expectedText, actualText, "Application displayed the Message for internship job type: \"There are no available "
				+ "job positions that match your filters\". ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
