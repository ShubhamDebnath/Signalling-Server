package com.webrtc.signallingserver.controller;

import com.webrtc.signallingserver.model.SimpleMessage;
import com.webrtc.signallingserver.model.UserList;
import com.webrtc.signallingserver.service.P2pCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class SignalController {

//    public String processMessageFromClient(@Payload String message, Principal principal)

    @Autowired
    private SimpMessagingTemplate template;

//    @Autowired
//    private CallService callService;

    @Autowired
    private P2pCallService callService;

    @Autowired
    private UserList userList;

    @MessageMapping("/register")
    @SendToUser("/queue/register-reply")
    public String registerUser(@Payload  String message, Principal user){
        userList.addUser(user.getName());
        return user.getName();
    }


    @MessageMapping("/message")
    public void processMessageFromClient(@Payload SimpleMessage message, Principal user) throws Exception {
        log.info("Message received: {}", message);
//        message.setFromUser(user.getName());
        log.info("from : {}", user.getName());
        log.info("to : {}", message.getToUser());

        template.convertAndSendToUser(message.getToUser(), "/queue/reply", message);

    }

    @MessageMapping({"/call"})
    public void processCallRequest(@Payload SimpleMessage message, Principal user) throws  Exception{
//        message.setFromUser(user.getName());

//        callService.handleRequest(message);
        callService.forwardRequest(message);
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public String handleException(Throwable exception) {

        exception.printStackTrace();
        return exception.getMessage();
    }

    public void sendSomething() {
        ScheduledExecutorService schService = Executors.newScheduledThreadPool(10);
        schService.scheduleAtFixedRate(() -> {
            log.warn("Sending a message");
            template.convertAndSend("/queue/start", "a new message each time");
        }, 0,5, TimeUnit.SECONDS);

    }
}
