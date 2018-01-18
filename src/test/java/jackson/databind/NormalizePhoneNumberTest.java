package jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NormalizePhoneNumberTest {
    @Mock private JsonGenerator gen;
    @Mock private SerializerProvider serializers;
    private NormalizePhoneNumber normalizePhoneNumber;

    @Before
    public void setup() {
        normalizePhoneNumber = new NormalizePhoneNumber();
    }

    @Test
    public void shouldReplaceAllSpaceWithDash() throws IOException {
        normalizePhoneNumber.serialize("234   333 3333", gen, serializers);
        verify(gen).writeString("234-333-3333");
    }

    @Test
    public void shouldWriteAsNull() throws IOException {
        normalizePhoneNumber.serialize(null, gen, serializers);
        verify(gen).writeNull();
    }
}