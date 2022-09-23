package com.gaiwey.jsv.jsonvalidatorutil;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/**
 * use schema validate json
 * @author gaiwey
 */
public class JsonSchemaUtil {

    private JsonSchemaUtil(){
    }

    /**
     * @param jsonStr 验证json字符串
     */
    public static JsonNode strToJsonNode(String jsonStr) {
        checkInputParamIsNull(jsonStr);

        JsonNode jsonNode = null;
        try {
            jsonNode = JsonLoader.fromString(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * @param jsonNode   json数据node
     * @param schemaNode jsonSchema约束node
     */
    private static boolean validate(JsonNode jsonNode, JsonNode schemaNode) {
        //fge验证json数据是否符合json schema约束规则
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (!report.isSuccess()) {
            // 校验失败打印原因
            printFailMessage(report);
            return Boolean.FALSE;
        }

        // 校验成功
        return Boolean.TRUE;
    }

    /**
     * @param json   json数据
     * @param schema jsonSchema数据
     */
    private static boolean validate(String json, String schema) {
        checkInputParamIsNull(json);
        checkInputParamIsNull(schema);
        JsonNode schemaNode = strToJsonNode(schema);
        JsonNode jsonNode = strToJsonNode(json);
        //fge验证json数据是否符合json schema约束规则
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (!report.isSuccess()) {
            // 校验失败打印原因
            printFailMessage(report);
            return Boolean.FALSE;
        }

        // 校验成功
        return Boolean.TRUE;
    }

    private static void checkInputParamIsNull(String inputParam) {
        if (Objects.isNull(inputParam) || inputParam.length()==0) {
            throw new IllegalArgumentException("input param is null");
        }
    }

    private static void printFailMessage(ProcessingReport report) {
        Iterator<ProcessingMessage> it = report.iterator();
        StringBuilder ms = new StringBuilder();
        ms.append("json格式错误: ");
        while (it.hasNext()) {
            ProcessingMessage pm = it.next();
            if (!LogLevel.WARNING.equals(pm.getLogLevel())) {
                ms.append(pm);
            }
        }
        System.out.println(ms);
    }
}
