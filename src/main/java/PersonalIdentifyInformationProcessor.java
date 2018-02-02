import com.opencsv.CSVReader;
import models.ProcessedResult;
import models.Person;

import java.io.*;
import java.util.Comparator;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Processor that will
 *   process csv input stream
 *   for each line normalized it to Person object
 *   then sort the result before write it to the provided Writer
 *
 * Notes:
 *   This is a very simple implementation where it doesn't properly handle large file input. As an improvement,
 * we can inject in FlushPolicy to control when to flush the data. Under this case this class will need to reconsider
 * the sorting logic.
 */
public class PersonalIdentifyInformationProcessor {
    private static final Logger logger = Logger.getLogger("PersonalIdentifyInformationProcessor");
    private final Function<String[],Person> personMapper;
    private final Comparator<Person> personComparator;
    private final PersonWriter writer;

    PersonalIdentifyInformationProcessor(Function<String[],Person> personMapper,
                                         Comparator<Person> personComparator,
                                         PersonWriter personWriter) {
        this.personMapper = personMapper;
        this.personComparator = personComparator;
        this.writer = personWriter;
    }

    /**
     * processor that will take in InputStream and perform normalize each line into a Person object that contains
     * firstName,lastName, phoneNumber, favorite color, zipcode and then delegate to the writer to persist the result
     *
     * @param inputStream the input stream to be process
     * @return the ProcessedResult with valid entries and the failed to parsed line number
     */
    public ProcessedResult process(InputStream inputStream) {
        ProcessedResult parsedResult = processInputStream(inputStream);
        writer.save(parsedResult);
        return parsedResult;
    }

    private ProcessedResult processInputStream(InputStream inputStream) {
        ProcessedResult result = new ProcessedResult();
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
        try {
            String inputStr[];
            while ((inputStr = csvReader.readNext()) != null) {
                try {
                    Person parse = personMapper.apply(inputStr);
                    result.add(parse);
                } catch (Exception e) {
                    result.addError(csvReader.getLinesRead()-1);
                }
            }
        } catch (IOException e) {
            result.addError(csvReader.getLinesRead()-1);
        } finally {
            close(csvReader);
        }

        sortEntries(result);
        return result;
    }

    private void sortEntries(ProcessedResult result) {
        result.sortEntries(personComparator);
    }

    private void close(Closeable reader) {
        try {
            reader.close();
        } catch (IOException e) {
            logger.warning("failed to perform close on reader");
        }
    }
}
