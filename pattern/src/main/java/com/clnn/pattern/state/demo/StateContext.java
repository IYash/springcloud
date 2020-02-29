package com.clnn.pattern.state.demo;

import lombok.Data;

@Data
public class StateContext {

    private State state;

    public void push(){
        state.push(this);
        System.out.println("push:"+state.getColor());
    }
    public void pull(){
        state.pull(this);
        System.out.println("pull:"+state.getColor());
    }
}
