package com.jimo.lilydb.java.util.common;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 22:18
 */
public class ISE extends IllegalStateException {

    public ISE(String formatText, Object... args) {
        super(StringUtils.nonStrictFormat(formatText, args));
    }

    public ISE(Throwable cause, String formatText, Object... args) {
        super(StringUtils.nonStrictFormat(formatText, args), cause);
    }
}
