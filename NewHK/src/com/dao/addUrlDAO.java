package com.dao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class addUrlDAO implements Runnable {
	private String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/fssr01001s_schi.jsp?SOAPQC=-1&FILE_NO_TYPE=A&FILE_NO={0}&FILE_NO_SMM=5&FILE_NO={1}&FILE_NO_SMM=3&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_CODE=&TM_CODE_OP=1&CLASS_NO=&MARK_TYPE=Y&SPECIAL_SIGNS=Y&COLOUR_CLAIM_DESCRIP_STYLE=&COLOUR_CLAIM_DESCRIP_STYLE_SMM=0&APPL_NAME=&APPL_NAME_SMM=0&LICENCEE=&LICENCEE_SMM=0&ADD_FOR_SERVICE=&ADD_FOR_SERVICE_SMM=0&FILE_DT=&FILE_DT_SMM=5&FILE_DT=&FILE_DT_SMM=3&RREG_DT=&RREG_DT_SMM=5&RREG_DT=&RREG_DT_SMM=3&EXP_DT=&EXP_DT_SMM=5&EXP_DT=&EXP_DT_SMM=3&PUB_DT=&PUB_DT_SMM=5&PUB_DT=&PUB_DT_SMM=3&TM_SPEC=&TM_SPEC_SMM=0&TM_DIS=&TM_DIS_SMM=6&STYPE=S&TYPE=S&LIVE_STATUS=Y";
	private List<String> list = new ArrayList<String>(); 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		for(int i = 19280000; i <= 19920010; i += 20){
		for(int i = 19920000; i <= 19930010; i += 20){
			synchronized(list){
				list.add(MessageFormat.format(url, String.valueOf(i), String.valueOf(i + 19)));
			}
		}
	}
	public void removeList(String url){
		synchronized(list){
			list.remove(url);
		}
	}
	public List<String> getList(){
		return list;
	}
}
