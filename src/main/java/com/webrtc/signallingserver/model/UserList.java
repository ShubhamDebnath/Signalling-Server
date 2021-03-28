package com.webrtc.signallingserver.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserList {

    @Autowired
    private SimpMessagingTemplate template;

    private final Set<String> users = new HashSet<>();

    public void addUser(String username) {
//        users.put(username, UserSession.builder().username(username).build());
        synchronized (this) {
            users.add(username);
            notifyUpdate();
        }
    }

    public void removeUser(String username) {
        synchronized (this) {
            users.remove(username);
            notifyUpdate();
        }
    }

//    public UserSession getUser(String username){
//        if(!users.containsKey(username)){
//            throw new RuntimeException(String.format("User %s not found", username));
//        }
//
//        return users.get(username);
//    }

    public void notifyUpdate() {
        template.convertAndSend("/topic/online", users);
    }
}
