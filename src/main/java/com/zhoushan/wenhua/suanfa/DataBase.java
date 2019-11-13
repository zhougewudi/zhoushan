package com.zhoushan.wenhua.suanfa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DataBase {
	ResultSet resultset = null;
	private String database;
	private String table;
	private String user;
	private String password;
	DataBase(String database,String table,String user,String password){
		this.database = database;
		this.table = table;
		this.user = user;
		this.password = password;
	}
	
	
	 public ResultSet start() {
		 try {
			 Connection con = null;
			 Statement statement = null;
			 PreparedStatement prepare = null;
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database +"?serverTimezone=UTC&characterEncoding=UTF-8", user, password);
			 statement = con.createStatement();
			 String sql = "select * from " + table;
			 
			  resultset = statement.executeQuery(sql);
			 
//			 ResultSetMetaData data = resultset.getMetaData();
//			 for(int i = 0 ; i < data.getColumnCount() ; i++) {
//				 System.out.print(data.getColumnLabel(i + 1) + " ");
//			 }
//			 System.out.println();
//			 while(resultset.next()) {
//				 System.out.println(resultset.getObject(1) + "  " + resultset.getObject(2) + "  "  + resultset.getObject(3));
//			 }
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}finally {
			return resultset;
		}
		
	}
}
