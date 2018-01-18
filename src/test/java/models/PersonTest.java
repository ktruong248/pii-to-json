package models;

import org.junit.Test;
import utils.Json;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
    @Test
    public void shouldSerializePersonAsJson() {
        Person person = new Person("john", "smith", "245 345  5333", "93554", "Blue");
        assertThat(Json.instance().pretty(person)).isEqualTo("{\n" +
                "  \"color\" : \"blue\",\n" +
                "  \"firstname\" : \"smith\",\n" +
                "  \"lastname\" : \"john\",\n" +
                "  \"phonenumber\" : \"245-345-5333\",\n" +
                "  \"zipcode\" : \"93554\"\n" +
                "}");
    }
}