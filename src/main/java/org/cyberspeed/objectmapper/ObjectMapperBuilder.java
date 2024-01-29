package org.cyberspeed.objectmapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object mapper to parse json file
 */
public class ObjectMapperBuilder {
    private static volatile ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if(null == objectMapper){
            synchronized (ObjectMapperBuilder.class) {
                if(null == objectMapper) {
                    objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
                }
            }
        }
        return objectMapper;
    }
}