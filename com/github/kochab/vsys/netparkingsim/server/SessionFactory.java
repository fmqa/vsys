package com.github.kochab.vsys.netparkingsim.server;

/**
 * Abstract Factory for sessions.
 *
 * @author Fadi Moukayed
 * @author Eugen Kinder
 *
 */

public interface SessionFactory {
    /**
     * Construct a new session.
     */
    Session createSession();
}
