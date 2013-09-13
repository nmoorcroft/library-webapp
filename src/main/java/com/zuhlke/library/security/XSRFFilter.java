package com.zuhlke.library.security;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A Servlet filter for preventing Cross-site request forgery attacks.
 * <p>
 * Cross-site request forgery, also known as a one-click attack or session riding and abbreviated as 
 * CSRF or XSRF, is a type of malicious exploit of a web site whereby unauthorised commands are transmitted from a 
 * user that the web site trusts. Unlike cross-site scripting (XSS), which exploits the trust a user has for a 
 * particular site, CSRF exploits the trust that a site has in a user's browser.
 */
@Component
public class XSRFFilter implements Filter {
	
	final Logger logger = LoggerFactory.getLogger(XSRFFilter.class);

    public static final String X_XSRF_TOKEN_HEADER = "X-XSRF-TOKEN";
    public static final String XSRF_TOKEN_NAME = "XSRF-TOKEN";

    @Inject
    private SecurityUtils securityUtils;
    
    public XSRFFilter() {
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (!equalsIgnoreCase("GET", request.getMethod()) && !isValidXSRFToken(request)) {
            setXSRFToken(request, response);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            logger.warn("Invalid XSRF-TOKEN found responding with SC_FORBIDDEN");
    		return;
        }
        
        setXSRFToken(request, response);
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
    
    private boolean isValidXSRFToken(final HttpServletRequest request) {
        String sessionToken = (String) request.getSession().getAttribute(XSRF_TOKEN_NAME);
        String token = (String) request.getHeader(X_XSRF_TOKEN_HEADER);
        return StringUtils.equals(token, sessionToken);
    }

    private void setXSRFToken(final HttpServletRequest request, final HttpServletResponse response) {
        String sessionToken = (String) request.getSession().getAttribute(XSRF_TOKEN_NAME);

        if (sessionToken == null) {
	        String token = securityUtils.getRandomString(50);
	        request.getSession().setAttribute(XSRF_TOKEN_NAME, token);
	
	        Cookie cookie = new Cookie(XSRF_TOKEN_NAME, token);
	        cookie.setMaxAge(-1);
	        cookie.setPath(request.getContextPath());
	
	        response.addCookie(cookie);

        }
        
    }


}

