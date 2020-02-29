package com.clnn.pattern.state.demo;

public class RedState extends State{
    @Override
    public void push(StateContext context) {
        context.setState(new BlueState());
    }

    @Override
    public void pull(StateContext context) {
        context.setState(new BlackState());
    }

    @Override
    public String getColor() {
        return "RED";
    }
}
