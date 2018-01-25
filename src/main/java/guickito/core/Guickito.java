package guickito.core;

public final class Guickito {
    
    private Guickito() {
    }

    public static InjectClassRuleBuilder injectClassRule() {
        return new InjectClassRuleBuilder();
    }
}
