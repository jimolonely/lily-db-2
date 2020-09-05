package com.jimo.lilydb.query.filter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jimo.lilydb.java.util.common.Cacheable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/6 7:00
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface DimFilter extends Cacheable {


}
