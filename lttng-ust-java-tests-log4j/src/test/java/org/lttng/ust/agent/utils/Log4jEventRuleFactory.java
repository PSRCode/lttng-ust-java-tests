package org.lttng.ust.agent.utils;

public final class Log4jEventRuleFactory extends EventRuleFactory {

    private static final int levelAllValue = Integer.MIN_VALUE;

    public Log4jEventRuleFactory() {
        super(levelAllValue);
    }
}