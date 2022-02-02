package org.lttng.ust.agent.utils;

public final class Log4j2CompatEventRuleFactory extends EventRuleFactory {

    private static final int levelAllValue = Integer.MIN_VALUE;

    public Log4j2CompatEventRuleFactory() {
        super(levelAllValue);
    }
}