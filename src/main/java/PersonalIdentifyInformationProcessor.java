import models.ProcessedResult;
import models.Person;

import java.io.*;
import java.util.Comparator;
import java.util.logging.Logger;

public class PersonalIdentifyInformationProcessor {
    private static final Logger logger = Logger.getLogger("PersonalIdentifyInformationProcessor");
    private final PersonConverter converter;
    private final Comparator<Person> personComparator;
    private final PersonWriter writer;

    PersonalIdentifyInformationProcessor(PersonConverter converter, Comparator<Person> personComparator,
                                         PersonWriter personWriter) {
        this.converter = converter;
        this.personComparator = personComparator;
        this.writer = personWriter;
    }

    /**
     * processor that will take in InputStream and perform normalize each line into a Person object that contains
     * firstName,lastName, phoneNumber, favorite color, zipcode and then delegate to the writer to persist the result
     *
     * @param inputStream
     * @return the ProcessedResult with valid entries and the failed to parsed line number
     */
    public ProcessedResult process(InputStream inputStream) {
        ProcessedResult parsedResult = processInputStream(inputStream);
        writer.save(parsedResult);
        return parsedResult;
    }

    private ProcessedResult processInputStream(InputStream inputStream) {
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader bufReader = new BufferedReader(isReader);
        ProcessedResult result = new ProcessedResult();

        int lineNumber = 0;
        try {
            boolean done = false;
            String inputStr;
            while (!done) {
                if ((inputStr = bufReader.readLine()) != null) {
                    try {
                        Person parse = converter.parse(inputStr);
                        result.add(parse);
                    } catch (Exception e) {
                        result.addError(lineNumber);
                    }
                } else {
                    // first null line indicate end of file
                    done = true;
                }
                lineNumber++;
            }
        } catch (IOException e) {
            result.addError(lineNumber);
        } finally {
            close(isReader);
            close(bufReader);
        }

        sortEntries(result);
        return result;
    }

    private void sortEntries(ProcessedResult result) {
        result.sortEntries(personComparator);
    }

    private void close(Reader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            logger.warning("failed to perform close on reader");
        }
    }
}
