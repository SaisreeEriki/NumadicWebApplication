package ApplicationTestingProcessFiles;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import abstractBase.AbstractBasePage;

public class NumadicCarrersProcess {
	
	WebDriver driver;
	
	public NumadicCarrersProcess(WebDriver driver) {
		this.driver = driver;
	}
	
	AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
	
	public void captureScreenShot(String filePath) throws IOException {
		TakesScreenshot screendhotShot =((TakesScreenshot)driver);
		File SrcFile=screendhotShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File("D:\\New Project\\NumadicAssessment\\Screenshots" + filePath);
		FileUtils.copyFile(SrcFile, DestFile);

		
	}

}
