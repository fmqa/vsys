package com.github.kochab.vsys.netparkingsim.server;

import java.net.InetAddress;

/**
 * A Parking server session.
 *
 * @author Fadi Moukayed
 *
 */

public interface Session extends Runnable {
    /**
     * Returns the address of the parking client.
     */
    InetAddress getClientAddress();
    
    /**
     * Returns the client port.
     */
    int getClientPort();
}
