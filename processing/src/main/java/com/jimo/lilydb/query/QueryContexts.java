package com.jimo.lilydb.query;

import com.jimo.lilydb.guice.PublicApi;
import com.jimo.lilydb.java.util.common.Numbers;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 7:12
 */
@PublicApi
public class QueryContexts {

    public static final String PRIORITY_KEY = "priority";
    public static final String LANE_KEY = "lane";


    static <T> boolean parseBoolean(Query<T> query, String key, boolean defaultValue) {
        final Object val = query.getContextValue(key);
        return val == null ? defaultValue : Numbers.parseBoolean(val);
    }

}
