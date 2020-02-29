package com.clnn.pattern.state.demo;

/**
 * 1.一个对象的行为取决于它的状态
 * 2.代码中包含大量于对象状态有关的条件语句
 */
public abstract  class State {//状态black->red->blue->green->black

    public abstract void push(StateContext context);

    public abstract void pull(StateContext context);

    public abstract String getColor();
}
