package handin_3.client.model;


import handin_3.shared.Event;
import handin_3.shared.Subject;

import java.util.ArrayList;


public interface ChatModel extends Subject {

    void sendMessage(String message);

    void sendMessage(String message, ArrayList<String> nicknames);

    void refreshConnectedUsers();

    void notifyAboutStateOfTheUser(String nickname, Event notifyUser);
}
