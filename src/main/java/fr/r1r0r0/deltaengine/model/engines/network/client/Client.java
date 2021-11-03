package fr.r1r0r0.deltaengine.model.engines.network.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Client extends Thread {

    private final Socket connection;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Collection<Object> buffer;
    private volatile boolean interrupted;

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

    public void send(Object... objects) throws IOException {
        for (Object obj : objects)
            outputStream.writeObject(obj);
    }

    public synchronized Collection<Object> receive() {
        try {
            return buffer;
        } finally {
            initBuffer();
        }
    }

    private void initBuffer() {
        buffer = new ArrayDeque<>();
    }

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
