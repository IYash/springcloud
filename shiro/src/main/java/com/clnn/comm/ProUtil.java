package com.clnn.comm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 为了读取特定的环境参数
 * 1.用于读取自定义的配置文件
 * 2.使用架构的参数的注入方式
 */
public class ProUtil {
    private static Properties properties;
    static {
        InputStream in = null;
        try {
            properties = new Properties();
            //获取文件名
            String parseFilename = parseFilename();
            in = ProUtil.class.getResourceAsStream(parseFilename);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String parseFilename() {
        File[] files = new File(ProUtil.class.getResource("/").getPath()).listFiles();
        File matchFile = Arrays.asList(files).stream().filter(file-> file.isFile()).filter(file -> file.getName().startsWith("application-")).collect(Collectors.toList()).get(0);
        System.out.println(matchFile.getName());
        return "/"+matchFile.getName();
    }

    public static  String  getProp(String key){
        return properties.getProperty(key);
    }
}
