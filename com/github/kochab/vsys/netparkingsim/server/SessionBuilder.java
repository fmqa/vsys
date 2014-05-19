package com.github.kochab.vsys.netparkingsim.server;

import java.net.Socket;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

/**
 * A session builder.
 *
 * @author Eugen Kinder
 * @author Fadi Moukayed
 *
 */

public interface SessionBuilder extends SessionFactory {
    /**
     * Sets the parking lot to be used for constructed sessions.
     */
    SessionBuilder setParkingLot(ParkingLot parkingLot);
    
    /**
     * Sets the client socket to be used for constructed sessions.
     */
    SessionBuilder setClientSocket(Socket clientSocket);
    
    /**
     * Sets the request observer to be used for constructed sessions.
     */
    SessionBuilder setObserver(RequestObserver observer);
}
