package handin_3.server.model;

import handin_3.shared.ClientCallBack;
import handin_3.shared.Event;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatManagerImpl implements ChatManager {
    private final static HashMap<String, ClientCallBack> users = Storage.getUsersDatabase();

    @Override
    public void broadcast(String message, ClientCallBack originalClient) {

        String nickName = findProperNickname(originalClient);
        for (Map.Entry<String, ClientCallBack> entry : users.entrySet()) {
            try {
                if (entry.getValue().equals(originalClient)) {
                    entry.getValue().receiveMessage("You : " + message);
                } else {
                    entry.getValue().receiveMessage(nickName + " : " + message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void broadcast(String message, ClientCallBack originalClient, ArrayList<String> nickNamesClientsToWrite) {
        String nickName = findProperNickname(originalClient);

        for (Map.Entry<String, ClientCallBack> entry : users.entrySet()) {
            try {
                if (entry.getValue().equals(originalClient)) {
                    originalClient.receiveMessage("You: " + message);
                    continue;
                }
                for (String string : nickNamesClientsToWrite) {
                    if (entry.getKey().equals(string)) {
                        entry.getValue().receiveMessage(nickName + " : " + message);
                        break;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void notifyAboutState(String nickname, Event notifyUser, ClientCallBack client) {
        try {
            if (notifyUser.compareTo(Event.NotifyInactiveUser) == 0) {
                for (ClientCallBack cli: users.values()) {
                    cli.receiveMessage(nickname + " is inactive");
                }
                client.disableUser(nickname);
                }
            else if (notifyUser.compareTo(Event.NotifyActiveUser) == 0){
                client.enableUser(nickname);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private String findProperNickname(ClientCallBack client) {
        for (Map.Entry<String, ClientCallBack> entry : users.entrySet()) {
            if (entry.getValue().equals(client)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Impossible outcome");
    }
}
