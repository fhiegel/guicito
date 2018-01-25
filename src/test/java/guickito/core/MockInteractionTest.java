package guickito.core;

import guickito.samples.MyMockedObject;
import guickito.samples.MySpyiedObject;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MockInteractionTest {

    @ClassRule
    public static InjectClassRule application = Guickito.injectClassRule().build();

    @Rule
    public MethodRule injection = application.inject();

    @Mock
    private MyMockedObject mocked;

    @Spy
    private MySpyiedObject spyied;

    @Test
    public void should_verify_mock() throws Exception {
        mocked.doSomeStuff("");
        verify(mocked, times(1)).doSomeStuff("");
    }

    @Test
    public void should_verify_spy() throws Exception {
        spyied.doSomeOtherStuff("");
        verify(spyied, times(1)).doSomeOtherStuff("");
    }
}
