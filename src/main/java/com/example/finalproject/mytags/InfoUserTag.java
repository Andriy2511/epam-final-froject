package com.example.finalproject.mytags;

import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Print the textual representation of the user's name
 */
public class InfoUserTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(InfoUserTag.class);
    private int userId;
    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getUserNameById());
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * Gets the user's name by id
     * @return textual representation of the user's name
     */
    public String getUserNameById(){
        return DAOFactory.getDaoFactory("MYSQL").getUserDAO().getUserById(userId).get(0).getName();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
