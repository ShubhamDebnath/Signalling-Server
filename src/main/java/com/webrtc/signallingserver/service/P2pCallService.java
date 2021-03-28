package com.webrtc.signallingserver.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webrtc.signallingserver.model.CommunicationType;
import com.webrtc.signallingserver.model.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class P2pCallService {

    @Autowired
    private SimpMessagingTemplate template;

    private final Gson gson = new Gson();

    public void forwardRequest(SimpleMessage message){

        template.convertAndSendToUser(message.getToUser(), "/queue/incoming-call", gson.toJson(message));
    }
}
