package com.clnn.filetool;

import com.clnn.filetool.domain.MapperGenerator;
import com.clnn.filetool.entity.beanDesc.*;
import com.clnn.filetool.util.EntityConstant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class PartTest {

    public CustomImport buildCustomImport(){
        CustomImport ci = new CustomImport();
        ci.setQualifiedName("");
        return ci;
    }
    public CustomParameterType buildCustomParametType(){
        CustomParameterType cpt = new CustomParameterType();
        cpt.setClz("");
        cpt.setParameterName("");
        return cpt;
    }
    public CustomParameterType buildPrimaryCustomParametType(){
        CustomParameterType cpt = new CustomParameterType();
        cpt.setInprimary(EntityConstant.CustomPrimary.INT);
        cpt.setParameterName("");
        return cpt;
    }
    public CustomClz buildCustomClz(){
        CustomClz cc = new CustomClz();
        cc.setClz("PkonlyMapper");
        cc.setHEAD("class");
        cc.setVisibility(EntityConstant.CustomVisibility.PUBLIC);
        return cc;
    }
    public CustomException buildCustomException(){
        CustomException exception = new CustomException();
        exception.setException(new RuntimeException());
        return exception;
    }
    public CustomMethod buildCustomMethod(){
        CustomParameterType cpt1= buildCustomParametType();
        CustomParameterType cpt2 = buildCustomParametType();
        ArrayList cps = new ArrayList();
        cps.add(cpt1);cps.add(cpt2);
        return CustomMethod.constructBuilder().buildMethodName("ohYho")
                .buildCustomParameterType(buildCustomParametType())
                .buildCustomParameterTypeList(cps)
                .buildCustomVisibility(EntityConstant.CustomVisibility.PUBLIC)
                .buildCustomException(Arrays.asList(buildCustomException())).build();

    }
    @Test
    public void testMethod(){
        CustomMethod cm  = buildCustomMethod();
        System.out.println(cm.buildMethodInfo());
    }
    @Test
    public void testFile() throws Exception{
        MapperGenerator jg = new MapperGenerator();
        jg.setCustomClz(buildCustomClz());
        CustomImport ci1=buildCustomImport();
        CustomImport ci2=buildCustomImport();
        ArrayList ls = new ArrayList();
        ls.add(ci1);ls.add(ci2);
        jg.setCustomImports(ls);
        ArrayList ms = new ArrayList();
        CustomMethod cm1 =  buildCustomMethod();
        CustomMethod cm2 = buildCustomMethod();
        ms.add(cm1);ms.add(cm2);
        jg.setCustomMethods(ms);
        jg.setTargetPath("E:\\PkonlyMapper.java");
        jg.generateBean();
    }
}
