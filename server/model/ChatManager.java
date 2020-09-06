package handin_3.server.model;

import handin_3.shared.ClientCallBack;
import handin_3.shared.Event;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatManager {
    void broadcast(String message, ClientCallBack originalClient) throws RemoteException;

    void broadcast(String message, ClientCallBack originalClient, ArrayList<String> nickNamesClientsToWrite) throws RemoteException;

    void notifyAboutState(String nickname, Event notifyUser, ClientCallBack client);
}
