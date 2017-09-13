package com.citi.training.spring.adv.db;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DatabaseUtils {
	
	private static final String DB_DRIVER	= "db.driver";
	private static final String DB_URL		= "db.url";
	private static final String DB_USER		= "db.username";
	private static final String DB_PASSWORD	= "db.password";
	
	public static DataSource getDataSource(String name) {
		DataSource ds = null;
		try {

			ResourceBundle rb = ResourceBundle.getBundle(name);
			
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(rb.getString(DB_DRIVER));
			bds.setUrl(rb.getString(DB_URL));
			bds.setUsername(rb.getString(DB_USER));
			bds.setPassword(rb.getString(DB_PASSWORD));
			ds = bds;
		} catch (Exception e) {
			e.printStackTrace(); // FIXME: should not be in PROD code
			ds = null;
		}
		return ds;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
		}
	}
	
	public static void release(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		DatabaseUtils.release(stmt);
	}
	
	public static void release(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}
	
}
