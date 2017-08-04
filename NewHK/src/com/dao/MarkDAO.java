package com.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import com.Bean.MarkBean;
import com.util.SQL;

public class MarkDAO implements Runnable {
	private MarkBean mark;
	private SQL a = new SQL();
	String s = "";
	Statement sta = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String sql = "insert into mark(mark, markNO,status,type,name,service,classNO,date)"
				+" values ({0},{1},{2},{3},{4},{5},{6},{7})";
		s = MessageFormat.format(sql, "'"+mark.getMark()+"'", "'"+mark.getMarkNO()+"'", "'"+mark.getStatus()+"'"
				 ,"'"+mark.getType()+"'", "'"+mark.getName()+"'","'"+mark.getService()+"'"
				 ,mark.getClassNO(),"'"+sdf.format(mark.getDate())+"'");
		sta = a.addJDBC("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "sherlock");
		System.out.println(s);
		a.change(s, sta);
		try {
			a.closeChange();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setMark(MarkBean mark){
		this.mark = mark;
	}
	public MarkDAO(MarkBean mark){
		setMark(mark);
	}
	
}
