package fr.r1r0r0.deltaengine.model.engines.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The server implementation for DeltaEngine.
 * Creates a server using java Socket and handle them.
 * It is possible to send or receive collections of objects with connected clients,
 * or send data to specific client
 */
public final class Server extends Thread {

    private final ServerSocket server;
    private final List<ClientHandler> clients;
    private final Thread connectionHandler;
    private final ReadWriteLock lock;
    private volatile boolean interrupted;

    /**
     * Default constructor.
     * Declare a new server with specified port, and prepare to receive new clients
     * when server will be launched.
     * @param port server port
     * @throws IOException exception from ServerSocket when it occurs
     */
    public Server(int port) throws IOException {
        clients = new ArrayList<>();
        server = new ServerSocket(port);
        lock = new ReentrantReadWriteLock(true);
        interrupted = false;

        connectionHandler = new Thread(() -> {
            while (!interrupted) {
                try {
                    ClientHandler client = new ClientHandler(this, server.accept());
                    lock.writeLock().lock();
                    clients.add(client);
                    lock.writeLock().unlock();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void run() {
        connectionHandler.start();

        while (!interrupted) {
            lock.readLock().lock();
            clients.forEach(ClientHandler::run);
            lock.readLock().unlock();
        }
    }

    /**
     * Send collection of objects to all clients (broadcast)
     * @param elements collection to send
     */
    public void send(Collection<Object> elements) {
        for (ClientHandler client : clients) {
            try {
                lock.readLock().lock();
                client.send(elements);
                lock.readLock().unlock();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send collection of objects to a specific client
     * @param elements collection to send
     * @param clientIndex index of specific client
     * @throws IOException when socket exception occurs
     */
    public void sendToClient(Collection<Object> elements, int clientIndex) throws IOException {
        lock.readLock().lock();
        ClientHandler selectedClient = clients.get(clientIndex);
        selectedClient.send(elements);
        lock.readLock().unlock();
    }

    /**
     * Retrieve all data received by clients until call of this method.
     * @return List of collection of objects for each client
     */
    public List<Collection<Object>> receive() {
        List<Collection<Object>> clientsDataReceived = new LinkedList<>();

        for (ClientHandler client : clients) {
            lock.readLock().lock();
            clientsDataReceived.add(client.receive());
            lock.readLock().unlock();
        }

        return clientsDataReceived;
    }

    /**
     * Close server socket, stop accepting new clients, and close all client connections
     */
    public void close() {
        if (interrupted) return;

        interrupted = true;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients.forEach(ClientHandler::close);
    }

    /**
     * Close specific client from all connected clients and remove it from handled clients
     * @param clientIndex index of specific client
     */
    public void closeClient(int clientIndex) {
        ClientHandler selectedClient = clients.get(clientIndex);
        selectedClient.close();
        clients.remove(clientIndex);
    }
}
