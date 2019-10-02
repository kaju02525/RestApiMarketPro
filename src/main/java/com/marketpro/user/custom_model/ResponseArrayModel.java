package com.marketpro.user.custom_model;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseArrayModel {
    private boolean status;
    private String message;
    private List<?> data;

    public ResponseArrayModel(boolean status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

   public ResponseArrayModel(boolean status, String message, List<?> data) {
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

public List<?> getData() {
	return data;
}

public void setData(List<?> data) {
	this.data = data;
}

}
