package handin_3.client.core;

import handin_3.client.networking.Client;
import handin_3.client.networking.RMIClient;


public class ClientFactory {
    private Client client;

    public ClientFactory() {
        this.client = new RMIClient();
    }

    public Client getClient() {
        return client;
    }
}
