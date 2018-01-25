package guickito.core;

import guickito.samples.MyMockedObject;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ArgumentCaptureTest {

    @ClassRule
    public static InjectClassRule application = Guickito.injectClassRule().build();

    @Rule
    public MethodRule injection = application.inject();

    @Mock
    private MyMockedObject mocked;

    @Captor
    private ArgumentCaptor<String> aCaptor;

    @Test
    public void should_capture() throws Exception {
        mocked.doSomeStuff("some input");

        verify(mocked, times(1)).doSomeStuff(aCaptor.capture());
        assertThat(aCaptor.getAllValues()).containsExactly("some input");
    }

    @Test
    public void should_capture_only_what_happend_in_test_when_capture_in_two_methods() throws Exception {
        mocked.doSomeStuff("some other input");

        verify(mocked, times(1)).doSomeStuff(aCaptor.capture());
        assertThat(aCaptor.getAllValues()).containsExactly("some other input");
    }
}
