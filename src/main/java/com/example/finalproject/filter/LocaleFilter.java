package com.example.finalproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
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
