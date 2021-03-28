//package com.webrtc.signallingserver.controller;
//
//import com.webrtc.signallingserver.model.GenericResponse;
//import com.webrtc.signallingserver.model.RtcUser;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//public class UserController {
//
//    @PostMapping("/register")
//    public ResponseEntity<GenericResponse<String>> registerUser(@RequestBody @Valid RtcUser user){return null;}
//
//    @PostMapping("/login")
//    @SendToUser
//    public  ResponseEntity<GenericResponse<String>> login(){}
//}
