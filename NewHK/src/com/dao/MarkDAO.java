package com.dao;

import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import com.Bean.MarkBean;
import com.util.SQL;

public class MarkDAO implements Runnable {
	private MarkBean mark; 
	private SQL Sql;
	String s = "";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Statement sta = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public void insert(MarkBean mark) {
		String sql = "insert into mark(mark,application,status,type,name,address,service,classNO,date)"
				+" values ('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}')";
		s = MessageFormat.format(sql, mark.getMark(), mark.getApplication(), mark.getStatus(), mark.getType()
				, mark.getName(), mark.getAddress(), mark.getService(), mark.getClassNO(), sdf.format(mark.getDate()));
		sta = Sql.addJDBC("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "sherlock");
		Sql.change(s, sta);
	}
	
}
