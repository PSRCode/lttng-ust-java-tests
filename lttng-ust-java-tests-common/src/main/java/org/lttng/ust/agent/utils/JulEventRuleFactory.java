package org.lttng.ust.agent.utils;

public final class JulEventRuleFactory extends EventRuleFactory {

    private static final int levelAllValue = Integer.MIN_VALUE;

    public JulEventRuleFactory() {
        super(levelAllValue);
    }
}