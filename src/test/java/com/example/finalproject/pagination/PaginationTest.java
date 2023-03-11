package com.example.finalproject.pagination;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaginationTest {
    @Mock
    HttpServletRequest request;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPaginationNext() {
        when(request.getParameter("page")).thenReturn("next");
        int result = Pagination.pagination(request, 20, 1, 10);
        assertEquals(2, result);
    }

    @Test
    public void testPaginationPrevious() {
        when(request.getParameter("page")).thenReturn("previous");
        int result = Pagination.pagination(request, 20, 2, 10);
        assertEquals(1, result);
    }

    @Test
    public void testPaginationNextWhenRichEndOfList() {
        when(request.getParameter("page")).thenReturn("next");
        int result = Pagination.pagination(request, 20, 2, 10);
        assertEquals(2, result);
    }

    @Test
    public void testPaginationPreviousWithStartPage() {
        when(request.getParameter("page")).thenReturn("previous");
        int result = Pagination.pagination(request, 20, 1, 10);
        assertEquals(1, result);
    }
}
