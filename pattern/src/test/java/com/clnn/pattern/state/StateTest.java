package com.clnn.pattern.state;

import com.clnn.pattern.state.demo.BlackState;
import com.clnn.pattern.state.demo.StateContext;
import org.junit.Test;

public class StateTest {

    @Test
    public void testMigrate(){
        StateContext context = new StateContext();
        context.setState(new BlackState());
        context.push();
        context.push();
        context.push();
        context.push();
        context.push();
        context.push();
    }
}
