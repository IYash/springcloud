package com.clnn.filetool.util.compiler;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过ThreadLocal在线程内共享
 */
public class ByteJavaFileObjectLocal {

    private static Map<String,ByteJavaFileObject> basic = new ConcurrentHashMap<>();
    private static final ThreadLocal<Map<String, ByteJavaFileObject>> javaFileObjectMap = new ThreadLocal<>();

   public static void addJavaFileObjectToMap(String className,ByteJavaFileObject byteJavaFileObject){
       basic.put(className,byteJavaFileObject);
       javaFileObjectMap.set(basic);
   }
   public static ByteJavaFileObject fetchJavaFileObjet(String className){
       return javaFileObjectMap.get().get(className);
   }

}
