package com.zuhlke.library.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A filter for setting no-cache, must-revalidate cache headers on the
 * {@code /api/} requests.
 */
@Component
public class CacheFilter implements Filter {

    final Logger logger = LoggerFactory.getLogger(CacheFilter.class);

    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse) resp).addHeader("Cache-Control", "no-cache, must-revalidate, max-age=0");
        ((HttpServletResponse) resp).addDateHeader("Expires", 0L);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
