package com.github.kochab.vsys.rpcparkingsim;

import java.util.Arrays;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * An RPC-based parking lot session handler.
 *
 * @author Fadi Moukayed
 * @author Eugen Kinder
 * @author Matthias Siegmund
 */

public final class Session implements Runnable {
    /**
     * Creates a parking session with the given service object and IO streams.
     *
     * @param service The service object that will be used
     * @param in The request data stream
     * @param out The response data stream
     */
    public Session(ParkingLotService service, ObjectInputStream in, ObjectOutputStream out) {
        this.service = service;
        this.in = in;
        this.out = out;
    }
    
    /**
     * Runs the session request handler.
     */
    @Override
    public void run() {
        while (serveOnce());
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Failed to close IO streams");
        }
    }
    
    /**
     * Handles a single request.
     *
     * @return true if the request was handled successfully, false otherwise.
     */
    public boolean serveOnce() {
        SerializableMethod m = null;
        Object[] args = null;
        
        try {
            m = (SerializableMethod)in.readObject();
            args = (Object[])in.readObject();
        } catch (IOException e) {
            System.err.println("Failed to read RPC data");
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
            return false;
        }
        
        try {
            System.out.println("Requested method: " + m.method() + ", with arguments: " + Arrays.toString(args));
            
            switch (args.length) {
                case 0:
                    out.writeObject(m.method().invoke(service));
                    break;
                case 1:
                    out.writeObject(m.method().invoke(service, args[0]));
                    break;
                case 2:
                    out.writeObject("Invalid arity: " + args.length);
                    return false;
            }
        } catch (IOException e) {
            System.err.println("Failed to write response data");
            return false;
        } catch (Exception e) {
            System.err.println("Failed to invoke RPC method");
            return false;
        }
        
        return true;
    }
    
    private final ParkingLotService service;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
}

