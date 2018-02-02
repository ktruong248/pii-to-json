package models;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Comparator;

/**
 * Simple sorting (last name follow by first name) in ascending alphabetical order
 */
public class LastNameFirstNameComparator implements Comparator<Person> {
    @Override
    public int compare(Person left, Person right) {
        Preconditions.checkNotNull(left);
        Preconditions.checkNotNull(right);

        return ComparisonChain.start()
                .compare(left.getLastName(), right.getLastName(), Ordering.natural())
                .compare(left.getFirstName(), right.getFirstName(), Ordering.natural())
                .result();
    }
}