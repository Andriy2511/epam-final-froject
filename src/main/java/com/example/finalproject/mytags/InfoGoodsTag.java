package com.example.finalproject.mytags;

import com.example.finalproject.dao.DAOFactory;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Print the textual representation of the goods
 */
public class InfoGoodsTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(InfoGoodsTag.class);
    private int goodsId;
    @Override
    public int doStartTag() {
        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(getGoodsNameById());
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    /**
     * Gets the goods name by id
     * @return textual representation of the goods
     */
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
