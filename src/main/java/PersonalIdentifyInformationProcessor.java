import models.ParsedResult;
import models.Person;

import java.io.*;
import java.util.Comparator;
import java.util.logging.Logger;

public class PersonalIdentifyInformationProcessor {
    private static final Logger logger = Logger.getLogger("PersonalIdentifyInformationProcessor");
    private final PersonParser parser;
    private final Comparator<Person> personComparator;

    PersonalIdentifyInformationProcessor(PersonParser parser, Comparator<Person> personComparator) {
        this.parser = parser;
        this.personComparator = personComparator;
    }

    /**
     * processor that will take in InputStream and perform normalize each line into a Person object that contains
     * firstName,lastName, phoneNumber, favorite color, zipcode.
     *
     * @param inputStream
     * @return the ParsedResult with valid entries and the failed to parsed line number
     */
    public ParsedResult process(InputStream inputStream) {
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader bufReader = new BufferedReader(isReader);
        ParsedResult result = new ParsedResult();

        int lineNumber = 0;
        try {
            boolean done = false;
            String inputStr;
            while (!done) {
                if ((inputStr = bufReader.readLine()) != null) {
                    try {
                        Person parse = parser.parse(inputStr);
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

    private void sortEntries(ParsedResult result) {
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
