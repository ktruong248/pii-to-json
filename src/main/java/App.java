import models.LastNameFirstNameComparator;
import models.ParsedResult;
import utils.Json;
import validation.DefaultValidator;

/**
 * Main program that will read file input stream then parse and write to standard output stream.
 * $ Solution < At-Home-Coding-Test-inputfile.txt > output.txt
 */
public class App {
    public static void main(String argv[]) {
        PersonParser parser = new PersonParser(new DefaultValidator());
        LastNameFirstNameComparator comparator = new LastNameFirstNameComparator();

        PersonalIdentifyInformationProcessor processor = new PersonalIdentifyInformationProcessor(parser, comparator);

        ParsedResult result = processor.process(System.in);
        System.out.println(Json.instance().pretty(result));
    }
}