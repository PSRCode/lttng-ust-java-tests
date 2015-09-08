/*
 * Copyright (C) 2015, EfficiOS Inc., Alexandre Montplaisir <alexmonthy@efficios.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.lttng.ust.agent.integration.events.log4j;

import static org.junit.Assume.assumeTrue;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.lttng.tools.ILttngSession.Domain;
import org.lttng.tools.ILttngSession;
import org.lttng.tools.LttngToolsHelper;
import org.lttng.ust.agent.integration.events.ListEventsITBase;
import org.lttng.ust.agent.log4j.LttngLogAppender;
import org.lttng.ust.agent.utils.LttngUtils;

/**
 * Test suite for the list events command for the log4j domain
 */
public class Log4jListEventsIT extends ListEventsITBase {

    private Logger[] loggers;
    private Appender[] appenders;

    /**
     * Test class setup
     */
    @BeforeClass
    public static void testClassSetup() {
        /* Skip tests if we can't find the JNI library or lttng-tools */
        assumeTrue(LttngUtils.checkForLog4jLibrary());
        assumeTrue(LttngUtils.checkForLttngTools(Domain.LOG4J));

        LttngToolsHelper.destroyAllSessions();
    }

    /**
     * Test setup
     *
     * @throws SecurityException
     * @throws IOException
     */
    @Before
    public void log4jSetup() throws SecurityException, IOException {
        loggers = new Logger[] {
                Logger.getLogger(LOGGER_NAME_1),
                Logger.getLogger(LOGGER_NAME_2),
                Logger.getLogger(LOGGER_NAME_3)
        };

        appenders = new Appender[] {
                new LttngLogAppender(),
                new LttngLogAppender()
        };
    }

    /**
     * Test teardown. Detach and close all log handlers.
     */
    @After
    public void log4jTeardown() {
        for (Logger logger : loggers) {
            for (Appender appender : appenders) {
                logger.removeAppender(appender);
            }
        }

        for (Appender appender : appenders) {
            appender.close();
        }
        appenders = null;
        loggers = null;
    }

    @Override
    protected ILttngSession.Domain getDomain() {
        return ILttngSession.Domain.LOG4J;
    }

    @Override
    protected void attachHandlerToLogger(int handlerIndex, int loggerIndex) {
        loggers[loggerIndex - 1].addAppender(appenders[handlerIndex - 1]);
    }

    @Override
    protected void detachHandlerFromLogger(int handlerIndex, int loggerIndex) {
        loggers[loggerIndex - 1].removeAppender(appenders[handlerIndex - 1]);
    }

}