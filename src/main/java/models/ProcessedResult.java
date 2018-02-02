package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ProcessedResult {
    private List<Person> entries;
    private List<Long> errors;

    public ProcessedResult() {
        entries = new LinkedList<>();
        errors = new LinkedList<>();
    }

    public void add(Person person) {
        entries.add(person);
    }

    public void addError(long failedLineNumber) {
        errors.add(failedLineNumber);
    }

    public List<Person> getEntries() {
        return entries;
    }

    public List<Long> getErrors() {
        return errors;
    }

    @JsonIgnore
    public void sortEntries(Comparator<Person> personComparator) {
        getEntries().sort(personComparator);
    }
}