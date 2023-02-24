package com.example.finalproject.mytags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * Print the notification to the page
 */
public class NotificationTag extends TagSupport {
    String notification;
    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
