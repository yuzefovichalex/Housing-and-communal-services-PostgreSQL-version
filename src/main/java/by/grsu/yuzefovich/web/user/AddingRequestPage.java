package by.grsu.yuzefovich.web.user;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;


import by.grsu.yuzefovich.dataaccess.impl.TenantDao;
import by.grsu.yuzefovich.dataaccess.impl.UserAccessDataDao;
import by.grsu.yuzefovich.dataaccess.impl.RequestDao;
import by.grsu.yuzefovich.datamodel.Tenant;
import by.grsu.yuzefovich.datamodel.UserAccessData;
import by.grsu.yuzefovich.datamodel.Request;

public class AddingRequestPage extends WebPage {
	
	private static final Logger log = Logger.getLogger(AddingRequestPage.class.getName());
	
	private TenantDao tenantDao;
	private Tenant tenant;
	private RequestDao requestDao;
	private Request request;
	private String username;
	private String password;
	private String returnTo;

	public AddingRequestPage(String userName, String passWord, String returnTo) throws SQLException {
		super();
		this.username = userName;
		this.password = passWord;
		this.returnTo = returnTo;
		tenantDao = new TenantDao();
		requestDao = new RequestDao();
		
		Long accessDataId = 0l;
		UserAccessDataDao userAccessDataDao = new UserAccessDataDao();
		for(UserAccessData userAccessData : userAccessDataDao.getAll())
			if(userAccessData.getLogin().equals(username) && userAccessData.getPassword().equals(password)) {
				accessDataId = userAccessData.getTenantId();
				break;
			}				
		
		this.tenant = tenantDao.get(accessDataId);
		request = new Request();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<Request> form = new Form<Request>("form", new CompoundPropertyModel<Request>(request));
		add(form);

		TextField<String> typeOfWorkField = new TextField<>("typeOfWork");
		typeOfWorkField.setLabel(new ResourceModel("typeOfWork"));
		typeOfWorkField.setRequired(true);
		form.add(typeOfWorkField);

		TextField<Integer> scopeOfWorkField = new TextField<>("scopeOfWork");
		scopeOfWorkField.setLabel(new ResourceModel("scopeOfWork"));
		scopeOfWorkField.setRequired(true);
		form.add(scopeOfWorkField);

		TextField<Integer> leadTimeField = new TextField<>("leadTime");
		leadTimeField.setLabel(new ResourceModel("leadTime"));
		leadTimeField.setRequired(true);
		form.add(leadTimeField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				request.setIsAccepted(false);
				request.setTenantId(tenant.getId());
				requestDao.saveNew(request);
				if (requestDao.get(request.getId()) != null)
					log.info("New request successfuly added");
				else
					log.warn("Request adding failed");
				if (returnTo == "UserPage")
					try {
						setResponsePage(new UserPage(username, password));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				if (returnTo == "ShowRequestsPage")
					try {
						setResponsePage(new ShowRequestsPage(username, password));
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});

		form.add(new Link("cancel") {

			@Override
			public void onClick() {
				if (returnTo == "UserPage")
					try {
						setResponsePage(new UserPage(username, password));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				if (returnTo == "ShowRequestsPage")
					try {
						setResponsePage(new ShowRequestsPage(username, password));
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});
		add(new FeedbackPanel("feedback"));
	}

}
