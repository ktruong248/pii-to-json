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
public class PersonConverterTest {

    @Mock private Validator validator;
    private PersonConverter parser;

    @Before
    public void setUp() {
        parser = new PersonConverter(validator);
    }

    @Test
    public void shouldAbleToParsePersonWithZipCodeAtTheEnd() {
        when(validator.isValidPhoneNumber(" 703 711 0996")).thenReturn(true);
        when(validator.isZipCode("11013")).thenReturn(true);

        Person person = parser.parse(" LastName, FirstName, 703 711 0996, Blue,11013");
        assertThat(person.getLastName()).isEqualTo("LastName");
        assertThat(person.getFirstName()).isEqualTo("FirstName");
        assertThat(person.getPhoneNumber()).isEqualTo("703 711 0996");
        assertThat(person.getFavoriteColor()).isEqualTo("Blue");
        assertThat(person.getZipCode()).isEqualTo("11013");
    }

    @Test
    public void shouldAbleToParsePersonWithFavoriteColorAtTheEnd() {
        when(validator.isValidPhoneNumber(" 636 121 1111")).thenReturn(true);

        Person person = parser.parse(" FirstName, LastName, 12023, 636 121 1111, Yellow");
        assertThat(person.getLastName()).isEqualTo("LastName");
        assertThat(person.getFirstName()).isEqualTo("FirstName");
        assertThat(person.getPhoneNumber()).isEqualTo("636 121 1111");
        assertThat(person.getFavoriteColor()).isEqualTo("Yellow");
        assertThat(person.getZipCode()).isEqualTo("12023");
    }

    @Test
    public void shouldAbleToParsePersonWithFullName() {
        when(validator.isValidPhoneNumber(" 713 905 0383")).thenReturn(true);

        Person person = parser.parse(" FirstName LastName, Purple, 14537, 713 905 0383");
        assertThat(person.getLastName()).isEqualTo("LastName");
        assertThat(person.getFirstName()).isEqualTo("FirstName");
        assertThat(person.getPhoneNumber()).isEqualTo("713 905 0383");
        assertThat(person.getFavoriteColor()).isEqualTo("Purple");
        assertThat(person.getZipCode()).isEqualTo("14537");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPiiDataLessThanFour() {
        assertThatThrownBy(() -> { parser.parse(" FirstName LastName, Purple, 14537");})
                .isExactlyInstanceOf(ParserFailureException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPiiDataMoreThanFive() {
        assertThatThrownBy(() -> { parser.parse(" First, Last, Purple, 14537,713 905 0383, extra ");})
                .isExactlyInstanceOf(ParserFailureException.class);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPhoneNumberIsInvalid() {
        assertThatThrownBy(() -> { parser.parse("First, Last, Purple, 14537,(713)905 0383");})
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}