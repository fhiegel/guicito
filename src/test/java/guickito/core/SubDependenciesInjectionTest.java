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

public class SubDependenciesInjectionTest {

    @ClassRule
    public static InjectClassRule application = Guickito.injectClassRule().build();

    @Rule
    public MethodRule injection = application.inject();

    @Spy
    private AnObject anObject;

    // @Mock
    // private AnObject aMockedObject;

    @Mock
    private MyMockedObject mocked;

    @Spy
    private MySpyiedObject spyied;

    static class AnObject {
        @Inject
        MyMockedObject injectedMock;
        @Inject
        MySpyiedObject injectedSpy;
        @Inject
        MyInjectedObject injectedObject;
    }

    @Test
    public void should_inject_dependencies_inside_dependencies() throws Exception {
        assertThat(anObject.injectedObject).isNotNull();
    }

    @Test
    public void should_inject_mocks_inside_dependencies() throws Exception {
        assertThat(MockUtil.isMock(anObject.injectedMock)).isTrue();
    }

    @Test
    public void should_inject_spied_dependcies_inside_dependencies() throws Exception {
        assertThat(MockUtil.isSpy(anObject.injectedSpy)).isTrue();
    }
}
