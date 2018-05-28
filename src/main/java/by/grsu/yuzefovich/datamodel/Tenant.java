package by.grsu.yuzefovich.datamodel;

import java.util.ArrayList;
import java.io.Serializable;

public class Tenant extends AbstractModel implements Serializable {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
}
