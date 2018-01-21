package models;

import org.junit.Before;
import org.junit.Test;
import utils.Json;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessedResultTest {

    private ProcessedResult result;

    @Before
    public void setup() {
        result = new ProcessedResult();
    }

    @Test
    public void serializesToJSON() {
        result.add(new Person("last", "first", "1223444", "33333", "Red"));
        result.addError(2);

        assertThat(Json.instance().pretty(result)).isEqualTo("{\n" +
                "  \"entries\" : [\n" +
                "    {\n" +
                "      \"color\" : \"red\",\n" +
                "      \"firstname\" : \"first\",\n" +
                "      \"lastname\" : \"last\",\n" +
                "      \"phonenumber\" : \"1223444\",\n" +
                "      \"zipcode\" : \"33333\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"errors\" : [\n" +
                "    2\n" +
                "  ]\n" +
                "}");
    }

}