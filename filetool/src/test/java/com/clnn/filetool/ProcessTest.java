package com.clnn.filetool;

import com.clnn.filetool.domain.*;
import org.junit.Before;
import org.junit.Test;


public class ProcessTest {
    BaseSupport support ;
    @Before
    public void before(){
    }

    @Test
    public void testConstructMapper() throws Exception{
        support  =new CustomMapperSupport("E:\\PkonlyMapper.xml");
        support.parseMapper();
        MapperGenerator generator = new MapperGenerator();
        generator.setTargetPath("E:\\PkonlyMapper.java");
        generator.setCustomClz(support.buildCustomClz());
        generator.setCustomMethods(support.buildCustomMethods());
        generator.setCustomImports(support.buildCustomImports());
        generator.generateBean();
    }
    @Test
    public void testConstructEntity() throws  Exception{
        support  =new CustomEntitySupport("E:\\PkonlyMapper.xml");
        support.parseMapper();
        EntityGenerator generator = new EntityGenerator();
        generator.setTargetPath("E:\\Pkonly.java");
        generator.setCustomClz(support.buildCustomClz());
        generator.setCustomImports(support.buildCustomImports());
        generator.setCustomProperties(support.buildCustomProperties());
        generator.generateBean();
    }
    @Test
    public void testBaseBean() throws Exception{
        support  =new CustomEntitySupport("E:\\PkonlyMapper.xml");
        support.parseMapper();
        BaseBeanGenerator generator = new BaseBeanGenerator();
        generator.setTargetPath("E:\\Pkonly.java");
        generator.setCustomClz(support.buildCustomClz());
        generator.setCustomImports(support.buildCustomImports());
        generator.setPropertyWithMethods(support.buildPropertyWithMethods());
        generator.generateBean();
    }
}
