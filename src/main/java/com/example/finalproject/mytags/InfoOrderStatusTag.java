package com.example.finalproject.mytags;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class InfoOrderStatusTag extends TagSupport {
    //private static final Logger logger = LogManager.getLogger(InfoOrderStatusTag.class);
    private int orderStatusId;

    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getOrderStatusNameById());
        } catch (IOException e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

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
