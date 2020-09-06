package handin_3.client.model;


import handin_3.client.networking.Client;
import handin_3.shared.Event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class UserModelImpl implements UserModel {

    private Client client;
    private PropertyChangeSupport support;

    public UserModelImpl(Client client) {
        support = new PropertyChangeSupport(this);
        this.client = client;

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


    @Override
    public boolean createNewUser(String nickname) {
            return client.addUser(nickname);
    }

    @Override
    public void userJoinedAnnounce(String userName) {
        client.userJoinedAnnounce(userName);
    }
}
