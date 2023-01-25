package com.example.finalproject.mytags;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class InfoGoodsTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(InfoGoodsTag.class);
    private int goodsId;
    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getGoodsNameById());
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
    public String getGoodsNameById(){
        return DAOFactory.getDaoFactory("MYSQL").getGoodsDAO().getGoodsById(goodsId).get(0).getName();
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
