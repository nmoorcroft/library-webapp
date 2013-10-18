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

@Component
public class IndexServlet implements HttpRequestHandler {

    @Value("${project.version}")
    private String version;
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("version", version);
        request.setAttribute("locale", angularLocale(request.getLocale()));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
    }
    
    private String angularLocale(Locale locale) {
        return locale.getLanguage() + (isEmpty(locale.getCountry()) ? "" : "-" + locale.getCountry());
    }

}

