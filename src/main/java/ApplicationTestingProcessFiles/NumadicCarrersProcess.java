package ApplicationTestingProcessFiles;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	

}
