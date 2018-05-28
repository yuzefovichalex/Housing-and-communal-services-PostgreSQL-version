package by.grsu.yuzefovich.web.user;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.grsu.yuzefovich.web.authentication.HomePage;

public class SignOut extends WebPage
{

    public SignOut(final PageParameters parameters)
    {    	
        getSession().invalidate();
        setResponsePage(new HomePage());
    }
}
