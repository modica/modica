package org.modica.web;


import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.modica.web.model.AfpServiceRequestCycleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application object for the Wicket web application.
 *
 */
public class ModicaApplication extends WebApplication {

    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getRequestCycleListeners().add(getApplicationContext().getBean(AfpServiceRequestCycleListener.class));
    }

    private ApplicationContext getApplicationContext() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new ModicaSession(request);
    }

}
