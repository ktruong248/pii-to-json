import exception.ParserFailureException;
import models.Person;
import models.ProcessedResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonalIdentifyInformationProcessorTest {

    @Mock private Comparator<Person> comparator;
    @Mock private Function<String[],Person> mapper;
    @Mock private PersonWriter writer;
    @Mock private Person sam;
    @Mock private Person cameron;
    private PersonalIdentifyInformationProcessor processor;

    @Before
    public void setup() {
        processor = new PersonalIdentifyInformationProcessor(mapper, comparator, writer);
    }

    @Test
    public void shouldContinueProcessWhenEncounterErrorAndCallWriterToSaveProcessedResult() {
        String datas = "Sam T., Washington, 85360, 353 791 6380, purple\n" +
                "Cameron, Kathy, 613-658-9293, red, 143123121\n" +
                "Jamie Stevenson, yellow, 84880, 028 164 6574\n";

        when(mapper.apply(new String[]{"Sam T.", " Washington", " 85360", " 353 791 6380", " purple"})).thenReturn(sam);
        when(mapper.apply(new String[]{"Cameron", " Kathy", " 613-658-9293", " red", " 143123121"}))
                .thenThrow(new ParserFailureException("invalid phone number"));
        when(mapper.apply(new String[]{"Jamie Stevenson", " yellow", " 84880", " 028 164 6574"})).thenReturn(cameron);

        ProcessedResult result = processor.process(new ByteArrayInputStream(datas.getBytes()));
        //verify the writer call with the same object as the return
        verify(writer).save(result);
        assertThat(result.getErrors()).containsExactly(1L);
        assertThat(result.getEntries()).containsExactly(sam, cameron);
    }

    @Test
    public void shouldCallComparatorToSort() {
        String datas = "Sam T., Washington, 85360, 353 791 6380, purple\n" +
                "Jamie Stevenson, yellow, 84880, 028 164 6574\n";

        when(mapper.apply(new String[]{"Sam T.", " Washington", " 85360", " 353 791 6380", " purple"})).thenReturn(sam);
        when(mapper.apply(new String[]{"Jamie Stevenson", " yellow", " 84880", " 028 164 6574"})).thenReturn(cameron);

        ProcessedResult result = processor.process(new ByteArrayInputStream(datas.getBytes()));
        assertThat(result.getErrors()).isEmpty();
        verify(comparator).compare(notNull(), notNull());
    }
}