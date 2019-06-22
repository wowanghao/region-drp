package io.terminus.common.data.transfer.demo.exporter.samples;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author yushuo
 */
public class NullSerializer extends JsonSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString("-");
    }
}
