//package com.webrtc.signallingserver.service;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.webrtc.signallingserver.model.*;
//import lombok.extern.slf4j.Slf4j;
//import org.kurento.client.EventListener;
//import org.kurento.client.IceCandidate;
//import org.kurento.client.IceCandidateFoundEvent;
//import org.kurento.client.KurentoClient;
//import org.kurento.jsonrpc.JsonUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Slf4j
////@Service
//public class CallService {
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private UserList userList;
//
//    @Autowired
//    private PipelinesList pipelinesList;
//
//    @Autowired
//    private KurentoClient kurento;
//
//    private static final Gson gson = new GsonBuilder().create();
//
//    public void handleRequest(SimpleMessage message){
//        JsonObject jsonMessage = gson.fromJson(message.getText(), JsonObject.class);
////        log.info("Recieved : {}", message.getText());
//
//        switch (CommunicationType.valueOf(jsonMessage.get("type").getAsString())){
//            case CALL: {
//                call(message.getFromUser(), message.getToUser(), jsonMessage);
//                break;
//            }
//            case INCOMING_CALL_RESPONSE:{
//                incomingCallResponse(message.getFromUser(), message.getToUser(), jsonMessage);
//                break;
//            }
//            case ON_ICE_CANDIDATE: {
//                UserSession user = userList.getUser(message.getFromUser());
//                onIceCandidate(user, jsonMessage);
//                break;
//            }
//            case STOP:{
//                stop(message.getFromUser(), message.getToUser());
//                break;
//            }
//
//            default:{
//                throw  new RuntimeException("Incorrect communication type");
//            }
//        }
//
//    }
//
//    public void call(String fromUser, String toUser, JsonObject jsonMessage){
//        JsonObject response = new JsonObject();
//
//        UserSession caller = userList.getUser(fromUser);
//        UserSession receiver = userList.getUser(toUser);
//
//        caller.setCallingTo(toUser);
//        receiver.setCallingFrom(fromUser);
//
//        caller.setSdpOffer(jsonMessage.getAsJsonPrimitive("sdpOffer").getAsString());
//
//        response.addProperty("type", CommunicationType.INCOMING_CALL.toString());
//        response.addProperty("fromUser", fromUser);
//
//        template.convertAndSendToUser(toUser, "/queue/incoming-call", response.toString());
//    }
//
//    private void incomingCallResponse(String fromUser, String toUser, JsonObject jsonMessage) {
//        String callResponse = jsonMessage.get("callResponse").getAsString();
//        final UserSession caller = userList.getUser(fromUser);
//        final UserSession callee = userList.getUser(toUser);
//
//        log.info("Recieved incoming call: {}", jsonMessage);
//
//        if ("accept".equals(callResponse)) {
//            log.info("Accepted call from '{}' to '{}'", fromUser, toUser);
//
//            CallMediaPipeline pipeline = null;
//            try {
//                pipeline = new CallMediaPipeline(kurento);
//                pipelinesList.addPipeline(fromUser, pipeline);
//                pipelinesList.addPipeline(toUser, pipeline);
//
//                String calleeSdpOffer = jsonMessage.get("sdpOffer").getAsString();
//                callee.setWebRtcEndpoint(pipeline.getCalleeWebRtcEP());
//                pipeline.getCalleeWebRtcEP().addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
//
//                    @Override
//                    public void onEvent(IceCandidateFoundEvent event) {
//                        JsonObject response = new JsonObject();
//                        response.addProperty("type", "iceCandidate");
//                        response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
//
//                        template.convertAndSendToUser(toUser, "/queue/incoming-call", response.toString());
//                    }
//                });
//
//                String calleeSdpAnswer = pipeline.generateSdpAnswerForCallee(calleeSdpOffer);
//                String callerSdpOffer = caller.getSdpOffer();
//                caller.setWebRtcEndpoint(pipeline.getCallerWebRtcEP());
//                pipeline.getCallerWebRtcEP().addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
//
//                    @Override
//                    public void onEvent(IceCandidateFoundEvent event) {
//                        JsonObject response = new JsonObject();
//                        response.addProperty("type", "iceCandidate");
//                        response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
//
//                        template.convertAndSendToUser(fromUser, "/queue/incoming-call", response.toString());
//                    }
//                });
//
//                String callerSdpAnswer = pipeline.generateSdpAnswerForCaller(callerSdpOffer);
//
//                JsonObject startCommunication = new JsonObject();
//                startCommunication.addProperty("type", "startCommunication");
//                startCommunication.addProperty("sdpAnswer", calleeSdpAnswer);
//
//                template.convertAndSendToUser(toUser, "/queue/incoming-call", startCommunication.toString());
//
//                pipeline.getCalleeWebRtcEP().gatherCandidates();
//
//                JsonObject response = new JsonObject();
//                response.addProperty("type", "callResponse");
//                response.addProperty("response", "accepted");
//                response.addProperty("sdpAnswer", callerSdpAnswer);
//
//                template.convertAndSendToUser(fromUser, "/queue/incoming-call", response.toString());
//
//                pipeline.getCallerWebRtcEP().gatherCandidates();
//
//            }catch (Throwable t) {
//                log.error(t.getMessage(), t);
//
//                if (pipeline != null) {
//                    pipeline.release();
//                }
//
//                pipelinesList.removePipeline(fromUser);
//                pipelinesList.removePipeline(toUser);
//
//                JsonObject response = new JsonObject();
//                response.addProperty("type", "callResponse");
//                response.addProperty("response", "rejected");
//                template.convertAndSendToUser(fromUser, "/queue/incoming-call", response.toString());
//
//                response = new JsonObject();
//                response.addProperty("type", CommunicationType.STOP.toString());
//                template.convertAndSendToUser(toUser, "/queue/incoming-call", response.toString());
//            }
//        }
//        else {
//            JsonObject response = new JsonObject();
//            response.addProperty("type", "callResponse");
//            response.addProperty("response", "rejected");
//            template.convertAndSendToUser(fromUser, "/queue/incoming-call", response.toString());
//        }
//
//    }
//
//    public void stop(String fromUser, String toUser){
//        JsonObject response = new JsonObject();
//
//        pipelinesList.removePipeline(fromUser);
//        pipelinesList.removePipeline(toUser);
//
//        userList.getUser(fromUser).clear();
//        userList.getUser(toUser).clear();
//
//        response.addProperty("type", CommunicationType.STOP.toString());
//
//        template.convertAndSendToUser(toUser, "/queue/incoming-call", response.toString());
////        template.convertAndSendToUser(fromUser, "/queue/incoming-call", response.toString());
//    }
//
//    public void onIceCandidate(UserSession user, JsonObject jsonMessage){
//        JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();
//        if (user != null) {
//            IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
//                    candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
//            user.addCandidate(cand);
//        }
//    }
//}
