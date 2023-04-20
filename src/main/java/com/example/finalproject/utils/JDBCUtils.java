package com.example.finalproject.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The JDBCUtils class is responsible for connecting to the database
 */
public class JDBCUtils {
	private static final HikariConfig config = new HikariConfig();
	private static final Logger logger = LogManager.getLogger(JDBCUtils.class);
	private static HikariDataSource ds;

	static {
		try {
			InputStream input = JDBCUtils.class.getClassLoader().getResourceAsStream("datasource.properties");
			Properties props = new Properties();
			props.load(input);
			config.setJdbcUrl("jdbc:mysql://" + props.getProperty("dataSource.serverName") + ":" + props.getProperty("dataSource.port") + "/" + props.getProperty("dataSource.databaseName"));
			config.setUsername(props.getProperty("dataSource.user"));
			config.setPassword(props.getProperty("dataSource.password"));
			config.setDriverClassName(props.getProperty("dataSourceClassName"));
			config.setMinimumIdle(Integer.parseInt(props.getProperty("minimumIdle")));
			config.setMaximumPoolSize(Integer.parseInt(props.getProperty("maximumPoolSize")));
			config.setConnectionTimeout(Long.parseLong(props.getProperty("connectionTimeout")));
			config.setIdleTimeout(Long.parseLong(props.getProperty("idleTimeout")));
			config.setMaxLifetime(Long.parseLong(props.getProperty("maxLifetime")));
			config.setAutoCommit(Boolean.parseBoolean(props.getProperty("autoCommit")));
			config.setConnectionTestQuery(props.getProperty("connectionTestQuery"));
			config.addDataSourceProperty("cachePrepStmts", props.getProperty("cachePrepStmts"));
			config.addDataSourceProperty("prepStmtCacheSize", props.getProperty("prepStmtCacheSize"));
			config.addDataSourceProperty("prepStmtCacheSqlLimit", props.getProperty("prepStmtCacheSqlLimit"));
			ds = new HikariDataSource(config);
		} catch (IOException e) {
			logger.error("Failed to load HikariCP config file");
			e.printStackTrace();
		}
	}

	/**
	 * Gets a database connection
	 *
	 * @return connection to the database
	 */
	public static Connection getConnection()  throws SQLException {
		return ds.getConnection();
	}
}
