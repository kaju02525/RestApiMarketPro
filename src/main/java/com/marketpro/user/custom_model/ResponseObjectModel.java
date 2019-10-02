package com.marketpro.user.custom_model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString()
public class ResponseObjectModel {
    private boolean status;
    private String message;
    private Object data;

    public ResponseObjectModel(boolean status, String message,Object data) {
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
