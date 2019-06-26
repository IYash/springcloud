package com.clnn.filetool;

import com.clnn.filetool.util.compiler.CustomStringJavaCompiler;
import org.junit.Test;

/**
 * lombok的钩子使用有点异常
 */
public class CompilerTest {
    @Test
    public void testCompiler(){
        CustomStringJavaCompiler customStringJavaCompiler = new CustomStringJavaCompiler(sourceCode());
        boolean res = customStringJavaCompiler.compiler();
        if(res){
            System.out.println("编译成功");
            System.out.println("compilerTakeTime:"+ customStringJavaCompiler.getCompilerTakeTime());
            try{
                customStringJavaCompiler.runMainMethod();
                System.out.println("runTakeTime:"+customStringJavaCompiler.getRunTakeTime());
            }catch (Exception e){

            }
            System.out.println(customStringJavaCompiler.getRunResult());
            System.out.println("诊断信息:"+customStringJavaCompiler.getCompilerMessage());
        }else{
            System.out.println("编译失败");
            System.out.println(customStringJavaCompiler.getCompilerMessage());
        }
    }
    private String sourceCode(){
        String code =
                "public class HelloWorld{\n"+
            "   public static void main(String []args){\n"+
            "\t\t System.out.println(\"hello world!\");\n"+
            "}\n"+
         "}";
        System.out.println(code);
        return code;
    }
    @Test
    public void testCompilerAndRun(){
        CustomStringJavaCompiler customStringJavaCompiler = new CustomStringJavaCompiler(sourceCode());
        customStringJavaCompiler.complierAndRun(System.getProperty("user.dir")+"\\target\\classes","E:/test/AllTest.java","sayHello");
    }
}
