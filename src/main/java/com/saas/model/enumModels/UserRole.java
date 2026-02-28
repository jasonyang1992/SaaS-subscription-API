package com.saas.model.enumModels;

import lombok.Getter;

@Getter
public enum UserRole {

    admin("admin"),
    customer("customer");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString(){
        return role;
    }
}
