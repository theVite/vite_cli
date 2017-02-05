package com.vite.service;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple client for proof of concept.
 */
public class ViteClient implements Client{
    private TTransport transport;
    private Service.Client client;
    private static Logger LOGGER = LoggerFactory.getLogger(ViteClient.class);

    public ViteClient() {
        try {
            LOGGER.debug("Creating Vite Client...");
            transport = new TSocket("127.0.0.1", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new Service.Client(protocol);
        } catch (TException ex) {
            LOGGER.error("Could not open socket", ex);
        }
    }

    @Override
    public String pingServer() {
        String response = null;
        try {
            response = client.ping();
        } catch (TException ex) {
            LOGGER.error("Error Pinging the server", ex);
        }

        return response;
    }

    @Override
    public void close() {
        if (transport.isOpen()) {
            transport.close();
        }
    }
}
