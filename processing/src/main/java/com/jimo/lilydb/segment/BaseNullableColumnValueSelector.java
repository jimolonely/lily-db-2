package com.jimo.lilydb.segment;

import com.jimo.lilydb.guice.PublicApi;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:13
 */
@PublicApi
public interface BaseNullableColumnValueSelector {
    /**
     * 原始类型返回true
     */
    boolean isNull();
}
