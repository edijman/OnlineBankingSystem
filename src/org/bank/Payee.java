package org.bank;

public class Payee {
	private String name;
	private String account_no;
	
	
	public Payee(String name, String account_no) {
		super();
		this.name = name;
		this.account_no = account_no;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String getName() {
		return name;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public void setName(String name) {
		this.name = name;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String getAccount_no() {
		return account_no;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	
}
