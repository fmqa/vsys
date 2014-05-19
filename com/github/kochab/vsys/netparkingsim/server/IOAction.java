package com.github.kochab.vsys.netparkingsim.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * An I/O operation involving an Input and an Output stream.
 *
 * @author Fadi Moukayed
 *
 */

public interface IOAction {
    /**
     * Executes the IO operation.
     *
     * @param in The input stream this IO operation will read from
     * @param out The output stream this IO operation will write to
     */
    void apply(InputStream in, OutputStream out) throws IOException;
}
