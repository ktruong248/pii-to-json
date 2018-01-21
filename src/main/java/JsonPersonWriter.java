import models.ProcessedResult;
import utils.Json;

import java.io.PrintStream;

/**
 * Simple implementation that will convert the @ParseResult into json and write to the stream
 */
public class JsonPersonWriter implements PersonWriter {

    private final Json json;
    private final PrintStream printStream;

    JsonPersonWriter(PrintStream printStream, Json json) {
        this.printStream = printStream;
        this.json = json;
    }

    @Override
    public void save(ProcessedResult result) {
        printStream.println(json.pretty(result));
    }
}