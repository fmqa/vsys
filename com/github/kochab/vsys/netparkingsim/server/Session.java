package com.github.kochab.vsys.netparkingsim.server;

import java.net.InetAddress;

public interface Session extends Runnable {
    InetAddress getClientAddress();
    int getClientPort();
}
