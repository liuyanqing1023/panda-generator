package io.yan.common;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库字段类型映射Java类型
 */
public class TypeConversion {

        public static Map<String, String> type = new LinkedHashMap<>();
        static {
            type.put("tinyint", "Integer");
            type.put("smallint", "Integer");
            type.put("mediumint", "Integer");
            type.put("int", "Integer");
            type.put("integer", "Integer");
            type.put("bigint", "Long");
            type.put("float", "Float");
            type.put("double", "Double");
            type.put("decimal", "BigDecimal");
            type.put("bit", "Boolean");
            type.put("char", "String");
            type.put("varchar", "String");
            type.put("tinytext", "String");
            type.put("text", "String");
            type.put("mediumtext", "String");
            type.put("longtext", "String");
            type.put("date", "Date");
            type.put("datetime", "Date");
            type.put("time", "Date");
            type.put("timestamp", "Date");

    }

}
