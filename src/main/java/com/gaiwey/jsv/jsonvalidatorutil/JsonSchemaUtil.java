package com.gaiwey.jsv.jsonvalidatorutil;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JsonSchemaUtil {

    private JsonSchemaUtil(){
    }

    /**
     * @param jsonStr 验证json字符串
     */
    public static JsonNode strToJsonNode(String jsonStr) {
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonLoader.fromString(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * @param jsonFilePath jsonSchema文件路径
     */
    public static JsonNode schemaToJsonNode(String jsonFilePath) {
        JsonNode jsonSchemaNode = null;
        try {
            jsonSchemaNode = new JsonNodeReader().fromReader(new FileReader(ResourceUtils.getFile(jsonFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonSchemaNode;
    }

    /**
     * @param jsonNode   json数据node
     * @param schemaNode jsonSchema约束node
     */
    private static boolean validate(JsonNode jsonNode, JsonNode schemaNode) {
        //fge验证json数据是否符合json schema约束规则
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            // 校验成功
            return Boolean.TRUE;
        } else {
            // 校验失败打印原因
            printFailMessage(report);
            return Boolean.FALSE;
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

    /**
     * @param jsonNode   json数据node
     * @param jsonFilePath jsonSchema file path
     */
    private static boolean validate(JsonNode jsonNode, String jsonFilePath) {
        JsonNode schemaNode = schemaToJsonNode(jsonFilePath);
        //fge验证json数据是否符合json schema约束规则
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            // 校验成功
            return Boolean.TRUE;
        } else {
            // 校验失败打印原因
            printFailMessage(report);
            return Boolean.FALSE;
        }
    }

    /**
     * @param json   json数据
     * @param jsonFilePath jsonSchema file path
     */
    private static boolean validate(String json, String jsonFilePath) {
        JsonNode schemaNode = schemaToJsonNode(jsonFilePath);
        JsonNode jsonNode = strToJsonNode(json);
        //fge验证json数据是否符合json schema约束规则
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            // 校验成功
            return Boolean.TRUE;
        } else {
            // 校验失败打印原因
            printFailMessage(report);
            return Boolean.FALSE;
        }
    }

    public static void main(String[] args) {
        //JsonSchemaUtil.validate()
    }
}
