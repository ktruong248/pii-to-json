package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import jackson.databind.LowerCase;
import jackson.databind.NormalizePhoneNumber;

import static com.google.common.base.Preconditions.checkNotNull;

@JsonPropertyOrder({"color", "firstname", "lastname", "phonenumber", "zipcode"})
public class Person {
    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("phonenumber")
    @JsonSerialize(using = NormalizePhoneNumber.class)
    private String phoneNumber;

    @JsonProperty("color")
    @JsonSerialize(using = LowerCase.class)
    private String favoriteColor;

    @JsonProperty("zipcode")
    private String zipCode;

    public Person(String lastName, String firstName, String phoneNumber, String zipCode, String favoriteColor) {
        assertRequiredNotNull(lastName, firstName, phoneNumber, zipCode, favoriteColor);
        this.lastName = lastName.trim();
        this.firstName = firstName.trim();
        this.phoneNumber = phoneNumber.trim();
        this.zipCode = zipCode.trim();
        this.favoriteColor = favoriteColor.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    private void assertRequiredNotNull(String lastName, String firstName, String phoneNumber, String zipCode, String favoriteColor) {
        checkNotNull(lastName);
        checkNotNull(firstName);
        checkNotNull(phoneNumber);
        checkNotNull(zipCode);
        checkNotNull(favoriteColor);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("phoneNumber", phoneNumber)
                .add("favoriteColor", favoriteColor)
                .add("zipCode", zipCode)
                .toString();
    }
}
