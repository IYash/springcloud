package com.clnn.elastic.entity;

import lombok.Data;

@Data
public class UserEntity {
    private String userName;
    private int age;
    private String gender;

    public UserEntity() {
    }

    public UserEntity(String userName, int age, String gender) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
    }
}
