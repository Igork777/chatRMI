package handin_3.server.networking;

import handin_3.client.networking.RMIClient;
import handin_3.server.model.ChatManager;
import handin_3.server.model.UserManager;
import handin_3.shared.ClientCallBack;
import handin_3.shared.Event;
import handin_3.shared.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl implements Server {
    private ChatManager chatManager;
    private UserManager userManager;

    public ServerImpl(ChatManager chatManager, UserManager userManager) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            this.chatManager = chatManager;
            this.userManager = userManager;
            System.out.println("Server started");
        } catch (RemoteException e) {
            throw new RuntimeException("Constructor ServerImpl threw an RemoteException in ServerImpl");
        }
    }


    public void startServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("RMIServer", this);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addUser(String nickname, ClientCallBack client) {
        try {
            return userManager.registerUser(nickname, client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeUser(ClientCallBack client) {
        try {
            userManager.removeUser(client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void broadcastMessage(String string, ClientCallBack client) {
        try {
            chatManager.broadcast(string, client);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void broadcastMessage(String string, ClientCallBack client, ArrayList<String> nicknameClients) {
        try {
            chatManager.broadcast(string, client, nicknameClients);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshConnectedUsers() {
        try {
             userManager.broadcastUsersList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userJoinedAnnounce(String userName) {
        try {
            userManager.userJoinedAnnounce(userName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAboutStateOfTheUser(String nickname, Event notifyUser, ClientCallBack client) {
        chatManager.notifyAboutState(nickname, notifyUser, client);
    }


}
