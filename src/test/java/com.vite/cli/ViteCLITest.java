package com.vite.cli;

import com.vite.service.Client;
import com.vite.service.DefaultClient;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests that the cli works as expected
 */
public class ViteCLITest extends org.hamcrest.Matchers {
    private CLI cli;
    private ByteArrayOutputStream outputStream;
    private InputStream inputStream;
    private static ClassLoader classLoader;
    private static Client client;

    @BeforeClass
    public static void setClassLoader() {
        classLoader = Thread.currentThread().getContextClassLoader();
        client = new DefaultClient();
    }

    @Before
    public void setup() {
        inputStream = null;
        outputStream = new ByteArrayOutputStream();
        cli = new ViteCLI(System.in, outputStream, client);
    }

    /**
     * Tests that ping works as expected
     */
    @Test
    public void testPing() {
        assertThat(cli.ping(), is(equalTo("")));
    }

    @After
    public void cleanup() throws IOException {
        outputStream.close();
    }
}
