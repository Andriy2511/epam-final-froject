package com.example.finalproject.pagination;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The pagination class is responsible for changing the starting page in the list
 */
public class Pagination {
    /**
     * The method gets parameter "next" or "previous" from request. If parameter equals "next" the method increase the starting page by one,
     * and if the parameter equals "previous" the method decrease the starting page by one.
     * @param request HttpServletRequest
     * @param numberOfRecords number of records
     * @param startPage start page
     * @param recordsPerPage records per page
     * @return the page from which pagination begins
     */
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
