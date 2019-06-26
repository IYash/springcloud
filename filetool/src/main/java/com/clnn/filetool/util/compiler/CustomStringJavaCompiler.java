package com.clnn.filetool.util.compiler;

import com.clnn.filetool.util.FullClassNameParser;
import com.clnn.filetool.util.StringClassLoader;
import lombok.Data;


import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * javaw文件在线编译
 * https://www.cnblogs.com/andysd/p/10081443.html
 */
@Data
public class CustomStringJavaCompiler {
    //类全名
    private String fullClassName;
    private String sourceCode;
    //获取java编译器
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //存放编译过程中输出的信息
    private DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
    //执行结果
    private String runResult;
    //编译耗时
    private long compilerTakeTime;
    //运行耗时
    private long runTakeTime;

    public CustomStringJavaCompiler(String sourceCode){
        this.sourceCode = sourceCode;
        this.fullClassName = FullClassNameParser.getFullClassName(sourceCode);
    }

    /**
     * 编译字符串源码
     * @return
     */
    public boolean compiler(){
        long startTime = System.currentTimeMillis();
        //标准的内容管理器，更换成自己的实现，覆盖部分方法
        StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(diagnosticCollector,null,null);
        JavaFileManager javaFileManager =new StringJavaFileManager(standardJavaFileManager);
        //构造源代码对象
        JavaFileObject javaFileObject = new StringJavaFileObject(fullClassName,sourceCode);
        //获取一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null,javaFileManager,diagnosticCollector,null,null, Arrays.asList(javaFileObject));
        //设置编译耗时
        compilerTakeTime = System.currentTimeMillis() - startTime;
        return task.call();
    }

    /**
     * 执行main方法，重定向system.out.print
     */
    public void runMainMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException, ClassNotFoundException {
        PrintStream out = System.out;
        try {
            long startTime = System.currentTimeMillis();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            //PrintStream printStream = new PringStream("/usr/local/tem.sql");输出到文件
            System.setOut(printStream);

            StringClassLoader scl = new StringClassLoader();
            Class<?> aClass = scl.findClass(fullClassName);
            Method main = aClass.getMethod("main", String[].class);
            Object[] pars = new Object[]{1};
            pars[0] = new String[]{};
            main.invoke(null, pars);//调用main方法
            //设置运行耗时
            runTakeTime = System.currentTimeMillis() - startTime;
            runResult = new String(outputStream.toByteArray(), "utf-8");
        }finally {
            //还原默认打印对象
            System.setOut(out);
        }

    }

    /**
     * 编译信息
     * @return
     */
    public String getCompilerMessage(){
        StringBuilder sb = new StringBuilder();
        diagnosticCollector.getDiagnostics().stream().forEach(diagnostic -> {
            sb.append(diagnostic.toString()).append("\r\n");
        });
        return sb.toString();
    }
    public void complierAndRun(String targetPath,String sourcePath,String methodName){
        try {

            System.out.println(System.getProperty("user.dir"));
            //动态编译
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            int status = javac.run(null, null, null, "-d", targetPath,sourcePath);
            if(status!=0){
                System.out.println("没有编译成功！");
            }

            //动态执行
            String clzName = sourcePath.substring(sourcePath.lastIndexOf("/")+1);
            String simpleName = clzName.substring(0,clzName.indexOf("."));
            Class clz = Class.forName(simpleName);//返回与带有给定字符串名的类 或接口相关联的 Class 对象。
            Object o = clz.newInstance();
            Method method = clz.getDeclaredMethod(methodName);//返回一个 Method 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明方法
            String result= (String)method.invoke(o);//静态方法第一个参数可为null,第二个参数为实际传参
            System.out.println(result);
        } catch (Exception e) {

        }
    }
}

