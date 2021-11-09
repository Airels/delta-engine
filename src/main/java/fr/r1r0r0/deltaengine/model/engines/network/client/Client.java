package fr.r1r0r0.deltaengine.model.engines.network.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * Client-side class of Network DeltaEngine package.
 * Uses Socket of Java, you connect to the server once constructor is called.
 * When connected, you can send objects to the server or receive objects from the server.
 */
public final class Client extends Thread {

    private final Socket connection;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Collection<Object> buffer;
    private volatile boolean interrupted;

    /**
     * Default constructor.
     * It will establish a connection with given server address and port
     *
     * @param address server address
     * @param port    used server port
     * @throws IOException for any exceptions thrown by Socket library
     */
    public Client(String address, int port) throws IOException {
        connection = new Socket(address, port);
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        inputStream = new ObjectInputStream(connection.getInputStream());
        initBuffer();
    }

    @Override
    public void run() {
        while (!interrupted) {
            try {
                Object receivedObj = inputStream.readObject();

                synchronized (this) {
                    buffer.add(receivedObj);
                }
            } catch (EOFException e) {
                close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send data to bound server
     *
     * @param objects objects to send
     * @throws IOException default exceptions thrown by Java libraries
     */
    public void send(Object... objects) throws IOException {
        for (Object obj : objects)
            outputStream.writeObject(obj);
    }

    /**
     * Retrieve all data received from the server and reset its buffer
     *
     * @return Collection of objects received
     */
    public synchronized Collection<Object> receive() {
        try {
            return buffer;
        } finally {
            initBuffer();
        }
    }

    /**
     * Initializes internal buffer
     */
    private void initBuffer() {
        buffer = new ArrayDeque<>();
    }

    /**
     * End connection with the server
     */
    public void close() {
        interrupted = true;
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInterrupted() {
        return interrupted;
    }
}
