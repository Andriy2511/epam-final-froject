package com.example.finalproject.path;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;

/**
 * The PathBuilder class response for building path for images
 */
public class PathBuilder {

    /**
     * This method gets the context from the request and then creates the path where the images are stored
     * @param request - HttpServletRequest
     * @param photo - name of the photo
     * @return - String path
     */
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
        path += "finalProject" + separator + "src" + separator + "main" + separator + "webapp" + separator + "image" + separator + photo;
        return path;
    }
}