package com.jimo.lilydb.query.spec;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 7:03
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface QuerySegmentSpec {
    // TODO
}
