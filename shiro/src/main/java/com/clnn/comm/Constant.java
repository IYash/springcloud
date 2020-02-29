package com.clnn.comm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant {

    public static final String BASE;
    private static final String CUSTOM;

    static{
        CUSTOM = ProUtil.getProp("redisPrefix");
        BASE = CUSTOM + "_base";
    }

}
