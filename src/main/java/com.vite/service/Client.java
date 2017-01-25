package com.vite.service;

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
