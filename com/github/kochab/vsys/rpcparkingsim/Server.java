package com.github.kochab.vsys.rpcparkingsim;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Parking lot RPC server.
 *
 * @author Matthias Siegmund
 * @author Fadi Moukayed
 * @author Eugen Kinder
 */

public class Server {
    public static void main(String[] args) throws IOException, NoSuchMethodException {
        int port = 6700;
        
        if (args.length == 1) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[0] + ". Using default port " + port);
            }
        }
        
        ServerSocket listenSocket = null;
        
        try {
            listenSocket = new ServerSocket(6700);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 6700.");
            System.exit(1);
        }
        
        System.out.println("Server Running.");
        Socket sessionSocket = null;
        
        ParkingLotService service = new ParkingLotService(new SynchronizedParkingLot(5));
        
        for (;;) {
            try {
                sessionSocket = listenSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            ObjectInputStream ois = new ObjectInputStream(sessionSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(sessionSocket.getOutputStream());
            new Thread(new Session(service, ois, oos)).start();
        }
    }
}
