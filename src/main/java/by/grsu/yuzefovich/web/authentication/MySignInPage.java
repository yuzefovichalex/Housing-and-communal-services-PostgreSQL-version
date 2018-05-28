package by.grsu.yuzefovich.web.authentication;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Simple example of a sign in page.
 * 
 * @author Jonathan Locke
 */
public final class MySignInPage extends WebPage
{
    /**
     * Construct
     */
    public MySignInPage()
    {
        this(null);
    }

    /**
     * Constructor
     * 
     * @param parameters
     *            The page parameters
     */
    public MySignInPage(final PageParameters parameters)
    {
        // That is all you need to add a logon panel to your application with rememberMe
        // functionality based on Cookies. Meaning username and password are persisted in a Cookie.
        // Please see ISecuritySettings#getAuthenticationStrategy() for details.
        add(new SignInPanel("signInPanel", false));
    }
}
