package com.clnn.filetool;

import org.junit.Test;

import java.io.File;

public class FilePathTest {
    @Test
    public void testPath(){
        System.out.println(System.getProperty("user.dir")+"-----------------------------1");//user.dir指定了当前的路径
        System.out.println(Class.class.getClass().getResource("/").getPath() +"------------------2");
    }
    @Test
    public void testMkdir(){
        String base = Class.class.getClass().getResource("/").getPath();
        String target = "com/clnn/create";
        File file = new File(base+target);
        if(!file.exists()) file.mkdirs();
    }
    @Test
    public void testCompile(){

    }
}
