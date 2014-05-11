package com.github.kochab.vsys.netparkingsim.server;

import java.net.Socket;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

public interface SessionBuilder extends SessionFactory {
    SessionBuilder setParkingLot(ParkingLot parkingLot);
    SessionBuilder setClientSocket(Socket clientSocket);
    SessionBuilder setObserver(RequestObserver observer);
}
