package com.github.kochab.vsys.netparkingsim.server;

public interface RequestObserver {
    void onFree();
    void onIn();
    void onOut();
    void onQuit();
    void onUnknown(String data);
}
