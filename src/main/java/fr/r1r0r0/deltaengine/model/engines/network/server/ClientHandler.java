package fr.r1r0r0.deltaengine.model.engines.network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Client handler for a Server. Represents an unique client connected.
 * Contains its own buffer, who will receive all data from the client machine,
 * when its run() method is called.
 */
final class ClientHandler implements Runnable {

    private final Server server;
    private final Socket client;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Collection<Object> buffer;

    /**
     * Default constructor. Creates a client handler for the server.
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
        synchronized (this) {
            try {
                if (inputStream.available() > 0)
                    buffer.addAll((Collection<Object>) inputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send data to bound client
     * @param elements objects collection to send
     */
    public void send(Collection<Object> elements) throws IOException {
        outputStream.writeObject(elements);
    }

    /**
     * Retrieve all data received from the client and reset its buffer
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
     * Initializes internal buffer
     */
    private void initBuffer() {
        buffer = new ArrayList<>();
    }
}
