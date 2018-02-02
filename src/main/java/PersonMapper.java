import exception.ParserFailureException;
import models.Person;
import validation.Validator;

import java.util.function.Function;

/**
 * Responsible for taking a string encoded person data and transform it into normalized Person object. This can handle
 * the following format:
 * <p>
 * LastName, FirstName, (703)-711-0996, Blue, 11013
 * FirstName, LastName, 12023, 636 121 1111, Yellow
 * FirstName LastName, Purple, 14537, 713 905 0383
 * <p>
 * For simplicity, a line is invalid if its phone number does not contain the proper number of digits.
 */
public class PersonMapper implements Function<String[], Person> {
    private final Validator validator;

    PersonMapper(Validator validator) {
        this.validator = validator;
    }

    /**
     * perform normalized pii data into normalized Person object.
     *
     * @param piiDatas source pii data to be normalize into normalize Person
     * @return normalized Person
     * @throws ParserFailureException for any invalid format
     */
    @Override
    public Person apply(String[] piiDatas) {
        if (piiDatas.length == 5) {
            if (validator.isValidPhoneNumber(piiDatas[2])) {
                //LastName, FirstName, (703)-711-0996, Blue, 11013
                return new Person(piiDatas[0], piiDatas[1], piiDatas[2], piiDatas[4], piiDatas[3]);
            } else if (validator.isValidPhoneNumber(piiDatas[3])) {
                // FirstName, LastName, 12023, 636 121 1111, Yellow
                return new Person(piiDatas[1], piiDatas[0], piiDatas[3], piiDatas[2], piiDatas[4]);
            }
            throw new ParserFailureException("Invalid Phone Number");
        } else if (piiDatas.length == 4) {
            // FirstName LastName, Purple, 14537, 713 905 0383
            if (validator.isValidPhoneNumber(piiDatas[3])) {
                String[] fullName = piiDatas[0].trim().split(" ");
                return new Person(fullName[1], fullName[0], piiDatas[3], piiDatas[2], piiDatas[1]);
            }
        }

        throw new ParserFailureException("Invalid Format");
    }
}
