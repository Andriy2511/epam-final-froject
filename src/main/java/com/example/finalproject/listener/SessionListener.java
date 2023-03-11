package com.example.finalproject.listener;

import com.example.finalproject.utils.JDBCUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Set the default language to English
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    public SessionListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        if (se.getSession().getAttribute("lang") == null) {
            se.getSession().setAttribute("lang", "en");
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }
}
