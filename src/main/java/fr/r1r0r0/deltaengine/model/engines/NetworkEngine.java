package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.IllegalCallException;
import fr.r1r0r0.deltaengine.model.engines.network.client.Client;
import fr.r1r0r0.deltaengine.model.engines.network.server.Server;

import java.io.IOException;

public final class NetworkEngine implements Engine {

    private Server server;
    private Client client;

    NetworkEngine() {

    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        if (server != null && server.isInterrupted()) server = null;
        else if (client != null && client.isInterrupted()) client = null;
    }

    public Server createServer(int port) throws IllegalCallException {
        if (server != null && !server.isInterrupted())
            throw new IllegalCallException("A server is already running, close it before creating a new one");
        if (client != null && !client.isInterrupted())
            throw new IllegalCallException("NetworkEngine is already running a client, can't init a server");

        try {
            server = new Server(port);
            server.start();
        } catch (IOException e) {
            DeltaEngine.showEngineError(e);
        }

        return server;
    }

    public Client createClient(String ipAddress, int port) throws IllegalCallException {
        if (client != null && !client.isInterrupted())
            throw new IllegalCallException("A client is already running, close it before creating a new one");
        if (server != null && !server.isInterrupted())
            throw new IllegalCallException("NetworkEngine is already running a server, can't init a client");


        try {
            client = new Client(ipAddress, port);
            client.start();
        } catch (IOException e) {
            DeltaEngine.showEngineError(e);
        }

        return client;
    }

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
    }
}
