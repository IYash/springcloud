package com.clnn.filetool.util;

import com.alibaba.druid.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于将jdbc类型映射成java类型,作为一个容器使用
 */

public enum JdbcTypeMapping {
    CHAR("java.lang.String"),VARCHAR("java.lang.String"),LONGVARCHAR("java.lang.String"),
    NUMERIC("java.math.BigDecimal"),
    DECIMAL("java.math.BigDecimal"),
    BIT("boolean"),BOOLEAN("boolean"),TINYINT("byte"),SMALLINT("short"),
    INTEGER("int"),BIGINT("long"),REAL("float"),FLOAT("float"),
    DOUBLE("double"),BINARY("byte[]"),VARBINARY("byte[]"),LONGVARBINARY("byte[]"),
    TIME("java.sql.Time"),TIMESTAMP("java.sql.Timestamp"),
    //DATE java.sql.Date修正为java.util.Date
    DATE("java.util.Date"),
    CLOB("Clob"),BLOB("Blog"),ARRAY("Array"),DISTINCT("mapping of underlying type"),
    STRUCT("Struct"),REF("Ref"),DATALINK("java.net.URL[color=red][/color]"),DEFAULT("")
    ;

    private String javaType;
    private static Map<String,JdbcTypeMapping> mappings = new ConcurrentHashMap<>();
    JdbcTypeMapping(String javaType) {
        this.javaType = javaType;
    }
    private String getJavaType(){return javaType;}

    public  static String mappingJavaType(String jdbcType){
        JdbcTypeMapping jdbcTypeM = mappings.get(jdbcType);
            if(null == jdbcTypeM){
                for(JdbcTypeMapping m:JdbcTypeMapping.values()){
                    if(StringUtils.equals(m.name(),jdbcType)){
                        mappings.put(jdbcType,m);
                       jdbcTypeM = m;
                    }
                }
            }
            return jdbcTypeM == null ? DEFAULT.getJavaType() :jdbcTypeM.getJavaType();
    }
}
