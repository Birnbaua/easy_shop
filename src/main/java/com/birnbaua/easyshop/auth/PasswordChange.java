package com.birnbaua.easyshop.auth;

public class PasswordChange {
	
	private String oldPassword;
	
	private String password;
	
	public PasswordChange() {
		
	}
	
	public PasswordChange(String oldPw, String newPw) {
		this.oldPassword = oldPw;
		this.password = newPw;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
