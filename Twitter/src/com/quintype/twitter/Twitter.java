package com.quintype.twitter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
  
 public class Twitter {
  public WebDriver driver;
  public String following;
  public String signoutlogo;
  public String logout;

   @BeforeTest
	 public void setUp() throws IOException
	 {
  		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/libs/chromedriver");
	        driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().window().maximize();
	 }
	@Test(priority=1)
	public void followProfile() throws IOException, InterruptedException
	{
			InputStream file = Twitter.class.getClassLoader().getResourceAsStream("com/quintype/twitter/twitterobjects.properties");
			Properties properties=new Properties();
			properties.load(file);
			String url = properties.getProperty("url");
			String signIn = properties.getProperty("signin");
			String usernamelocation = properties.getProperty("usernamexpath");
			String passwordlocation = properties.getProperty("passwordxpath");
			String username = properties.getProperty("usernamedata");
			String password = properties.getProperty("passworddata");
			String login = properties.getProperty("login");
		        String searchbar = properties.getProperty("searchbar");
		        String searchData = properties.getProperty("data");
		        String potus = properties.getProperty("potusHref");
		        String follow = properties.getProperty("follow");
		        following = properties.getProperty("following");
		        signoutlogo = properties.getProperty("signoutlogo");
		        logout = properties.getProperty("logout");
		    
		        driver.get(url);
			driver.findElement(By.partialLinkText(signIn)).click();
			driver.findElement(By.xpath(usernamelocation)).sendKeys(username);
			driver.findElement(By.xpath(passwordlocation)).sendKeys(password);
			driver.findElement(By.xpath(login)).click();
			Thread.sleep(3000);
			driver.findElement(By.id(searchbar)).sendKeys(searchData);
			driver.findElement(By.xpath(potus)).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(follow)).click();
			
	}
	//validating following profile or not
	@Test(priority=2)
	public void validate() throws InterruptedException
	{
		Thread.sleep(3000);
		String actual=driver.findElement(By.xpath(following)).getText();
		String exp="Following";
		Assert.assertEquals(actual, exp);
	}
	// tear down
	@Test(priority=3)
	public void unfollowAndLogout() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//*[text()='Following'])[3]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(signoutlogo)).click();
		driver.findElement(By.xpath(logout)).click();
	}
	@AfterTest
	public void close()
	{
		driver.close();
		
	}
}
