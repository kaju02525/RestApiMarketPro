package com.marketpro.user.custom_model;

import lombok.Getter;

@Getter
public class ResponseModel {
    private boolean status;
    private String message;

    public ResponseModel(boolean status, String message) {
        this.setStatus(status);
        this.setMessage(message);
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
}
