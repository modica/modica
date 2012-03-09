package org.modica.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AfpFilter implements Filter {

    // private AfpService afpService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);

        } finally {
            // afpService.closeSession();
        }
    }

    @Override
    public void destroy() {
    }

    // public void setAfpService(AfpService afpService) {
    // this.afpService = afpService;
    // }

}
