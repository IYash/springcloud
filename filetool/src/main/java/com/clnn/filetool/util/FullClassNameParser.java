package com.clnn.filetool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取类的全名称
 */
public class FullClassNameParser {
    /**
     * 获取类的全名称
     * @param sourceCode
     * @return
     */
    public static String getFullClassName(String sourceCode) {
        String className = "";
        Pattern pattern = Pattern.compile("package\\s+\\S+\\s*;");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className = matcher.group().replaceFirst("package", "").replace(";", "").trim() + ".";
        }
        pattern = Pattern.compile("class\\s+\\S+\\{");
        matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className += matcher.group().replaceFirst("class", "").replace("{", "").trim();
        }
        return className;
    }
}
