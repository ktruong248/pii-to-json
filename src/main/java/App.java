import models.LastNameFirstNameComparator;
import utils.Json;
import validation.DefaultValidator;

import java.io.PrintStream;

/**
 * Main program that will read file input stream then parse and write to standard output stream.
 * $ Solution < At-Home-Coding-Test-inputfile.txt > output.txt
 */
public class App {
    public static void main(String argv[]) {
        PersonConverter converter = new PersonConverter(new DefaultValidator());
        LastNameFirstNameComparator comparator = new LastNameFirstNameComparator();
        PersonWriter writer = new JsonPersonWriter(new PrintStream(System.out), Json.instance());

        PersonalIdentifyInformationProcessor processor = new PersonalIdentifyInformationProcessor(converter, comparator,
                writer);
        processor.process(System.in);
    }
}