package com.jimo.lilydb.segment.column;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:07
 */
public interface NumericColumn extends BaseColumn {

    int length();

    long getLongSingleValueRow(int rowNum);

    @Override
    void close();
}
