package com.github.kochab.vsys.netparkingsim.server;

import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import com.github.kochab.vsys.netparkingsim.core.ParkingLot;

/**
 * A Parking server session.
 *
 * @author Matthias Siegmund
 * @author Fadi Moukayed
 *
 */

public class ServerSession implements Session {
    /**
     * Constructs a Parking server session for the given client, Parking lot, and
     * the supplied request observer.
     *
     * @param clientSock The client socket.
     * @param pLot The parking lot which will be the subject of this session
     * @param observer A request observer
     */
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