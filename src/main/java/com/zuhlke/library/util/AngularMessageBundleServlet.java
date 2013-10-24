package com.zuhlke.library.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AngularMessageBundleServlet implements HttpRequestHandler {

    final Logger logger = LoggerFactory.getLogger(AngularMessageBundleServlet.class);
    
    private ObjectMapper objectMapper;
    private String basename;
        
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/javascript");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("angular.module('i18n',[]).value('messages',");
            writer.print(loadBundle(request.getLocale()));
            writer.print(");");
        }
    }
    
    private String loadBundle(Locale locale) {
        try {
            Map<String, String> bundleMap = new HashMap<>();
            ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                bundleMap.put(key, bundle.getString(key));
            }
            return objectMapper.writeValueAsString(bundleMap);

        } catch (Exception e) {
            logger.error("cannot load i18n messages", e);
            throw new IllegalStateException("cannot load i18n messages");
            
        }
        
    }
    
    public void setBaseName(String baseName) {
        this.basename = baseName;
    }
    
    public void setMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
}

