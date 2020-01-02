package com.web.websocket.controller;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.web.websocket.model.ChatMessage;

@Component
public class WebSocketEventListener {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@EventListener
	public void handlewebSocketDisconnectListener(SessionDisconnectEvent event) {
		  StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//		 headers.getSessionId();
//		 headers.getSessionAttributes();
//		 headers.getPrincipal();
		String username=(String) headerAccessor.getSessionAttributes().get("username");
		if(username !=null) {
			System.out.println("Disconnected User"+username);
		    ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
		}
		
	}
	

	 

}
