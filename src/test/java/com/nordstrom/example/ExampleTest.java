package com.nordstrom.example;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.manager.SeleniumManager;
import org.openqa.selenium.manager.SeleniumManagerOutput.Result;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import com.nordstrom.automation.selenium.annotations.InitialPage;
import com.nordstrom.automation.selenium.annotations.NoDriver;
import com.nordstrom.automation.selenium.model.Page;
import com.nordstrom.automation.selenium.support.TestNgBase;
import com.nordstrom.example.components.DealsMenuComponent;
import com.nordstrom.example.pages.HomePage;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

@InitialPage(HomePage.class)
public class ExampleTest extends TestNgBase {
	
	@Test
	public void testDealsMenu() {
		HomePage homePage = getInitialPage();
		DealsMenuComponent dealsMenu = homePage.fromMainMenu().openDealsMenu();
		assertTrue(dealsMenu.isDisplayed());
		Page foo = dealsMenu.fromPackage("Popular").openTravelOffer("Adult Escapes");
		String bar = foo.getTitle();
		boolean stop = true;
	}
	
	@Test
	@NoDriver
	public void testProxy() {
		BrowserMobProxyServer proxy = null;
		WebDriver driver = null;

        try {
	       // Start BrowserMob Proxy
	        proxy = new BrowserMobProxyServer();
	        proxy.start(8080); // Starts on port 8080

	        // Block specific domains
	        proxy.blacklistRequests(".*everesttech\\.net.*", 200);
	        proxy.blacklistRequests(".*adobedc\\.net.*", 200);

	        // Convert to Selenium Proxy
	        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

	        // Set up Firefox with Proxy
	        FirefoxOptions options = new FirefoxOptions();
	        options.setProxy(seleniumProxy);

	        // Start WebDriver
	        driver = new FirefoxDriver(options);

	        // Open test website
	        driver.get("https://www.costcotravel.com");

	        // Wait for a few seconds to observe
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

//	        driver.get("https://www.costcotravel.com/Travel-Offers/Adults-Only");

	        HomePage homePage = new HomePage(driver);
			DealsMenuComponent dealsMenu = homePage.fromMainMenu().openDealsMenu();
			assertTrue(dealsMenu.isDisplayed());
			Page foo = dealsMenu.fromPackage("Popular").openTravelOffer("Adult Escapes");
			String bar = foo.getTitle();
			boolean stop = true;

	        // Wait for a few seconds to observe
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
        } finally {
	        // Stop proxy and close browser
	        if (driver != null) driver.quit();
	        if (proxy != null) proxy.stop();
        }
	        
	}

	@Test
	@NoDriver
	public void testBasic() {
		WebDriver driver = null;

        try {
            SeleniumManager manager = SeleniumManager.getInstance();
            Result result = manager.getBinaryPaths(new ArrayList<String>(Arrays.asList("--browser", "chrome")));
            
	        ChromeOptions options = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", result.getDriverPath());
	        options.setBinary(result.getBrowserPath());
	        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            driver = new ChromeDriver(options);

	        // Open test website
	        driver.get("https://www.costcotravel.com");

	        // Wait for a few seconds to observe
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

//	        driver.get("https://www.costcotravel.com/Travel-Offers/Adults-Only");
	        
	        HomePage homePage = new HomePage(driver);
			DealsMenuComponent dealsMenu = homePage.fromMainMenu().openDealsMenu();
			assertTrue(dealsMenu.isDisplayed());
			Page foo = dealsMenu.fromPackage("Popular").openTravelOffer("Adult Escapes");
			String bar = foo.getTitle();
			boolean stop = true;

	        // Wait for a few seconds to observe
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
        } finally {
	        if (driver != null) driver.quit();
        }
	        
	}

}
