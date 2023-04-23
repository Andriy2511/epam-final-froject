package com.example.finalproject.locale;

import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InternationalizationMessageTest {

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrintMessageForEnglishLocale() {
        String localeMessage = "locale.Name";
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("lang")).thenReturn("en");

        String message = InternationalizationMessage.printMessage(request, localeMessage);

        Assertions.assertEquals("Name", message);
        verify(request.getSession(), times(1)).getAttribute("lang");
    }

    @Test
    public void testPrintMessageForUkraineLocale() {
        String localeMessage = "locale.Name";
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("lang")).thenReturn("ua");

        String message = InternationalizationMessage.printMessage(request, localeMessage);

        Assertions.assertEquals("Ім'я", message);
        verify(request.getSession(), times(1)).getAttribute("lang");
    }

    @Test
    public void testPrintMessageForWithoutLanguageLocale() {
        String localeMessage = "locale.Name";
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("lang")).thenReturn("en");

        String message = InternationalizationMessage.printMessage(request, localeMessage);

        Assertions.assertEquals("Name", message);
        verify(request.getSession(), times(1)).getAttribute("lang");
    }
}