package com.example.finalproject.mytags;

import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Print the textual representation of the category
 */
@SuppressWarnings("serial")
public class InfoCategoryTag extends TagSupport {
    private int categoryId;
    private static final Logger logger = LogManager.getLogger(InfoCategoryTag.class);
    @Override
    public int doStartTag() {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getCategoryNameById(categoryId));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * Gets the category name by id
     * @param categoryId goods category id
     * @return textual representation of the category
     */
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


