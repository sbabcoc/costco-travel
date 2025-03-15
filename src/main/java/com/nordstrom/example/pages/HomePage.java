package com.nordstrom.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.nordstrom.automation.selenium.annotations.PageUrl;
import com.nordstrom.automation.selenium.model.Page;
import com.nordstrom.example.components.MainMenuComponent;

@PageUrl("/")
public class HomePage extends Page {
	
	enum Using implements ByEnum {
		MAIN_MENU(By.cssSelector("nav#main_navigation"));
		
		private final By locator;
		
		Using(By locator) {
			this.locator = locator;
		}

		@Override
		public By locator() {
			return locator;
		}
	}

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	private MainMenuComponent mainMenu;
	
    public MainMenuComponent fromMainMenu() {
        if (mainMenu == null) {
        	mainMenu = new MainMenuComponent(Using.MAIN_MENU.locator, this);
        }
        return mainMenu;
    }


}
