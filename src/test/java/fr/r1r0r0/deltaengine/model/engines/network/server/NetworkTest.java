package fr.r1r0r0.deltaengine.model.engines.network.server;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.IllegalCallException;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.NetworkEngine;
import fr.r1r0r0.deltaengine.model.engines.network.client.Client;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    @Test
    public void testCreation() throws AlreadyInitializedException, IllegalCallException, IOException {
        KernelEngine kernelEngine = DeltaEngine.launch();
        NetworkEngine networkEngine = kernelEngine.getNetworkEngine();

        assertNull(networkEngine.getServer());
        assertNull(networkEngine.getClient());

        Server server = networkEngine.createServer(3003);
        ;
        assertFalse(server.isInterrupted());

        assertThrows(IllegalCallException.class, () -> {
            networkEngine.createClient("127.0.0.1", 3003);
        });

        server.close();
        assertTrue(server.isInterrupted());


        Server s = new Server(3003);
        s.start();
        Client client = networkEngine.createClient("127.0.0.1", 3003);

        assertThrows(IllegalCallException.class, () -> {
            networkEngine.createServer(3003);
        });

        assertFalse(client.isInterrupted());
        client.close();
        assertTrue(client.isInterrupted());

        s.close();
    }

    @Test
    public void testConnections() throws IOException, InterruptedException {
        Server server = new Server(3004);
        server.start();
        assertFalse(server.isInterrupted());
        assertEquals(0, server.nbConnectedClients());

        Client client = new Client("localhost", 3004);
        Client client2 = new Client("localhost", 3004);
        client.start();
        client2.start();

        assertFalse(client.isInterrupted());
        assertFalse(client2.isInterrupted());
        assertEquals(2, server.nbConnectedClients());

        client.close();
        assertTrue(client.isInterrupted());
        Thread.sleep(500);
        assertEquals(1, server.nbConnectedClients());

        server.close();
        assertTrue(server.isInterrupted());

        assertTrue(client2.isInterrupted());
    }

    @Test
    public void testTransfer() throws IOException, InterruptedException {
        Server server = new Server(3005);
        server.start();

        Client client = new Client("localhost", 3005);
        client.start();

        assertFalse(server.isInterrupted());
        assertFalse(client.isInterrupted());
        assertEquals(1, server.nbConnectedClients());


        // Server -> Clients
        server.send("Ping!");

        Thread.sleep(1000);

        Collection<Object> objs = client.receive();
        assertEquals(1, objs.size());

        List<Object> objects = new ArrayList<>(objs);
        String msg = (String) objects.get(0);
        assertEquals("Ping!", msg);


        // Client -> Server
        client.send("Pong!");

        Thread.sleep(1000);

        List<Collection<Object>> listObjs = server.receive();
        assertEquals(1, listObjs.size());

        objs = listObjs.get(0);
        assertEquals(1, objs.size());

        objects = new ArrayList<>(objs);
        msg = (String) objects.get(0);
        assertEquals("Pong!", msg);
    }

    @Test
    public void testTransferMultipleClients() throws IOException, InterruptedException {
        Server server = new Server(3006);
        server.start();

        Client client1 = new Client("localhost", 3006);
        Client client2 = new Client("localhost", 3006);
        client1.start();
        client2.start();

        assertEquals(2, server.nbConnectedClients());


        // Server -> Client2
        server.sendToClient(1, "DeltaEngine!");

        Thread.sleep(1000);

        Collection<Object> objectCollection1 = client1.receive();
        Collection<Object> objectCollection2 = client2.receive();

        assertEquals(0, objectCollection1.size());
        assertEquals(1, objectCollection2.size());

        List<Object> objects = new ArrayList<>(objectCollection2);
        String msg = (String) objects.get(0);
        assertEquals("DeltaEngine!", msg);


        // Client1 -> Server
        client1.send("Hello!");

        Thread.sleep(1000);

        List<Collection<Object>> collections = server.receive();

        assertEquals(1, collections.get(0).size());
        assertEquals(0, collections.get(1).size());

        Collection<Object> collection = collections.get(0);
        assertEquals(1, collection.size());

        objects = new ArrayList<>(collection);
        msg = (String) objects.get(0);

        assertEquals("Hello!", msg);
    }
}