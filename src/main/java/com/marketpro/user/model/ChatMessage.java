package com.marketpro.user.model;

import lombok.Data;

@Data
public class ChatMessage {

	private String content;
	private String sender;
	private MessageType type;
	
public enum MessageType{
	CHAT,LEAVE,JOIN
}
}
