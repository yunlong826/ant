package com.ant.yun.core;

import com.ant.yun.util.Assert;
import com.ant.yun.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 14:28
 */
public class Constants {

    private final String className;
    private final Map<String, Object> fieldCache = new HashMap();

    public Constants(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        this.className = clazz.getName();
        Field[] fields = clazz.getFields();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (ReflectionUtils.isPublicStaticFinal(field)) {
                String name = field.getName();

                try {
                    Object value = field.get((Object)null);
                    this.fieldCache.put(name, value);
                } catch (IllegalAccessException var9) {
                }
            }
        }
    }
    public Number asNumber(String code) throws Constants.ConstantException {
        Object obj = this.asObject(code);
        if (!(obj instanceof Number)) {
            throw new Constants.ConstantException(this.className, code, "not a Number");
        } else {
            return (Number)obj;
        }
    }
    public Object asObject(String code) throws Constants.ConstantException {
        Assert.notNull(code, "Code must not be null");
        String codeToUse = code.toUpperCase(Locale.ENGLISH);
        Object val = this.fieldCache.get(codeToUse);
        if (val == null) {
            throw new Constants.ConstantException(this.className, codeToUse, "not found");
        } else {
            return val;
        }
    }

    public static class ConstantException extends IllegalArgumentException {
        public ConstantException(String className, String field, String message) {
            super("Field '" + field + "' " + message + " in class [" + className + "]");
        }

        public ConstantException(String className, String namePrefix, Object value) {
            super("No '" + namePrefix + "' field with value '" + value + "' found in class [" + className + "]");
        }
    }
}
