package by.grsu.yuzefovich.web.user;


import org.apache.wicket.markup.html.link.Link;


import by.grsu.yuzefovich.datamodel.Tenant;
import by.grsu.yuzefovich.datamodel.UserAccessData;
import by.grsu.yuzefovich.dataaccess.impl.TenantDao;
import by.grsu.yuzefovich.dataaccess.impl.UserAccessDataDao;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import by.grsu.yuzefovich.web.authentication.BasePage;

import java.sql.SQLException;

import org.apache.wicket.markup.html.basic.Label;





public class UserPage extends BasePage {
	
	private String name;
	private String username;
	private String password;
	private Tenant tenant;
	
	public UserPage() {
		
	}
	
	
	public UserPage(String userName, String passWord) throws SQLException {
		
		this.username = userName;
		this.password = passWord;
		
		Long accessDataId = 0l;
		UserAccessDataDao userAccessDataDao = new UserAccessDataDao();
		for(UserAccessData userAccessData : userAccessDataDao.getAll())
			if(userAccessData.getLogin().equals(username) && userAccessData.getPassword().equals(password)) {
				accessDataId = userAccessData.getTenantId();
				break;
			}				
		
		TenantDao tenantDao = new TenantDao();
		this.tenant = tenantDao.get(accessDataId);
		this.name = tenant.getName();
	
		
		add(new Label("helloMessage", "Welcome " + name + "!"));
		
		add(new Link("addRequest") {

			@Override
			public void onClick() {
				try {
					setResponsePage(new AddingRequestPage(username, password, "UserPage"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(new Link("showRequests") {
			
			@Override
			public void onClick() {
				try {
					setResponsePage(new ShowRequestsPage(username, password));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
}