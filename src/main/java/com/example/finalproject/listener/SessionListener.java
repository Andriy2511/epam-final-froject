package com.example.finalproject.listener;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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
