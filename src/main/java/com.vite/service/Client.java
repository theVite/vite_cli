package com.vite.service;

/**
 * Defines what clients will look like
 */
public interface Client {
    /**
     * Closes the connection with the server
     */
    void close();

    /**
     * Pings the server
     */
    String pingServer();
}
