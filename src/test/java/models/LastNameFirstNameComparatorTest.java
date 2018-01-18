package models;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LastNameFirstNameComparatorTest {
    private LastNameFirstNameComparator comparator;
    private LinkedList<Person> persons;

    @Before
    public void setup() {
        comparator = new LastNameFirstNameComparator();
        persons = new LinkedList<>();
    }

    @Test
    public void shouldEnsureLeftParamIsNotNull() {
        assertThatThrownBy(() -> {
            comparator.compare(null, new Person("Sam T.", "Washington", "353 791 6380", "85360", "Purple"));
        }).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldEnsureRightParamIsNotNull() {
        assertThatThrownBy(() -> {
            comparator.compare(new Person("Sam T.", "Washington", "353 791 6380", "85360", "Purple"), null);
        }).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldSortByLastNameThanFirstNameInAscending() {
        // Sam T., Washington, 85360, 353 791 6380, purple
        persons.add(new Person("Sam T.", "Washington", "353 791 6380", "85360", "Purple"));
        persons.add(new Person("Jamie", "Stevenson", "353 791 6380", "84880", "Purple"));

        persons.sort(comparator);

        assertThat(persons.get(0).getLastName()).isEqualTo("Jamie");
        assertThat(persons.get(0).getFirstName()).isEqualTo("Stevenson");
        assertThat(persons.get(1).getLastName()).isEqualTo("Sam T.");
        assertThat(persons.get(1).getFirstName()).isEqualTo("Washington");
    }
}