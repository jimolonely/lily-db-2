package com.jimo.lilydb.java.util.common;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 22:15
 */
public class Numbers {

    public static boolean parseBoolean(Object val) {
        if (val instanceof String) {
            return Boolean.parseBoolean((String) val);
        } else if (val instanceof Boolean) {
            return (boolean) val;
        } else {
            if (val == null) {
                throw new NullPointerException("Input is null");
            } else {
                throw new ISE("unknown type: [%s]", val.getClass());
            }
        }
    }


}
