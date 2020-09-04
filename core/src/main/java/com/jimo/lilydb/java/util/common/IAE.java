package com.jimo.lilydb.java.util.common;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/4 9:23
 */
public class IAE extends IllegalArgumentException {
    public IAE(String formatText, Object... arguments) {
        super(StringUtils.nonStrictFormat(formatText, arguments));
    }

    public IAE(Throwable cause, String formatText, Object... arguments) {
        super(StringUtils.nonStrictFormat(formatText, arguments), cause);
    }
}
