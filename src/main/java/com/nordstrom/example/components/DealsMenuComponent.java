package com.nordstrom.example.components;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;

import com.nordstrom.automation.selenium.model.ComponentContainer;
import com.nordstrom.automation.selenium.model.PageComponent;

public class DealsMenuComponent extends PageComponent {
	
	enum Using implements ByEnum {
		COLUMN(By.cssSelector("div.col"));
		
		private By locator;
		
		Using(By locator) {
			this.locator = locator;
		}

		@Override
		public By locator() {
			return locator;
		}
	}

	public DealsMenuComponent(By locator, ComponentContainer parent) {
		super(locator, parent);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		packages = newComponentMap(DealsMenuColumnComponent.class, Using.COLUMN.locator);
	}
	
	private final Map<Object, DealsMenuColumnComponent> packages;
	
	private MainMenuComponent getContainer() {
		return (MainMenuComponent) parent;
	}
	
	public Set<String> getPackageNames() {
		return packages.keySet().stream().map(Object::toString).collect(Collectors.toSet());
	}
	
	public DealsMenuColumnComponent fromPackage(String packageName) {
		if (packages.containsKey(packageName)) {
			getContainer().openDealsMenu();
			return packages.get(packageName);
		}
    	throw new IllegalArgumentException(
    			"Deals menu does not include package name '" + packageName + "'");
	}

}
