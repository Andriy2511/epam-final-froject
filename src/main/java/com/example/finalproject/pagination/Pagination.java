package com.example.finalproject.pagination;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Pagination {
    public static int pagination(HttpServletRequest request, int numberOfRecords, int startPage, int recordsPerPage) {
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
        if(startPage > numberOfPages && startPage > 1)
            startPage--;
        try {
            if(request.getParameter("page") != null){
                if(request.getParameter("page").equals("next")){
                    if(startPage < numberOfPages) {
                        startPage++;
                    }
                }else if(request.getParameter("page").equals("previous")){
                    if(startPage > 1) {
                        startPage--;
                    }
                }
            }
        } catch (NumberFormatException e){
            startPage = 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        return startPage;
    }
}
