package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.logout.LogOutCommand;
import com.example.finalproject.utils.JDBCUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.*;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.mockito.Mockito.*;

import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class AddProductCommandTest {
//    @BeforeAll
//    public static void setUp() {
//        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
//    }

//    @BeforeAll
//    public static void setUp() throws Exception {
//        Context context = null;
//        try {
//            Properties props = new Properties();
//            props.load(new FileInputStream("src/main/webapp/META-INF/context.xml"));
//            context = new InitialContext(props);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (context != null) {
//                context.close();
//            }
//        }
//    }

    @Test
    public void addProductTest(){
        final AddProductCommand servlet = new AddProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    }

//    @Test
//    public void testDBConnection() throws NamingException {
//        System.out.println(System.getProperty("java.version"));
//        Properties env = new Properties();
//        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
//        InitialContext ctx = new InitialContext(env);
//        System.out.println("Context created successfully");
//
//        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/shop");
////        Context envCtx = (Context) ctx.lookup("java:comp/env");
////        DataSource ds = (DataSource) envCtx.lookup("jdbc/shop");
//        System.out.println("Data source retrieved successfully");
//    }
//
//    @Test
//    public void testDBConnection2() throws NamingException {
//        Properties props = new Properties();
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
//        props.put(Context.PROVIDER_URL, "file:/tmp");
//        InitialContext ctx = new InitialContext(props);
//    }
//    @Test
//    public void testDBConnection3() throws NamingException {
//        Context initCtx = new InitialContext();
//        Context envCtx = (Context) initCtx.lookup("java:comp/env");
//        DataSource ds = (DataSource) envCtx.lookup("jdbc/shop");
//        NamingEnumeration<NameClassPair> list = initCtx.list("");
//        while (list.hasMore()) {
//            System.out.println(list.next().getName());
//        }
//    }
//
//    @Test
//    public void testDBConnection4() throws NamingException, SQLException {
//        Context context = new InitialContext();
//        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/shop");
//        if (dataSource == null) {
//            // setup the connection pool if not already set up
//            PoolProperties p = new PoolProperties();
//            p.setUrl("jdbc:mysql://localhost:3306/shop");
//            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            p.setUsername("root");
//            p.setPassword("12345");
//            p.setMaxIdle(4);
//
//            dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
//            ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPoolProperties(p);
//
//            // bind the data source to the context
//            context.bind("java:comp/env/jdbc/shop", dataSource);
//        }
//    }

    @Test
    public void forwardToAdminAddProductPageWhenPriceLowerThanZero() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        final ICommand servlet = new AddProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        String path = "admin/admin_add_product.jsp";
        when(request.getParameter("price")).thenReturn("-1");
        when(request.getParameter("category")).thenReturn("test");
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        verify(dispatcher).forward(request, response);
        verify(request, times(1)).getRequestDispatcher(path);
        //Assertions.assertEquals("Price must be higher or equal than 0", request.getAttribute("NOTIFICATION"));
    }
}
