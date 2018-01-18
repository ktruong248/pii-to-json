package validation;

public class DefaultValidator implements Validator {
    private static final String ZIPCODE_REGEX = "^[0-9]{5,}";
    private static final String PHONE_NUMBER_REGEX = "[0-9]{10}";

    @Override
    public boolean isZipCode(String value) {
        return (value != null && value.trim().matches(ZIPCODE_REGEX));
    }

    @Override
    public boolean isValidPhoneNumber(String value) {
        return (value != null && value.trim().replaceAll("\\s+", "").matches(PHONE_NUMBER_REGEX));
    }
}