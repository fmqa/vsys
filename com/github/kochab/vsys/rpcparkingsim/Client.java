package com.github.kochab.vsys.rpcparkingsim;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Parking lot RPC requester (client).
 *
 * @author Eugen Kinder
 *
 */

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 6700;
        
        switch (args.length) {
            case 2:
                try {
                    port = Integer.valueOf(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port: " + args[1] + ". Using default port: " + port);
                }
            case 1:
                host = args[0];
        }
        
        Socket sock = null;

        try {
            sock = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.err.println("host unknown.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 127.0.0.1.");
            System.exit(1);
        }
       
        final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        final ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        final ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
       
        for (;;) {
            String[] params = stdIn.readLine().split("\\s+");
                
            try {
                if (params.length == 1) {
                    final SerializableMethod m = SerializableMethod.serialize(
                        ParkingLotService.class,
                        params[0]
                    );
                    
                    oos.writeObject(m);
                    oos.writeObject(new Object[]{});
                
                    if (params[0].equals("exit")) {
                        break;
                    }
                } else if(params.length == 2) { 
                    final SerializableMethod m = SerializableMethod.serialize(
                        ParkingLotService.class,
                        params[0],
                        Integer.TYPE
                    );
                    
                    oos.writeObject(m);
                    oos.writeObject(new Object[]{Integer.valueOf(params[1])});
                }
            } catch (NoSuchMethodException e) {
                System.err.println("Method not found: " + params[0]);
                continue;
            }

            try {
                String result = (String) ois.readObject();
                System.out.println("Server response: " + result);
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found.");
                break;
            }                
        }
        
        oos.close();
        ois.close();
        sock.close();
        stdIn.close();
        System.out.println("Client exit.");
    }
}
