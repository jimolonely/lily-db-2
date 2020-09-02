package com.jimo.lilydb.java.util.common;

import java.util.IllegalFormatException;
import java.util.Locale;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 21:59
 */
public class StringUtils {

    public static String nonStrictFormat(String msg, Object... formatArgs) {
        if (formatArgs == null || formatArgs.length == 0) {
            return msg;
        }
        try {
            return String.format(Locale.ENGLISH, msg, formatArgs);
        } catch (IllegalFormatException e) {
            final StringBuilder bob = new StringBuilder(msg);
            for (Object formatArg : formatArgs) {
                bob.append("; ").append(formatArg);
            }
            return bob.toString();
        }
    }
}
