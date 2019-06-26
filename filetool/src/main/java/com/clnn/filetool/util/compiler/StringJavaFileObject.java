package com.clnn.filetool.util.compiler;

import lombok.Data;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

@Data
public class StringJavaFileObject  extends SimpleJavaFileObject {
    //等待编译的源码字段
    private String contents;
    public StringJavaFileObject(String fullClassName, String contents) {
        super(URI.create("string:///"+fullClassName.replaceAll("\\.","/")+Kind.SOURCE.extension),Kind.SOURCE);
        this.contents = contents;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}
