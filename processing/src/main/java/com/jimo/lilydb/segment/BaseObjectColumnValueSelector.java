package com.jimo.lilydb.segment;

import com.jimo.lilydb.guice.ExtensionPoint;
import com.sun.istack.internal.Nullable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:16
 */
@ExtensionPoint
public interface BaseObjectColumnValueSelector<T> {

    @Nullable
    T getObject();

    Class<? extends T> classObject();
}
