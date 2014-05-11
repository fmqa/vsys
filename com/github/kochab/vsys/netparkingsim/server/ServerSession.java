package com.github.kochab.vsys.netparkingsim.server;

import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

public class ServerSession implements Session {
    public ServerSession(Socket clientSock, ParkingLot pLot, RequestObserver observer) {
        this.clientSock = clientSock;
        dispatcher = new RequestDispatcher(
            new RequestObservingDecorator(
                new ParkingLotRequestHandler(pLot),
                observer
            )
        );
    }
    
    @Override
    public void run() {
        try {
            dispatcher.apply(clientSock.getInputStream(), 
                             clientSock.getOutputStream());
        } catch (IOException e) {
            System.err.println("Connection error!");
        }
    }
    
    @Override
    public InetAddress getClientAddress() {
        return clientSock.getInetAddress();
    }
    
    @Override
    public int getClientPort() {
        return clientSock.getPort();
    }
    
    private final Socket clientSock;
    private final RequestDispatcher dispatcher;
}