package com.Bean;

import java.net.Proxy;

public class AgencyBean {
	private String ip;
	private int post;
	private Proxy proxy;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public Proxy getProxy() {
		return proxy;
	}
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	public boolean equals(AgencyBean agency){
		if(agency.getIp() == null || !this.ip.equals(agency.getIp()))
			return false;
		if(!(this.post == agency.getPost()))
			return false;
		return true;
	}
}
