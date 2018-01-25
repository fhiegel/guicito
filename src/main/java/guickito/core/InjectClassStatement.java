package guickito.core;

import org.junit.runners.model.Statement;

class InjectClassStatement extends Statement {

    private final Statement statement;
    private final InjectionRuleSupport handler;

    InjectClassStatement(Statement statement, InjectionRuleSupport handler) {
        this.statement = statement;
        this.handler = handler;
    }

    @Override
    public void evaluate() throws Throwable {
        handler.initialize();
        statement.evaluate();
        handler.release();
    }

}