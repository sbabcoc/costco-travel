package com.nordstrom.example.components;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import com.nordstrom.automation.selenium.model.ComponentContainer;
import com.nordstrom.automation.selenium.model.PageComponent;

public class MainMenuComponent extends PageComponent {
	
	enum Using implements ByEnum {
		DEALS_MENU(By.cssSelector("li[data-hook=nav-deals]")),
		DEALS_DROPDOWN(By.cssSelector("div[aria-labelledby=nav_deals]"));
		
		private final By locator;
		
		Using(By locator) {
			this.locator = locator;
		}

		@Override
		public By locator() {
			return locator;
		}
	}

	public MainMenuComponent(By locator, ComponentContainer parent) {
		super(locator, parent);
	}
	
	public DealsMenuComponent openDealsMenu() {
		Actions actions = new Actions(driver);
		actions.moveToElement(findElement(Using.DEALS_MENU)).perform();
		DealsMenuComponent dealsMenu = new DealsMenuComponent(Using.DEALS_DROPDOWN.locator, this);
		dealsMenu.getWait().until(PageComponent.componentIsVisible());
		actions.release();
		return dealsMenu;
	}
}
