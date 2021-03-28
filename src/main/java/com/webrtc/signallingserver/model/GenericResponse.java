package com.webrtc.signallingserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

    @Builder.Default
    private Status status = Status.SUCCESS;

    private String message;

    private T payload = null;

    public enum Status{
        SUCCESS, ERROR
    }
}
