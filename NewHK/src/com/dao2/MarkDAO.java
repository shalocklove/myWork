package com.dao2;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
		
		String sql = "insert into mark(mark, markNO, status, markname, type, owner, address, service";
		String value = " values ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}";
		String values = MessageFormat.format(value, "'"+mark.getMark()+"'", "'"+mark.getMarkNO()+"'"
				, "'"+mark.getStatus()+"'", "'"+mark.getMarkName()+"'", "'"+mark.getType()+"'", "'"+mark.getOwner()+"'"
				, "'"+mark.getAddress()+"'", "'"+mark.getService()+"'");
		if(mark.getDate() != null){
			values += ", '" + sdf.format(mark.getDate()) + "'";
			sql += ", date";
		}
		if(mark.getActualdate() != null){
			values += ", '" + sdf.format(mark.getActualdate()) + "'";
			sql += ", actualdate";
		}
		if(mark.getExpirydate() != null){
			values += ", '" + sdf.format(mark.getExpirydate()) + "'";
			sql += ", expirydate";
		}
		if(mark.getFillingdate() != null){
			values += ", '" + sdf.format(mark.getFillingdate()) + "'";
			sql += ", fillingdate";
		}
		sta = a.addJDBC("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8", "root", "sherlock");
		s = sql + ")" + values + ")";
		System.out.println(s);
		a.change(s, sta);
		for(Map.Entry<Integer, String> e : mark.getClassNO().entrySet()){
			for(String specification : e.getValue().split(";")){
				s = "insert into specification (markNO, class, specification) values ('"+ mark.getMarkNO() +"'"
						+ ", "+ e.getKey() +", '"+ specification +"')";
				System.out.println(s);
				a.change(s, sta);
			}
		}
		for(Map.Entry<Date, String> e : mark.getMatters().entrySet()){
			s = "insert into matter (markNO, entrydate, matters) values ('"+ mark.getMarkNO() +
					"', '" +sdf.format(e.getKey())+ "', '" +e.getValue()+ "')";
			System.out.println(s);
			a.change(s, sta);
		}
		
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
