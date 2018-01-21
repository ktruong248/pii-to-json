import models.ProcessedResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utils.Json;

import java.io.PrintStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JsonPersonWriterTest {
    @Mock private Json json;
    @Mock private ProcessedResult result;
    @Mock private PrintStream printStream;
    private JsonPersonWriter writer;

    @Before
    public void setup() {
        writer = new JsonPersonWriter(printStream, json);
    }

    @Test
    public void shouldConvertToJsonAndWriteToTheStream() {
        String convertedJson = "{\n" +
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
                "}";
        given(json.pretty(result)).willReturn(convertedJson);
        writer.save(result);
        verify(printStream).println(convertedJson);
    }
}