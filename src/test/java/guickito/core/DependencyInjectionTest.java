package guickito.core;

import guickito.samples.MyInjectedObject;
import guickito.samples.MyMockedObject;
import guickito.samples.MySpyiedObject;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.util.MockUtil;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

public class DependencyInjectionTest {

    @ClassRule
    public static InjectClassRule application = Guickito.injectClassRule().build();

    @Rule
    public MethodRule injection = application.inject();

    @Mock
    private MyMockedObject mocked;

    @Spy
    private MySpyiedObject spyied;

    @Inject
    private MyInjectedObject injectedObject;

    @Test
    public void should_inject_dependencies() throws Exception {
        assertThat(injectedObject).isNotNull();
    }

    @Test
    public void should_inject_mocked_dependencies() throws Exception {
        assertThat(MockUtil.isMock(mocked)).isTrue();
    }

    @Test
    public void should_inject_spied_dependencies() throws Exception {
        assertThat(MockUtil.isSpy(spyied)).isTrue();
    }
}
