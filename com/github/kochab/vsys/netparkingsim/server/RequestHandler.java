package com.github.kochab.vsys.netparkingsim.server;

import java.io.OutputStream;
import java.io.IOException;

public interface RequestHandler {
    void handleFree(OutputStream out) throws IOException;
    void handleIn(OutputStream out) throws IOException;
    void handleOut(OutputStream out) throws IOException;
    void handleQuit(OutputStream out) throws IOException;
    void handleUnknown(OutputStream out, String data) throws IOException; 
}
