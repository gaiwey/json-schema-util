package com.gaiwey.jsv.jsonvalidatorutil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.Map;

public class GenerateJsonSchemaUtilTest {


    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(
            GenerateJsonSchemaUtil.classToJsonSchema(MyClass.class, true)
        );
    }

    class MyClass {

        @JsonProperty("name")
        private String name;

        @JsonProperty("startDayOfWeek")
        private StartDayOfWeek startDayOfWeek;

        @JsonProperty("timezone")
        private String timezone;

        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("startDayOfWeek")
        public StartDayOfWeek getStartDayOfWeek() {
            return startDayOfWeek;
        }

        @JsonProperty("startDayOfWeek")
        public void setStartDayOfWeek(StartDayOfWeek startDayOfWeek) {
            this.startDayOfWeek = startDayOfWeek;
        }
    }

    enum StartDayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;

    }
}