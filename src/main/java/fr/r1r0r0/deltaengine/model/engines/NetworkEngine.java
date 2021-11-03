package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.IllegalCallException;
import fr.r1r0r0.deltaengine.model.engines.network.client.Client;
import fr.r1r0r0.deltaengine.model.engines.network.server.Server;

import java.io.IOException;

/**
 * Network Engine of the DeltaEngine.
 * Manages Client/Server paradigm between computers.
 * A server can handle multiple clients simultaneously.
 * With NetworkEngine, it is impossible to have both Server and Client instance :
 * you must choose what the machine will do (a Server or a Client)
 */
public final class NetworkEngine implements Engine {

    private Server server;
    private Client client;

    /**
     * Default constructor.
     */
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

    /**
     * Allows to create a Server object with given port.
     *
     * @param port server port to use
     * @return a newly created and running server
     * @throws IllegalCallException If a Server instance is already running or a Client instance is already running
     * @throws IOException          if exception occurs on server creation
     */
    public Server createServer(int port) throws IllegalCallException, IOException {
        if (server != null && !server.isInterrupted())
            throw new IllegalCallException("A server is already running, close it before creating a new one");
        if (client != null && !client.isInterrupted())
            throw new IllegalCallException("NetworkEngine is already running a client, can't init a server");

        server = new Server(port);
        server.start();

        return server;
    }

    /**
     * Allows to create a Client object with given address and port
     *
     * @param ipAddress Server address
     * @param port      server port
     * @return a newly created and running client, connected to the server
     * @throws IllegalCallException If a Client instance is already running or a Server instance is already running
     * @throws IOException          if exception occurs on client creation
     */
    public Client createClient(String ipAddress, int port) throws IllegalCallException, IOException {
        if (client != null && !client.isInterrupted())
            throw new IllegalCallException("A client is already running, close it before creating a new one");
        if (server != null && !server.isInterrupted())
            throw new IllegalCallException("NetworkEngine is already running a server, can't init a client");


        client = new Client(ipAddress, port);
        client.start();

        return client;
    }

    /**
     * Allows getting Server instance if it was previously created.
     * If Server wasn't created, it will return a null.
     *
     * @return server instance if Server was created, null otherwise
     */
    public Server getServer() {
        return server;
    }

    /**
     * Allows getting Client instance if it was previously created.
     * If Client wasn't created, it will return a null.
     *
     * @return client instance if Client was created, null otherwise
     */
    public Client getClient() {
        return client;
    }
}
