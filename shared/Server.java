package handin_3.shared;

import handin_3.client.networking.RMIClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote {
    boolean addUser(String name, ClientCallBack client) throws RemoteException;

    void removeUser(ClientCallBack client) throws RemoteException;

    void broadcastMessage(String string, ClientCallBack client) throws RemoteException;

    void broadcastMessage(String string, ClientCallBack client, ArrayList<String> nicknameClients) throws RemoteException;

    void refreshConnectedUsers() throws RemoteException;

    void userJoinedAnnounce(String userName) throws RemoteException;

    void notifyAboutStateOfTheUser(String nickname, Event notifyUser, ClientCallBack client) throws RemoteException;
}
