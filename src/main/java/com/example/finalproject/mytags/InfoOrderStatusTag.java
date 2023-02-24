package com.example.finalproject.mytags;

import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * Print the textual representation of the order status
 */
public class InfoOrderStatusTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(InfoOrderStatusTag.class);
    private int orderStatusId;

    @Override
    public int doStartTag() {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getOrderStatusNameById());
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * Gets the order status name by id
     * @return textual representation of the order status
     */
    public String getOrderStatusNameById(){
        return DAOFactory.getDaoFactory("MYSQL").getOrderStatusDAO().getOrderStatusById(orderStatusId).get(0).getName();
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
