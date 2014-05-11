package com.github.kochab.vsys.netparkingsim.server;

import java.io.OutputStream;
import java.io.IOException;

public class RequestObservingDecorator implements RequestHandler {
    public RequestObservingDecorator(RequestHandler handler, RequestObserver observer) {
        this.handler = handler;
        this.observer = observer;
    }
    
    @Override
    public void handleFree(OutputStream out) throws IOException {
        observer.onFree();
        handler.handleFree(out);
    }
    
    @Override
    public void handleIn(OutputStream out) throws IOException {
        observer.onIn();
        handler.handleIn(out);
    }
    
    @Override
    public void handleOut(OutputStream out) throws IOException {
        observer.onOut();
        handler.handleOut(out);
    }
    
    @Override
    public void handleQuit(OutputStream out) throws IOException {
        observer.onQuit();
        handler.handleQuit(out);
    }
    
    @Override
    public void handleUnknown(OutputStream out, String data) throws IOException {
        observer.onUnknown(data);
        handler.handleUnknown(out, data);
    }
    
    private final RequestHandler handler;
    private final RequestObserver observer;
}
