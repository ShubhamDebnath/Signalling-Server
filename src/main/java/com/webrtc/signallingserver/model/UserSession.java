//package com.webrtc.signallingserver.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.kurento.client.IceCandidate;
//import org.kurento.client.WebRtcEndpoint;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserSession {
//
//    private String username;
//
//    private WebRtcEndpoint webRtcEndpoint;
//
//    private String sdpOffer;
//
//    private String callingTo;
//
//    private String callingFrom;
//
//    private final List<IceCandidate> candidateList = new ArrayList<IceCandidate>();
//
//    public void setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
//        this.webRtcEndpoint = webRtcEndpoint;
//
//        for (IceCandidate e : candidateList) {
//            this.webRtcEndpoint.addIceCandidate(e);
//        }
//        this.candidateList.clear();
//    }
//
//    public void addCandidate(IceCandidate candidate) {
//        if (this.webRtcEndpoint != null) {
//            this.webRtcEndpoint.addIceCandidate(candidate);
//        } else {
//            candidateList.add(candidate);
//        }
//    }
//
//    public void clear() {
//        this.webRtcEndpoint = null;
//        this.candidateList.clear();
//    }
//
//}
