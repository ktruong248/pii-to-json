package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ParsedResult {
    private List<Person> entries;
    private List<Integer> errors;

    public ParsedResult() {
        entries = new LinkedList<>();
        errors = new LinkedList<>();
    }

    public void add(Person person) {
        entries.add(person);
    }

    public void addError(int failedLineNumber) {
        errors.add(failedLineNumber);
    }

    public List<Person> getEntries() {
        return entries;
    }

    public List<Integer> getErrors() {
        return errors;
    }

    @JsonIgnore
    public void sortEntries(Comparator<Person> personComparator) {
        getEntries().sort(personComparator);
    }

}