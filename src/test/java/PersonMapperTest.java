import exception.ParserFailureException;
import models.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonMapperTest {
    @Mock
    private Validator validator;
    private PersonMapper mapper;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String color;
    private String zipCode;

    @Before
    public void setUp() {
        mapper = new PersonMapper(validator);

        lastName = "LastName";
        firstName = "FirstName";
        phoneNumber = "703 711 0996";
        color = "Blue";
        zipCode = "11013";
    }

    @Test
    public void shouldHandleThirdValueIsValidPhoneNumber() {
        when(validator.isValidPhoneNumber(phoneNumber)).thenReturn(true);

        Person person = mapper.apply(new String[]{lastName, firstName, phoneNumber, color, zipCode});
        assertThat(person.getLastName()).isEqualTo(lastName);
        assertThat(person.getFirstName()).isEqualTo(firstName);
        assertThat(person.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(person.getFavoriteColor()).isEqualTo(color);
        assertThat(person.getZipCode()).isEqualTo(zipCode);
    }

    @Test
    public void shouldHandleFourthValueIsValidPhoneNumber() {
        when(validator.isValidPhoneNumber(phoneNumber)).thenReturn(true);

        Person person = mapper.apply(new String[]{firstName, lastName, zipCode, phoneNumber, color});
        assertThat(person.getLastName()).isEqualTo(lastName);
        assertThat(person.getFirstName()).isEqualTo(firstName);
        assertThat(person.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(person.getFavoriteColor()).isEqualTo(color);
        assertThat(person.getZipCode()).isEqualTo(zipCode);
    }

    @Test
    public void shouldThrowParserExceptionWhenTheFourthValueIsInvalidPhoneNumber() {
        when(validator.isValidPhoneNumber(phoneNumber)).thenReturn(false);

        assertThatThrownBy(() -> mapper.apply(new String[]{firstName, lastName, zipCode, phoneNumber, color}))
                .isExactlyInstanceOf(ParserFailureException.class);
    }

    @Test
    public void shouldAbleToParsePersonWithFullNameFormat() {
        when(validator.isValidPhoneNumber(phoneNumber)).thenReturn(true);

        Person person = mapper.apply(new String[]{firstName + " " + lastName, color, zipCode, phoneNumber});
        assertThat(person.getLastName()).isEqualTo(lastName);
        assertThat(person.getFirstName()).isEqualTo(firstName);
        assertThat(person.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(person.getFavoriteColor()).isEqualTo(color);
        assertThat(person.getZipCode()).isEqualTo(zipCode);
    }

    @Test
    public void shouldThrowParserExceptionForInvalidPhoneNumberWithFullNameFormat() {
        assertThatThrownBy(() -> mapper.apply(new String[]{firstName + " " + lastName, zipCode, phoneNumber, color}))
                .isExactlyInstanceOf(ParserFailureException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPiiDataLessThanFour() {
        assertThatThrownBy(() -> mapper.apply(new String[]{firstName, zipCode, phoneNumber}))
                .isExactlyInstanceOf(ParserFailureException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPiiDataMoreThanFive() {
        assertThatThrownBy(() ->
                mapper.apply(new String[]{firstName, lastName, phoneNumber, zipCode, phoneNumber, zipCode}))
                .isExactlyInstanceOf(ParserFailureException.class);
    }
}