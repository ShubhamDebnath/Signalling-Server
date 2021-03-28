package com.webrtc.signallingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SignallingServerApplication {

//	@Bean
//	public KurentoClient kurentoClient() {
//		return KurentoClient.create();
//	}

    public static void main(String[] args) {
        SpringApplication.run(SignallingServerApplication.class, args);
    }

}
