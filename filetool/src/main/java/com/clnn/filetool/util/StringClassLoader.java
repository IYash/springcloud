package com.clnn.filetool.util;

import com.clnn.filetool.util.compiler.ByteJavaFileObject;
import com.clnn.filetool.util.compiler.ByteJavaFileObjectLocal;

public class StringClassLoader extends ClassLoader {
    @Override
    public Class<?> findClass(String fullClassName) throws ClassNotFoundException{
        ByteJavaFileObject fileObject = ByteJavaFileObjectLocal.fetchJavaFileObjet(fullClassName);
        if(fileObject != null){
            byte[] bytes = fileObject.getCompilerByte();
            return defineClass(fullClassName,bytes,0,bytes.length);
        }
        try{
            return ClassLoader.getSystemClassLoader().loadClass(fullClassName);
        }catch (Exception e){
            return super.findClass(fullClassName);
        }
    }
}
