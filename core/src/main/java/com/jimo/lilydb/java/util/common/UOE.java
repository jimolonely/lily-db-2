package com.jimo.lilydb.java.util.common;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 21:58
 */
public class UOE extends UnsupportedOperationException {
    public UOE(String formatText, Object... arguments) {
        super(StringUtils.nonStrictFormat(formatText, arguments));
    }
}
