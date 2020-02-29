package com.clnn.pattern.state.demo;

public class BlackState extends State{
    @Override
    public void push(StateContext context) {
        context.setState(new RedState());
    }

    @Override
    public void pull(StateContext context) {
        context.setState(new GreenState());
    }

    @Override
    public String getColor() {
        return "BLACK";
    }
}
