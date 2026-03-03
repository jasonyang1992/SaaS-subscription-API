package com.saas.model.enumModels;

import lombok.Getter;

@Getter
public enum SubscriptionType {

    Basic("Basic"),
    Pro("Pro"),
    Ultimate("Ultimate");

    private final String type;

    SubscriptionType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
