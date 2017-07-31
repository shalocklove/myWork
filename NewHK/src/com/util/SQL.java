package com.util;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
public class SQL {
	private String User = "root";//数据库用户名
	private String Password = "sherlock";//数据库密码
	private String url = "jdbc:mysql://localhost:3306/steam?useUnicode=true&characterEncoding=UTF-8";//数据库加载
	private Connection con = null;//建立连接
	private Statement sta = null;//数据库操作
	private ResultSet rs = null;//查找操作
	public Statement addJDBC(String url, String user, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("第一步连接出错");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("第二步连接出错");
		}
		try {
			sta = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();System.out.print("添加出错1");
		}
		return sta;
	}
	public ResultSet select(String sql, Statement sta){
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println("添加出错");
		}finally{}
		return rs;
	}
	public int change(String sql, Statement sta){
		int i = 0;
		try {
			i = sta.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.print("添加出错2");
		}
		return i;
	}
	public void closeSelect() throws SQLException{
		rs.close();
		sta.close();
		con.close();
	}
	public void closeChange() throws SQLException {
			sta.close();
			con.close();
	}
}
//Migic. Don`t touch