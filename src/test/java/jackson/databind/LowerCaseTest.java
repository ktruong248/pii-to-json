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
public class LowerCaseTest {
    private LowerCase lowerCase;
    @Mock private JsonGenerator gen;
    @Mock private SerializerProvider serializers;

    @Before
    public void setUp() throws Exception {
        lowerCase = new LowerCase();
    }

    @Test
    public void shouldWriteValueInLowerCase() throws IOException {
        lowerCase.serialize("Hi ", gen, serializers);
        verify(gen).writeString("hi ");
    }

    @Test
    public void shouldWriteValueAsNull() throws IOException {
        lowerCase.serialize(null, gen, serializers);
        verify(gen).writeNull();
    }
}