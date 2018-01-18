import models.ParsedResult;
import models.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonalIdentifyInformationProcessorTest {

    @Mock private Comparator<Person> comparator;
    @Mock private PersonParser parser;
    @Mock private Person sam;
    @Mock private Person cameron;

    private PersonalIdentifyInformationProcessor processor;
    private InputStream inputStream;

    @Before
    public void setup() {
        processor = new PersonalIdentifyInformationProcessor(parser, comparator);
    }

    @Test
    public void shouldContinueProcessWhenEncounterError() {
        String datas = "Sam T., Washington, 85360, 353 791 6380, purple\n" +
                "Cameron, Kathy, (613)-658-9293, red, 143123121\n" +
                "Jamie Stevenson, yellow, 84880, 028 164 6574\n";

        when(parser.parse("Sam T., Washington, 85360, 353 791 6380, purple")).thenReturn(sam);
        when(parser.parse("Cameron, Kathy, (613)-658-9293, red, 143123121"))
                .thenThrow(new IllegalArgumentException("invalid phone number"));
        when(parser.parse("Jamie Stevenson, yellow, 84880, 028 164 6574")).thenReturn(cameron);

        inputStream = new ByteArrayInputStream(datas.getBytes());

        ParsedResult result = processor.process(inputStream);
        assertThat(result.getErrors()).containsExactly(1);
        assertThat(result.getEntries()).containsExactly(sam, cameron);
    }

    @Test
    public void shouldCallComparatorToSort() {
        String datas = "Sam T., Washington, 85360, 353 791 6380, purple\n" +
                "Jamie Stevenson, yellow, 84880, 028 164 6574\n";

        when(parser.parse("Sam T., Washington, 85360, 353 791 6380, purple")).thenReturn(sam);
        when(parser.parse("Jamie Stevenson, yellow, 84880, 028 164 6574")).thenReturn(cameron);

        inputStream = new ByteArrayInputStream(datas.getBytes());

        ParsedResult result = processor.process(inputStream);
        assertThat(result.getErrors()).isEmpty();
        verify(comparator).compare(notNull(), notNull());
    }
}