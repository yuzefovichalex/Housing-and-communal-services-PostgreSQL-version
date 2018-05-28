package by.grsu.yuzefovich.web.admin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;


import by.grsu.yuzefovich.dataaccess.impl.TenantDao;
import by.grsu.yuzefovich.dataaccess.impl.BrigadeDao;
import by.grsu.yuzefovich.dataaccess.impl.RequestDao;
import by.grsu.yuzefovich.datamodel.Brigade;
import by.grsu.yuzefovich.datamodel.Request;

public class ShowAllRequestsPage extends WebPage{
	
	private static final Logger log = Logger.getLogger(ShowAllRequestsPage.class.getName());
	
	private List<Request> list;
	
	public ShowAllRequestsPage() {
		
		RequestDao requestDao = new RequestDao();
		list = requestDao.getAll();

    }
	
	@Override
	protected void onInitialize() {
		super.onInitialize();

        final DataView<Request> dataView = new DataView<Request>("simple", new ListDataProvider(list)) {
        	
        public void populateItem(final Item item) {
            final Request user = (Request) item.getModelObject();
                     
            item.add(new Label("id", user.getId()));
            try {
				item.add(new Label("tenantName", getTenantName(user)));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            item.add(new Label("typeOfWork", user.getTypeOfWork()));
            item.add(new Label("scopeOfWork", user.getScopeOfWork()));
            item.add(new Label("leadTime", user.getLeadTime()));
            item.add(new Label("isAccepted", user.getIsAccepted() ? "Performing" : "Processing"));
            try {
				item.add(new Label("brigade", user.getIsAccepted() ? getNumberOfWorkersInBrigade(user) : "None"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
            item.add(new Link("acceptionAndChanging") {
            	
            	@Override
    			public void onClick() {
            		if(user.getIsAccepted())
						try {
							setResponsePage(new ChangeRequestPage(user));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					else
            			setResponsePage(new AcceptRequestPage(user));
    			}
            	
            }.add(new Label("label", user.getIsAccepted() ? "Change" : "Accept")));
            item.add(new Link("deleting") {
            	
            	@Override
    			public void onClick() {
            		try {
						DeleteRequest(user);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    				setResponsePage(new ShowAllRequestsPage());
    			}
            	
            });
            }
            
        };
        
        add(dataView);
        
		add(new Link("userPage") {
			
			@Override
			public void onClick() {
				setResponsePage(new AdminPage());
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
	
	public String getTenantName(Request request) throws SQLException {		
        TenantDao tenantDao = new TenantDao();
		return tenantDao.get(request.getTenantId()).getName();
	}
	
	public void DeleteRequest(Request request) throws SQLException {
		RequestDao requestDao = new RequestDao();
		BrigadeDao brigadeDao = new BrigadeDao();
		
		requestDao.delete(request.getId());
		if(request.getBrigadeId() != null)
		    brigadeDao.delete(request.getBrigadeId());
		
		if (requestDao.get(request.getId()) == null && brigadeDao.get(request.getBrigadeId()) == null)
			log.info("The request (and brigade connected to this request) deleting was successfully completed");
		else
			log.warn("The request (or brigade connected to this request) wasn't deleted");
				
	}
 
}
