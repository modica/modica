package org.modica.web;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for the Wicket web application.
 *
 */
public class WicketApplication extends WebApplication {

    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        getRequestCycleListeners().add(new AbstractRequestCycleListener() {
            @Override
            public void onBeginRequest(RequestCycle cycle) {
                System.out.println(Session.get().getId());
                super.onBeginRequest(cycle);
            }

        });
    }

}
