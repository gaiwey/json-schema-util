package com.gaiwey.jsv.jsonvalidatorutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

/**
 * Class生产JSON Schema工具类
 * @author gaiwey
 */
public class GenerateJsonSchemaUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private GenerateJsonSchemaUtil(){
    }

    /**
     * 讲CLass生产对应的JSON SCHEMA STRING
     * @param clazz
     * @param isPretty
     * @return
     * @throws JsonProcessingException
     */
    public static String classToJsonSchema(Class clazz, boolean isPretty) throws JsonProcessingException {
        JsonSchema jsonSchema = mapper.generateJsonSchema(clazz);
        if (isPretty){
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
        }
        return mapper.writeValueAsString(jsonSchema);
    }

}
