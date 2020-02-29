package com.clnn.pattern.state.faced;

import lombok.Data;

@Data
public class StateContext {

    private State state;

    public void push(State state){
        state.push(this);
    }
    public void pull(State state){
        state.pull(this);
    }
    public String buildStateInfo(){
        return state.buildStateInfo();
    }
}
