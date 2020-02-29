package com.clnn.pattern.state.faced;

public abstract class State {
    public abstract void push(StateContext context);

    public abstract void pull(StateContext context);

    public abstract String buildStateInfo();
}
