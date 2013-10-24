package com.zuhlke.library.util;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

/**
 * Application index page controller
 *
 */
@Component
public class IndexServlet implements HttpRequestHandler {

    @Value("${index.expires}")
    private Long expires;
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("locale", angularLocale(request.getLocale()));
        response.setDateHeader("Expires", System.currentTimeMillis() + (1000L * expires));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
    }
    
    private String angularLocale(Locale locale) {
        return locale.getLanguage() + (isEmpty(locale.getCountry()) ? "" : "-" + locale.getCountry());
    }

}

