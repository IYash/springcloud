package com.clnn.filetool.util.compiler;


import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
/**
 * 自定义JavaFileManage来控制编译之后字节码位置
 */
public class StringJavaFileManager extends ForwardingJavaFileManager {
    public StringJavaFileManager(JavaFileManager javaFileManager) {
        super(javaFileManager);
    }
    //获取输出的文件对象，它表示给定位置处指定类型的指定类
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        ByteJavaFileObject javaFileObject = new ByteJavaFileObject(className,kind);
        ByteJavaFileObjectLocal.addJavaFileObjectToMap(className,javaFileObject);
        return javaFileObject;

    }
}
