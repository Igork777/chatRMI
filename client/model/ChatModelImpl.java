package handin_3.client.model;

import handin_3.client.networking.Client;
import handin_3.shared.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ChatModelImpl implements ChatModel {
    private Client client;
    private PropertyChangeSupport support;

    public ChatModelImpl(Client client) {
        this.support = new PropertyChangeSupport(this);
        this.client = client;
        this.client.addListener(Event.NotifyInactiveUser.name(), this::sendToChatViewModel);
        this.client.addListener(Event.NotifyActiveUser.name(), this::sendToChatViewModel);
        this.client.addListener(Event.MessagePushNotification.name(), this::sendToChatViewModel);
        this.client.addListener(Event.UserLeftPushNotification.name(), this::sendToChatViewModel);
        this.client.addListener(Event.UserJoinedPushNotification.name(), this::sendToChatViewModel);
        this.client.addListener(Event.AllNicknamesAreReceived.name(), this::sendToChatViewModel);
    }

    private void sendToChatViewModel(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }


    @Override
    public void sendMessage(String message) {
            client.sendMessage(message);
    }

    @Override
    public void sendMessage(String message, ArrayList<String> nicknames) {
            client.sendMessage(message, nicknames);
    }

    @Override
    public void refreshConnectedUsers() {
           client.refreshConnectedUsers();
    }

    @Override
    public void notifyAboutStateOfTheUser(String nickname, Event notifyUser) {
        client.notifyAboutStateOfTheUser(nickname, notifyUser);
    }

    @Override
    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void addListener(String name, PropertyChangeListener listener) {
        support.addPropertyChangeListener(name, listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void removeListener(String name, PropertyChangeListener listener) {
        support.removePropertyChangeListener(name, listener);
    }
}
