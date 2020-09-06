package handin_3.client.core;

import handin_3.client.model.ChatModel;
import handin_3.client.model.ChatModelImpl;
import handin_3.client.model.UserModel;
import handin_3.client.model.UserModelImpl;
import handin_3.client.networking.Client;


public class ModelFactory {
    private UserModel userModel;
    private ChatModel chatModel;
    private ClientFactory clientFactory;

    public UserModel getUserModel() {
        return userModel;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public ModelFactory(ClientFactory cf) {
        clientFactory = cf;
        Client client = clientFactory.getClient();
        userModel = new UserModelImpl(client);
        chatModel = new ChatModelImpl(client);
    }
}
