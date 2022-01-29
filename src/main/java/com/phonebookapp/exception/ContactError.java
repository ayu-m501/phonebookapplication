package com.phonebookapp.exception;

public class ContactError {

	private Integer errorCode;
	private String erroDesc;

	public ContactError(Integer errorCode, String erroDesc) {
		super();
		this.errorCode = errorCode;
		this.erroDesc = erroDesc;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErroDesc() {
		return erroDesc;
	}

	public void setErroDesc(String erroDesc) {
		this.erroDesc = erroDesc;
	}

}
