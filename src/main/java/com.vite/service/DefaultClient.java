package com.vite.service;

/**
 * Default implementation of the client interface
 */
public class DefaultClient implements Client {
    @Override
    public void close() {}

    @Override
    public String pingServer() {
        return "";
    }
}
