package com.github.kochab.vsys.netparkingsim.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * A Parking-Lot request handler that dispatches requests to a handler.
 *
 * @author Fadi Moukayed
 */

public class RequestDispatcher implements IOAction {
    /**
     * Constructs a new request dispatcher will the given
     * request handler
     */
    public RequestDispatcher(RequestHandler handler) {
        this.handler = handler;
    }
    
    /**
     * Dispatches a parking request.
     */
    @Override
    public void apply(InputStream in, OutputStream out) throws IOException {
        BufferedReader clIn = new BufferedReader(new InputStreamReader(in));
        String line;
        
        while ((line = clIn.readLine()) != null) {
            if (line.equals("free")) {
                handler.handleFree(out);
            } else if (line.equals("in")) {
                handler.handleIn(out);
            } else if (line.equals("out")) {
                handler.handleOut(out);
            } else if (line.equals("quit")) {
                handler.handleQuit(out);
            } else {
                handler.handleUnknown(out, line);
            }
        }
    }
    
    private final RequestHandler handler;
}
