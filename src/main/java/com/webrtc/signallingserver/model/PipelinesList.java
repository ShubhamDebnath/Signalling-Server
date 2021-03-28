//package com.webrtc.signallingserver.model;
//
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class PipelinesList {
//
//    private final Map<String , CallMediaPipeline> pipelines = new HashMap<>();
//
//    public void addPipeline(String username, CallMediaPipeline pipeline){
//        pipelines.put(username, pipeline);
//    }
//
//    public CallMediaPipeline getPipeline(String username){
//        return pipelines.get(username);
//    }
//
//    public void removePipeline(String username){
//        if(!pipelines.containsKey(username)){
//            throw new RuntimeException(String.format("Pipeline not found for User: %s", username));
//        }
//    }
//}
