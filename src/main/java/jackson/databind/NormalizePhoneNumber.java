package jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * simple normalized phone number json serializer that will replace all empty space with `-`
 */
public class NormalizePhoneNumber extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.replaceAll("\\s+", "-"));
        } else {
            gen.writeNull();
        }
    }
}