package com.krkgj.blogapi.framework.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMessage {

	private String code;
	private String message;
	private Object result;
	
	public ResultMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultMessage(String code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public ResultMessage(String code, Object result) {
		this.code = code;
		this.result = result;
	}


	@Override
	public String toString() {
		return "ResultMessage [code=" + code + ", message=" + message + ", result="
				+ result + "]";
	}
}
