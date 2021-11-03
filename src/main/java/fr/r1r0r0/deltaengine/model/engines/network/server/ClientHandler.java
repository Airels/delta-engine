package fr.r1r0r0.deltaengine.model.engines.network.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Client handler for a Server. Represents an unique client connected.
 * Contains its own buffer, who will receive all data from the client machine,
 * when its run() method is called.
 */
final class ClientHandler extends Thread {

    private final Server server;
    private final Socket client;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Collection<Object> buffer;

    /**
     * Default constructor. Creates a client handler for the server.
     *
     * @param server the server
     * @param client client socket
     * @throws IOException when socket exception occurs
     */
    ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());
        initBuffer();
    }

    @Override
    public void run() {
        while (!client.isClosed()) {
            try {
                Object receivedObj = inputStream.readObject();

                synchronized (this) {
                    buffer.add(receivedObj);
                }
            } catch (EOFException | SocketException e) {
                close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send data to bound client
     *
     * @param objects objects to send
     * @throws IOException default exceptions thrown by Java libraries
     */
    public void send(Object... objects) throws IOException {
        for (Object obj : objects)
            outputStream.writeObject(obj);
    }

    /**
     * Retrieve all data received from the client and reset its buffer
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
     * End client connection
     */
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if connection has been closed
     *
     * @return boolean, true if connection is closed, false otherwise
     */
    public boolean isClosed() {
        return client.isClosed();
    }

    /**
     * Initializes internal buffer
     */
    private void initBuffer() {
        buffer = new ArrayDeque<>();
    }
}
