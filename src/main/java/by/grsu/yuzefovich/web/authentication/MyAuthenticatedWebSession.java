package by.grsu.yuzefovich.web.authentication;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.log4j.Logger;
import org.apache.wicket.RestartResponseException;

import java.sql.SQLException;
import java.util.List;

import by.grsu.yuzefovich.web.admin.AdminPage;
import by.grsu.yuzefovich.web.user.UserPage;
import by.grsu.yuzefovich.datamodel.UserAccessData;
import by.grsu.yuzefovich.dataaccess.impl.UserAccessDataDao;





public class MyAuthenticatedWebSession extends AuthenticatedWebSession
{
    
	private static final Logger log = Logger.getLogger(MyAuthenticatedWebSession.class.getName());
	
    public MyAuthenticatedWebSession(Request request)
    {
        super(request);
    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        final String adminData = "admin";
        UserAccessDataDao userAccessDataDao = new UserAccessDataDao();
        List<UserAccessData> users = null;
		users = userAccessDataDao.getAll();
        
        
        if (adminData.equals(username) && adminData.equals(password)) {
        	log.info("Admin entered");
        	throw new RestartResponseException(AdminPage.class);        	
        }        	
        else {
        	for (UserAccessData userAccessData : users)
        		if (userAccessData.getLogin().equals(username) && userAccessData.getPassword().equals(password)) {
        			try {
        				log.info("User " + username + " entered");
						throw new RestartResponseException(new UserPage(username, password));
					} catch (SQLException e) {
						log.error("Error in access to user page");
						e.printStackTrace();
					}
        		}        	        
        }
        return false;
    }

    @Override
    public Roles getRoles()
    {
        return null;
    }
}
