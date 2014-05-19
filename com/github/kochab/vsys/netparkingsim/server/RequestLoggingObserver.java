package com.github.kochab.vsys.netparkingsim.server;

import java.net.InetAddress;
import java.io.PrintStream;

/**
 * A request observer that logs requests to a given stream.
 *
 *
 * @author Eugen Kinder
 *
 */

public class RequestLoggingObserver implements RequestObserver {
    /**
     * Construct a new logging observer.
     *
     * @param clientAddr The client (request source's) address
     * @param clientPort This session's communication port
     * @param log The stream to log requests to
     */
    public RequestLoggingObserver(InetAddress clientAddr, int clientPort, PrintStream log) {
        this.clientAddr = clientAddr;
        this.clientPort = clientPort;
        this.log = log;
    }
    
    private void onReq(String req) {
        log.println("Received '" + req + "' request from: " 
                    + clientAddr + ":" + clientPort);
    }
    
    @Override
    public void onFree() { onReq("free"); }
    
    @Override
    public void onIn() { onReq("in"); }
    
    @Override
    public void onOut() { onReq("out"); }
    
    @Override
    public void onQuit() { onReq("quit"); }
    
    @Override
    public void onUnknown(String data) {
        log.println("Received unknown request from: " 
                    + clientAddr + ":" + clientPort
                    + " [DATA: " + data + "]");
    }
    
    final InetAddress clientAddr;
    final int clientPort;
    private final PrintStream log;
}

