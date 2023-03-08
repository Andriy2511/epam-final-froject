package com.example.finalproject.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The JDBCUtils class is responsible for connecting to the database
 */
public class JDBCUtils {
//	private static volatile DataSource ds;

	/**
	 * Gets a database connection
	 * @return connection to the database
	 */
//	public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException {
//		return getDatabase().getConnection();
//	}

	/**
	 * Receives DataSource to the database
	 * @return DataSource
	 */
//	public static DataSource getDatabase() throws ClassNotFoundException, SQLException, NamingException{
//		synchronized (JDBCUtils.class) {
//			if(ds == null) {
//				Context initCtx = new InitialContext();
//				Context envCtx = (Context) initCtx.lookup("java:comp/env");
//				ds = (DataSource) envCtx.lookup("jdbc/shop");
//			}
//		}
//		return ds;
//	}

	/**
	 * Outputs an SQL exception to the console
	 * @param ex SQL exception
	 */
//	public static void printSQLException(SQLException ex) {
//		for (Throwable e : ex) {
//			if (e instanceof SQLException) {
//				e.printStackTrace(System.err);
//				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
//				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
//				System.err.println("Message: " + e.getMessage());
//				Throwable t = ex.getCause();
//				while (t != null) {
//					System.out.println("Cause: " + t);
//					t = t.getCause();
//				}
//			}
//		}
//	}

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		config.setJdbcUrl( "jdbc:mysql://localhost:3306/shop" );
		config.setUsername( "root" );
		config.setPassword( "12345" );
		config.addDataSourceProperty( "cachePrepStmts" , "true" );
		config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
		ds = new HikariDataSource( config );
	}
	/**
	 * Gets a database connection
	 * @return connection to the database
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException {
		return ds.getConnection();
	}
}
