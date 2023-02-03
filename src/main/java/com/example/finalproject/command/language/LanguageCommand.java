package com.example.finalproject.command.language;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(LanguageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //response.sendRedirect(buildRequest(request, response));
        testBuildURL(request, response);
    }

//    private String buildRequest(HttpServletRequest request, HttpServletResponse response){
//        logger.info("Method buildRequest is started");
//        StringBuilder stringBuilder = new StringBuilder();
//        @SuppressWarnings("unchecked")
//        Map<String, String[]> paramsMap = (Map<String, String[]>) request.getSession().getAttribute("mapParam");
//        if(paramsMap != null && !paramsMap.isEmpty()) {
//            if(paramsMap.size() == 1 && paramsMap.get("NOTIFICATION") != null) {
//                stringBuilder.append(request.getContextPath()).append(request.getSession().getAttribute("servletPath"));
//                buildMapRequest(paramsMap, stringBuilder);
//                System.out.println(stringBuilder);
//            }
//            else {
//                stringBuilder.append(request.getServletPath());//FrontController
//                buildMapRequest(paramsMap, stringBuilder);
//                stringBuilder.insert(0, request.getContextPath());
//                System.out.println(stringBuilder);
//            }
//        } else {
//            stringBuilder.append(request.getContextPath()).append(request.getSession().getAttribute("servletPath"));
//        }
//        request.getSession().removeAttribute("servletPath");
//        request.getSession().removeAttribute("mapParam");
//        logger.debug("stringBuilder is {}", stringBuilder);
//        System.out.println(stringBuilder);
//        return stringBuilder.toString();
//    }
//
//    private void buildMapRequest(Map<String, String[]> paramsMap, StringBuilder stringBuilder){
//        List<String> keysList = new ArrayList<>();
//        List<String> valuesList = new ArrayList<>();
//        for (String key : paramsMap.keySet()) {
//            if (!key.equals("page")) {
//                keysList.add(key);
//                valuesList.add(paramsMap.get(key)[0]);
//            }
//        }
//        if (!paramsMap.isEmpty()) {
//            stringBuilder.append("?");
//            for (int i = 0; i < keysList.size(); i++) {
//                stringBuilder.append(keysList.get(i)).append("=").append(valuesList.get(i));
//                if (i != paramsMap.size() - 1) {
//                    stringBuilder.append("&");
//                }
//            }
//        }
//    }

    private void testBuildURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("URL is " + request.getSession().getAttribute("MyURL"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getSession().getAttribute("MyURL"));
        //String url = (String) request.getSession().getAttribute("MyURL");
        String url = String.valueOf(stringBuilder);
        System.out.println(url);
        request.getSession().removeAttribute("MyURL");
        response.sendRedirect(url);
    }
}
