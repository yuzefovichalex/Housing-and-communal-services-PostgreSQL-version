package by.grsu.yuzefovich.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;


import by.grsu.yuzefovich.dataaccess.impl.TenantDao;
import by.grsu.yuzefovich.dataaccess.impl.UserAccessDataDao;
import by.grsu.yuzefovich.dataaccess.impl.BrigadeDao;
import by.grsu.yuzefovich.dataaccess.impl.RequestDao;
import by.grsu.yuzefovich.datamodel.Tenant;
import by.grsu.yuzefovich.datamodel.UserAccessData;
import by.grsu.yuzefovich.datamodel.Brigade;
import by.grsu.yuzefovich.datamodel.Request;

public class ShowRequestsPage extends WebPage{
	
	private Tenant tenant;
	private RequestDao requestDao;
	private List<Request> list;
	private String username;
	private String password;
	
	public ShowRequestsPage(String userName, String passWord) throws SQLException {
		
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

		list = new ArrayList<Request>();
		requestDao = new RequestDao();
		for (Request request : requestDao.getAll())
			if (request.getTenantId().equals(tenant.getId()))
				list.add(request);
							
    }
	
	@Override
	protected void onInitialize() {
		super.onInitialize();

        final DataView<Request> dataView = new DataView<Request>("simple", new ListDataProvider(list)) {
        	
        public void populateItem(final Item item) {
            final Request user = (Request) item.getModelObject();
            item.add(new Label("id", user.getId()));
            item.add(new Label("typeOfWork", user.getTypeOfWork()));
            item.add(new Label("scopeOfWork", user.getScopeOfWork()));
            item.add(new Label("leadTime", user.getLeadTime()));
            item.add(new Label("isAccepted", user.getIsAccepted() ? "Performing" : "Processing"));
            try {
				item.add(new Label("brigade", user.getIsAccepted() ? getNumberOfWorkersInBrigade(user) : "None"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
            }
            
        };
        
        add(dataView);
        
        add(new Link("addRequest") {

			@Override
			public void onClick() {
				try {
					setResponsePage(new AddingRequestPage(username, password, "ShowRequestsPage"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		add(new Link("userPage") {
			
			@Override
			public void onClick() {
				try {
					setResponsePage(new UserPage(username, password));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Integer getNumberOfWorkersInBrigade(Request request) throws SQLException {
		BrigadeDao brigadeDao = new BrigadeDao();
		for(Brigade brigade : brigadeDao.getAll())
			if (request.getBrigadeId().equals(brigade.getId()))
				return brigade.getNumberOfWorkers();
		return null;
	}
 
}
