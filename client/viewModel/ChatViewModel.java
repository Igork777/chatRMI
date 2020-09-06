package handin_3.client.viewModel;

import handin_3.client.model.ChatModel;
import handin_3.shared.Event;
import handin_3.shared.Subject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ChatViewModel implements Subject {
    private ChatModel chatModel;
    private SimpleStringProperty userNameStringProperty, messageStringProperty, allMessagesStringProperty;
    private PropertyChangeSupport support;

    public ChatViewModel(ChatModel chatModel) {
        this.chatModel = chatModel;


        userNameStringProperty = new SimpleStringProperty();
        userNameStringProperty.setValue(" ");
        messageStringProperty = new SimpleStringProperty();
        allMessagesStringProperty = new SimpleStringProperty();
        allMessagesStringProperty.setValue("");
        this.chatModel.addListener(Event.NotifyInactiveUser.name(), this::onNotifyStateUser);
        this.chatModel.addListener(Event.NotifyActiveUser.name(), this::onNotifyStateUser);
        this.chatModel.addListener(Event.UserJoinedPushNotification.name(), this::onUserJoinedPushNotification);
        this.chatModel.addListener(Event.UserLeftPushNotification.name(), this::onUserLeftPushNotification);
        this.chatModel.addListener(Event.AllNicknamesAreReceived.name(), this::onAllNicknamesAreReceived);
        this.chatModel.addListener(Event.MessagePushNotification.name(), this::onMessagePushNotification);
        this.support = new PropertyChangeSupport(this);
    }

    private void onNotifyStateUser(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
    }

    public void sendMessage(String message) {
        chatModel.sendMessage(message);
    }

    public void sendMessage(String message, ArrayList<String> users_name) {
        chatModel.sendMessage(message, users_name);
    }

    private void onUserLeftPushNotification(PropertyChangeEvent propertyChangeEvent) {
        appendToStringProperty(allMessagesStringProperty, propertyChangeEvent.getNewValue().toString() + " left the chat =(");
    }

    private void onUserJoinedPushNotification(PropertyChangeEvent propertyChangeEvent) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    userNameStringProperty.setValue(userNameStringProperty.getValue().equals(" ") ? propertyChangeEvent.getNewValue().toString() : userNameStringProperty.getValue());
                }
            });
            appendToStringProperty(allMessagesStringProperty, propertyChangeEvent.getNewValue().toString() + " joined the chat =)");

    }

    private void onMessagePushNotification(PropertyChangeEvent propertyChangeEvent) {

        appendToStringProperty(allMessagesStringProperty, propertyChangeEvent.getNewValue().toString());
    }


    private void onAllNicknamesAreReceived(PropertyChangeEvent propertyChangeEvent) {
        support.firePropertyChange(propertyChangeEvent);
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

    public SimpleStringProperty getUserNameStringProperty() {
        return userNameStringProperty;
    }

    public SimpleStringProperty getMessageStringProperty() {
        return messageStringProperty;
    }


    public SimpleStringProperty getAllMessagesTextAreaStringAreaProperty() {
        return allMessagesStringProperty;
    }

    private void appendToStringProperty(StringProperty stringProperty, String str) {
        stringProperty.setValue(stringProperty.getValue() + "\n" + str);
    }

    public void refreshUsers() {
        chatModel.refreshConnectedUsers();
    }

    public void notifyAboutStateOfTheUser(String nickname, Event notifyUser) {
        chatModel.notifyAboutStateOfTheUser(nickname, notifyUser);
    }
}
