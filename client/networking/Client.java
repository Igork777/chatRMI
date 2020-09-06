package handin_3.client.networking;


import handin_3.shared.ClientCallBack;
import handin_3.shared.Event;
import handin_3.shared.Subject;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends Subject {
    boolean addUser(String nickName);

    void sendMessage(String message);

    void sendMessage(String message, ArrayList<String> nicknameClients);

     void refreshConnectedUsers();

    void removeUser();

    void userJoinedAnnounce(String userName);

    void notifyAboutStateOfTheUser(String nickname, Event notifyInactiveUser);
}
