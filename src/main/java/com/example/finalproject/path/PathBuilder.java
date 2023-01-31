package com.example.finalproject.path;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;

public class PathBuilder {
    public static String buildImagePath(HttpServletRequest request, String photo){
        String separator = File.separator;
        ServletContext c = request.getServletContext();
        String rootAddress = c.getRealPath(".");
        String[] list = rootAddress.split("\\\\");
        String path = "";
        for (String str : list) {
            if(!str.equals("finalProject")) {
                path = path + str + File.separator;
            } else {
                break;
            }
        }
        path += "finalproject" + separator + "src" + separator + "main" + separator + "webapp" + separator + "image" + separator + photo;
        return path;
    }
}
