import com.google.common.base.Preconditions;
import exception.ParserFailureException;
import models.Person;
import validation.Validator;

public class PersonParser {
    private static final String PERSON_DELIMETER = ",";
    private final Validator validator;

    PersonParser(Validator validator) {
       this.validator = validator;
    }

    public Person parse(String encodedPersonPii) {
        String[] piiDatas = encodedPersonPii.trim().split(PERSON_DELIMETER);
        String lastName;
        String firstName;
        String phoneNumber;
        String favoriteColor;
        String zipCode;

        if (piiDatas.length == 5) {
            if (isZipCodeAtTheEndFormat(piiDatas)) {
                //LastName, FirstName, (703)-711-0996, Blue, 11013
                lastName = piiDatas[0];
                firstName = piiDatas[1];
                phoneNumber = piiDatas[2];
                favoriteColor = piiDatas[3];
                zipCode = piiDatas[4];
            } else {
                // FirstName, LastName, 12023, 636 121 1111, Yellow
                firstName = piiDatas[0];
                lastName = piiDatas[1];
                zipCode = piiDatas[2];
                phoneNumber = piiDatas[3];
                favoriteColor = piiDatas[4];
            }
        } else if (piiDatas.length == 4) {
            // FirstName LastName, Purple, 14537, 713 905 0383
            String[] fullName = piiDatas[0].trim().split(" ");
            firstName = fullName[0];
            lastName = fullName[1];
            favoriteColor = piiDatas[1];
            zipCode = piiDatas[2];
            phoneNumber = piiDatas[3];
        } else {
            throw new ParserFailureException("Invalid Format");
        }

        validatePhoneNumber(phoneNumber);

        return new Person(lastName, firstName, phoneNumber, zipCode, favoriteColor);
    }


    private void validatePhoneNumber(String phoneNumber) {
        Preconditions.checkArgument(validator.isValidPhoneNumber(phoneNumber), "Invalid Phone Number " + phoneNumber);
    }

    private boolean isZipCodeAtTheEndFormat(String[] piiDatas) {
        return validator.isZipCode(piiDatas[4]);
    }
}
