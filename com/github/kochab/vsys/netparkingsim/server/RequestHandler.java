package com.github.kochab.vsys.netparkingsim.server;

import java.io.OutputStream;
import java.io.IOException;

/**
 * A Parking request handler for Parking lot operations.
 *
 * @author Matthias Siegmund
 *
 */

public interface RequestHandler {
    /**
     * Handles a 'free' request.
     */
    void handleFree(OutputStream out) throws IOException;
    
    /**
     * Handles an 'in' (Park) request.
     */
    void handleIn(OutputStream out) throws IOException;
    
    /**
     * Handles an 'out' (Unpark) request.
     */
    void handleOut(OutputStream out) throws IOException;
    
    /**
     * Handles a 'quit' request.
     */
    void handleQuit(OutputStream out) throws IOException;
    
    /**
     * Handles an unidentified request.
     */
    void handleUnknown(OutputStream out, String data) throws IOException; 
}
