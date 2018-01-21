package validation;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultValidatorTest {

    private DefaultValidator validator;

    @Before
    public void setup() {
        validator = new DefaultValidator();
    }
    
    @Test
    public void shouldReturnFalseForEmptyValue() {
        Assertions.assertThat(validator.isZipCode("")).isFalse();
    }

    @Test
    public void shouldReturnFalseForNullValue() {
        assertThat(validator.isZipCode(null)).isFalse();
    }

    @Test
    public void shouldReturnTrueForValidZipCode() {
        assertThat(validator.isZipCode("12345")).isTrue();
    }

    @Test
    public void shouldReturnFalseForAllNumericLessThanFiveDigits(){
        assertThat(validator.isZipCode("1234")).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenValueContainNonNumeric() {
        assertThat(validator.isZipCode("1234-")).isFalse();
    }

    @Test
    public void shouldReturnTrueForPhoneNumberWithTenDigitsAndSpaceInBetween() {
        assertThat(validator.isValidPhoneNumber("045 345 5333")).isTrue();
    }

    @Test
    public void shouldReturnTrueForPhoneNumberWithDashAndParentheisAroundAreaCode() {
        assertThat(validator.isValidPhoneNumber("(245)-345-5333")).isTrue();
    }

    @Test
    public void shouldReturnInvalidPhoneNumberWhenExceed10Digits() {
        assertThat(validator.isValidPhoneNumber("245 345 53335")).isFalse();
    }

    @Test
    public void shouldReturnFalseWhePhoneNumberContainNonDigit() {
        assertThat(validator.isValidPhoneNumber("245- 345 5333")).isFalse();
    }
}
