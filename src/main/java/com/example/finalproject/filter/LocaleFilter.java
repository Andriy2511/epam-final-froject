package com.example.finalproject.filter;

import com.example.finalproject.command.admin.AdminChangeProductCommand;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("LocaleFilter is started");
        HttpServletRequest req = (HttpServletRequest) request;
        String lang = request.getParameter("lang");
        if(lang != null) {
            if (lang.compareToIgnoreCase("en") == 0) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", new Locale("en"));
                req.getSession().setAttribute("bundle", resourceBundle);
                req.getSession().setAttribute("lang", "en");
            } else if (lang.compareToIgnoreCase("ua") == 0) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", new Locale("ua"));
                req.getSession().setAttribute("bundle", resourceBundle);
                req.getSession().setAttribute("lang", "ua");
            }
        }
        chain.doFilter(request, response);
    }
}
