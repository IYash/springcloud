package com.clnn.filetool.util.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class ByteJavaFileObject extends SimpleJavaFileObject {
    //存放编译后的字节码
    private ByteArrayOutputStream outputStream;
    protected ByteJavaFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), kind);
    }
    //StringFileManager编译之后的字节码输出会调用该方法
    @Override
    public OutputStream openOutputStream(){
        outputStream = new ByteArrayOutputStream();
        return outputStream;
    }
    //在类加载器加载的时候需要用到
    public byte[] getCompilerByte(){
        return outputStream.toByteArray();
    }
}
