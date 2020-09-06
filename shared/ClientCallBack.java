package handin_3.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;

public interface ClientCallBack extends Remote {
    void receiveMessage(String message) throws RemoteException;

    void fireAllNicknamesCallback(ArrayList<String> string) throws RemoteException;

    void userJoinedPushNotification(String nickname) throws RemoteException;

    void userLeftPushNotification(String nickname) throws RemoteException;


    void disableUser(String nickname) throws RemoteException;

    void enableUser(String nickname) throws RemoteException;
}
