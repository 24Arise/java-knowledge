package com.org.arise.JsonDeserialize.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * <p>
 * Boolean Deserializer Class
 * </p>
 * >
 *
 * @author 24Arise 2020/12/06 19:07
 */
@Service
public class OptimizedBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        if ("0".equals(text)) {
            return false;
        }
        return true;
    }

}
