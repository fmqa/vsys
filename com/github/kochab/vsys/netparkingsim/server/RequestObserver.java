package com.github.kochab.vsys.netparkingsim.server;

/**
 * Observer for Parking requests.
 *
 *
 * @author Matthias Siegmund
 *
 */

public interface RequestObserver {
    /**
     * Called on a free request.
     */
    void onFree();
    
    /**
     * Called on an 'in' (Park) request.
     */
    void onIn();
    
    /**
     * Called on an 'out' (Unpark) request.
     */
    void onOut();
    
    /**
     * Called on a 'quit' request.
     */
    void onQuit();
    
    /**
     * Called on an unidentified request.
     *
     * @param data The request data
     */
    void onUnknown(String data);
}
