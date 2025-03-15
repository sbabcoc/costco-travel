package com.nordstrom.example.components;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.nordstrom.automation.selenium.model.ComponentContainer;
import com.nordstrom.automation.selenium.model.Page;
import com.nordstrom.automation.selenium.model.PageComponent;
import com.nordstrom.automation.selenium.model.RobustWebElement;
import com.nordstrom.automation.selenium.support.Coordinators;

public class DealsMenuColumnComponent extends PageComponent {

	enum Using implements ByEnum {
		COLUMN_HEADING(By.cssSelector("h2.packages-headline")),
		COLUMN_ENTRY(By.cssSelector("span.nav-menu-group a"));
		
		private final By locator;
		
		Using(By locator) {
			this.locator = locator;
		}

		@Override
		public By locator() {
			return locator;
		}
	}
	
    public DealsMenuColumnComponent(RobustWebElement element, ComponentContainer parent) {
		super(element, parent);
		columnName = (String) getKey(this);
		columnEntries = findElements(Using.COLUMN_ENTRY).stream()
				.collect(Collectors.toMap(
						entry -> entry.getText(),
						entry -> entry));
	}
    
    private final String columnName;
    private final Map<String, WebElement> columnEntries;
    
    public String getColumnName() {
    	return columnName;
    }
    
    public Set<String> getTravelOffers() {
    	return columnEntries.keySet();
    }
    
    public Page openTravelOffer(String entryName) {
    	if (columnEntries.containsKey(entryName)) {
    		WebElement link = columnEntries.get(entryName);
    		link.click();
    		getWait().until(Coordinators.stalenessOf(link));
    		return new Page(driver);
    	}
    	throw new IllegalArgumentException(
    			"Deals menu column '" + columnName + "' does not include travel offer '" + entryName + "'");
    }

	public static Object getKey(SearchContext context) {
		return context.findElement(Using.COLUMN_HEADING.locator).getText();
    }
}
