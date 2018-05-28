package by.grsu.yuzefovich.web.admin;

import org.apache.wicket.markup.html.link.Link;

import by.grsu.yuzefovich.web.authentication.BasePage;


public class AdminPage extends BasePage {
	
	public AdminPage() {
		
		
		add(new Link("addTenant") {

			@Override
			public void onClick() {
				setResponsePage(new AddingTenantPage());
			}
		});
		
		add(new Link("showRequests") {
			
			@Override
			public void onClick() {
				setResponsePage(new ShowAllRequestsPage());
			}
		});
		
	}
	
}