package com.saas.utitly;

import com.saas.model.domain.RequestEvent;
import com.saas.model.domain.Status;

public class StatusUtil {

    public static void successfulUserCreation(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("User Creation")
                .statusCode(200)
                .status("User account is created")
                .color("Green")
                .build());
    }

    public static void failedUserCreation(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("User Creation")
                .statusCode(400)
                .status("User already exist")
                .color("Red")
                .build());
    }

    public static void successfulUserUpdate(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("User Password")
                .statusCode(200)
                .status("User's password successfully updated")
                .color("Green")
                .build());
    }

    public static void failedUserUpdate(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("User Password")
                .statusCode(400)
                .status("User's password failed to update")
                .color("Red")
                .build());
    }

    public static void invalidActionName(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("Action")
                .statusCode(400)
                .status("Invalid action name")
                .color("Red")
                .build());
    }

    public static void invalidEventName(RequestEvent requestEvent){
        requestEvent.getStatusMap().add(Status.builder()
                .workflow("Event")
                .statusCode(400)
                .status("Invalid event name")
                .color("Red")
                .build());
    }
}
