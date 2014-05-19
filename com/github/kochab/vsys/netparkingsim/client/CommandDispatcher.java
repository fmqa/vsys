package com.github.kochab.vsys.netparkingsim.client;

/**
 * Synchronously dispatches commands and returns the response.
 *
 * @author Matthias Siegmund
 *
 */

public interface CommandDispatcher {
    /**
     * Dispatch a command a return the response.
     *
     * Note that this method is synchronous and may block.
     *
     * @param cmd The command to be dispatched
     * @return A response
     */
    String execute(String cmd);
}