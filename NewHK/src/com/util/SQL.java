package com.util;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
public class SQL {
	private String User = "root";//���ݿ��û���
	private String Password = "sherlock";//���ݿ�����
	private String url = "jdbc:mysql://localhost:3306/steam?useUnicode=true&characterEncoding=UTF-8";//���ݿ����
	private Connection con = null;//��������
	private Statement sta = null;//���ݿ����
	private ResultSet rs = null;//���Ҳ���
	public Statement addJDBC(String url, String user, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��һ�����ӳ���");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�ڶ������ӳ���");
		}
		try {
			sta = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();System.out.print("��ӳ���1");
		}
		return sta;
	}
	public ResultSet select(String sql, Statement sta){
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.println("��ӳ���");
		}finally{}
		return rs;
	}
	public int change(String sql, Statement sta){
		int i = 0;
		try {
			i = sta.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();System.out.print("��ӳ���2");
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