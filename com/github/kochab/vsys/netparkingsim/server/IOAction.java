package com.github.kochab.vsys.netparkingsim.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public interface IOAction {
    void apply(InputStream in, OutputStream out) throws IOException;
}
