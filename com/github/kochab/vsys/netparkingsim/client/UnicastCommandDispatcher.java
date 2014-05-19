package com.github.kochab.vsys.netparkingsim.client;

import java.net.InetAddress;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A command dispatchers that sends commands to a single endpoint.
 *
 * @author Fadi Moukayed
 * @author Matthias Siegmund
 */

public class UnicastCommandDispatcher implements CommandDispatcher {
    /**
     * Creates a command dispatcher for the given endpoint.
     *
     * @param addr Endpoint address
     * @param port Endpoint port
     */
    public UnicastCommandDispatcher(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }
    
    @Override
    public String execute(String cmd) {
        String resp = null;
        try {
            Socket csock = new Socket(addr, port);
            PrintWriter out = new PrintWriter(csock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(csock.getInputStream())
            );
            
            out.println(cmd);
            resp = in.readLine();
            
            out.close();
            in.close();
            csock.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return resp;
    }
    
    private final InetAddress addr;
    private final int port;
}

