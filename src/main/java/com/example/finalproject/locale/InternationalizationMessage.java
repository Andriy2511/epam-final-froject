package com.example.finalproject.locale;

import com.example.finalproject.command.admin.AddProductCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * InternationalizationMessage class is responsible for converting messages into the appropriate language
 */
public class InternationalizationMessage {
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);

    /**
     * Gets the name of the message to be converted according to the language whose value is stored in the session.
     * If the message is not found in the files or the required language parameter is missing, returns the message in untranslated form.
     * @param request HttpServletRequest
     * @param localeMessage the name of the message that is recorded in the language properties files
     * @return converted message
     */
    public static String printMessage(HttpServletRequest request, String localeMessage){
        String message;
        logger.info("Locale massage is {}", localeMessage);
        String language = (String) request.getSession().getAttribute("lang");
        logger.info("language is {}", language);
        try {
            if(language.equals("en") && localeMessage != null && !localeMessage.equals("")){
                ResourceBundle bundle = ResourceBundle.getBundle("locale");
                message = bundle.getString(localeMessage);
            } else if(language.equals("ua") && localeMessage != null && !localeMessage.equals("")){
                ResourceBundle bundle = ResourceBundle.getBundle("locale_ua");
                message = bundle.getString(localeMessage);
            } else {
                message = localeMessage;
            }
        } catch (MissingResourceException | NullPointerException e){
            logger.error(e);
            message = localeMessage;
            e.printStackTrace();
        }

        return message;
    }
}
