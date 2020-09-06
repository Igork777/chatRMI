package handin_3.server.model;

import handin_3.shared.ClientCallBack;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UserManager {
    boolean registerUser(String name, ClientCallBack client) throws RemoteException;

    void removeUser(ClientCallBack client) throws RemoteException;

    void broadcastUsersList() throws RemoteException;
    void userJoinedAnnounce(String userName) throws RemoteException;
}
