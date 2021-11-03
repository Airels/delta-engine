package fr.r1r0r0.deltaengine.model.engines.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
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
     *
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
                    try {
                        clients.add(client);
                        client.start();
                    } finally {
                        lock.writeLock().unlock();
                    }
                } catch (SocketException e) {
                    // ignored
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
            List<ClientHandler> pendingDeletionClients = new ArrayList<>();
            lock.readLock().lock();
            try {
                for (ClientHandler client : clients) {
                    if (client.isClosed())
                        pendingDeletionClients.add(client);
                }
            } finally {
                lock.readLock().unlock();
            }

            if (pendingDeletionClients.size() > 0) {
                lock.writeLock().lock();
                try {
                    clients.removeAll(pendingDeletionClients);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }

    /**
     * Send collection of objects to all clients (broadcast)
     *
     * @param objects objects to send
     */
    public void send(Object... objects) {
        lock.readLock().lock();
        try {
            for (ClientHandler client : clients) {
                try {
                    client.send(objects);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Send collection of objects to a specific client
     *
     * @param clientIndex index of specific client
     * @param objects     objects to send
     * @throws IOException when socket exception occurs
     */
    public void sendToClient(int clientIndex, Object... objects) throws IOException {
        lock.readLock().lock();
        try {
            ClientHandler selectedClient = clients.get(clientIndex);
            selectedClient.send(objects);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieve all data received by clients until call of this method.
     *
     * @return List of collection of objects for each client
     */
    public List<Collection<Object>> receive() {
        List<Collection<Object>> clientsDataReceived = new LinkedList<>();

        lock.readLock().lock();
        try {
            for (ClientHandler client : clients) {
                clientsDataReceived.add(client.receive());
            }
        } finally {
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
     *
     * @param clientIndex index of specific client
     */
    public void closeClient(int clientIndex) {
        ClientHandler selectedClient = clients.get(clientIndex);
        selectedClient.close();
    }

    @Override
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * Returns number of connected clients currently
     *
     * @return integer number of connected clients
     */
    public int nbConnectedClients() {
        lock.readLock().lock();
        try {
            return clients.size();
        } finally {
            lock.readLock().unlock();
        }
    }
}
