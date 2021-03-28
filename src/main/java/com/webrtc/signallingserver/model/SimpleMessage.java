package com.webrtc.signallingserver.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessage{

    private String fromUser;

    private String toUser;

    private String text;
}
