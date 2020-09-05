package com.jimo.lilydb.java.util.common.granularity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.joda.time.DateTimeZone;

import java.io.IOException;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 22:06
 */
public class PeriodGranularity extends Granularity implements JsonSerializable {
    @Override
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }

    @Override
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider,
                                  TypeSerializer typeSerializer) throws IOException {

    }

    @JsonProperty("timeZone")
    public DateTimeZone getTimeZone() {
        return null;
    }
}
