package com.github.kochab.vsys.netparkingsim.server;

import java.net.Socket;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

public class StandardSessionBuilder implements SessionBuilder {
    @Override
    public Session createSession() {
        return new ServerSession(clientSocket, parkingLot, observer);
    }
    
    @Override
    public SessionBuilder setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        return this;
    }
    
    @Override
    public SessionBuilder setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        return this;
    }
    
    @Override
    public SessionBuilder setObserver(RequestObserver observer) {
        this.observer = observer;
        return this;
    }
    
    private ParkingLot parkingLot;
    private Socket clientSocket;
    private RequestObserver observer = RequestObservers.NULL;
}
