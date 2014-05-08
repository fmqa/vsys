package com.github.kochab.vsys.netparkingsim;

import java.net.ServerSocket;
import java.io.IOException;

class ParkingLotServerMain {
    private ParkingLotServerMain() {
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: ParkingLotServerMain <PORT> [N = 5]");
            System.exit(1);
        }
        
        int n = 5;
        
        if (args.length == 2) {
            try {
                n = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                n = -1;
            }
            if (n < 1) {
                System.err.println("Invalid number of parking spaces: " + args[1]);
            }
        }
        
        ParkingLot pLot = new SynchronizedParkingLot(n);
        
        ServerSocket ssock = null;
        try {
            try {
                ssock = new ServerSocket(Integer.valueOf(args[0]));
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number: " + args[0]);
            }
            
            try {
                for (;;) {
                    (new Thread(new ParkingLotServer(ssock.accept(), pLot))).start();
                }
            } catch (IOException e) {
                System.err.println("Couldn't accept incoming connection.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Listen failed on port: " + args[0]);
            System.exit(1);
        } finally {
            try {
                ssock.close();
            } catch (IOException e) {
                System.err.println("Could close server socket: " + ssock);
                System.exit(1);
            }
        }
    }
}
