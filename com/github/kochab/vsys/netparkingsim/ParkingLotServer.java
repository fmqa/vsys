package com.github.kochab.vsys.netparkingsim;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ParkingLotServer implements Runnable {
    public ParkingLotServer(Socket clientSock, ParkingLot pLot) {
        this.clientSock = clientSock;
        this.pLot = pLot;
    }
    
    @Override
    public void run() {
        try {
            PrintWriter clientOut = new PrintWriter(
                clientSock.getOutputStream(),
                true
            );
            BufferedReader clientIn = new BufferedReader(
                new InputStreamReader(clientSock.getInputStream())
            );
            
            String line;
            while ((line = clientIn.readLine()) != null) {
                System.out.println("Got request [" + line + "] from " + 
                                   clientSock.getInetAddress());
                if (line.equals("free")) {
                    clientOut.println(pLot.remaining());
                } else if (line.equals("in")) {
                    clientOut.println(pLot.park() ? "ok" : "fail");
                } else if (line.equals("out")) {
                    clientOut.println(pLot.unpark() ? "ok" : "fail");
                } else if (line.equals("quit")) {
                    System.exit(0);
                } else {
                    System.err.println("Unknown command received: " + line);
                }
            }
            
            clientOut.close();
            clientIn.close();
            clientSock.close();
        } catch (IOException e) {
            System.err.println("Connection error!");
        }
    }
    
    private final Socket clientSock;
    private final ParkingLot pLot;
}