package com.clnn.pattern.state.demo;

public class BlueState extends State {
    @Override
    public void push(StateContext context) {
        context.setState(new GreenState());
    }

    @Override
    public void pull(StateContext context) {
        context.setState(new RedState());
    }

    @Override
    public String getColor() {
        return "BLUE";
    }
}
