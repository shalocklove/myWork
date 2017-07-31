package com.Bean;

import java.text.MessageFormat;

public class URLBean {
	private String key;
	private String file_NO;
	private String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_main_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY={0}&FILE_NO={1}&FILE_NO_TYPE=REG_TM&SOAPQC=1";
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFile_NO() {
		return file_NO;
	}
	public void setFile_NO(String file_NO) {
		this.file_NO = file_NO;
	}
	public String getURL(){
		return MessageFormat.format(url, "key", "file_NO");
	}
	public URLBean(String key, String file_NO) {
		this.key = key;
		this.file_NO = file_NO;
	}
	
}
