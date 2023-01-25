package com.example.finalproject.mytags;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SuppressWarnings("serial")
public class InfoCategoryTag extends TagSupport {
    private int categoryId;
    private static final Logger logger = LogManager.getLogger(InfoCategoryTag.class);
    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getCategoryNameById(categoryId));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    public String getCategoryNameById(int categoryId) {
        return DAOFactory.getDaoFactory("MYSQL").getCategoryDAO().showCategoryById(categoryId).get(0).getName();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}


