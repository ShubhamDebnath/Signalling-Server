package com.webrtc.signallingserver.service;

import com.webrtc.signallingserver.model.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class EventListeners {

    @Autowired
    private UserList userList;

//    @EventListener
//    public void handleSessionConnectedEvent(SessionConnectedEvent event){
//        userList.addUser(event.getUser().getName());
//    }

    @EventListener
    public void handleSessionDisconnectedEvent(SessionDisconnectEvent event){
        userList.removeUser(event.getUser().getName());
    }
}
