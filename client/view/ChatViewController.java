package handin_3.client.view;

import com.Semester2.FourthLesson.datavisualizerwithobserver.RunDataVisualizer;
import com.Semester2.OpenClosedPrinciple.Item;
import handin_3.client.core.ViewHandler;
import handin_3.client.viewModel.ChatViewModel;
import handin_3.shared.Event;
import handin_3.shared.Subject;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatViewController implements ViewController {
    @FXML
    public Label userNameLabel;
    @FXML
    public Label activeButton;
    @FXML
    private TextField messageTextField;
    @FXML
    private TableView<String> usersTable;
    @FXML
    private TableColumn<String, String> userColumn;
    @FXML
    private TextArea allMessagesTextArea;

    private ArrayList<String> nameOfTheUser = new ArrayList<>();
    private ChatViewModel chatViewModel;
    private ViewHandler viewHandler;
    private Thread deactivationOfTheUser;

    @Override
    public void init(Subject vm, ViewHandler vh) {
        chatViewModel = (ChatViewModel) vm;
        viewHandler = vh;
        chatViewModel.addListener(Event.AllNicknamesAreReceived.name(), this::onAllNicknamesAreReceived);
        chatViewModel.addListener(Event.NotifyInactiveUser.name(), this::onNotifyInactive);
        chatViewModel.addListener(Event.NotifyActiveUser.name(), this::onNotifyActive);
        usersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userNameLabel.textProperty().bind(chatViewModel.getUserNameStringProperty());
        chatViewModel.getMessageStringProperty().bindBidirectional(messageTextField.textProperty());
        allMessagesTextArea.textProperty().bind(chatViewModel.getAllMessagesTextAreaStringAreaProperty());
        allMessagesTextArea.setWrapText(true);
        messageTextField.setFocusTraversable(true);
        messageTextField.requestFocus();
        userColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));

        chatViewModel.refreshUsers();
        deactivationOfTheUser = getNewThread();
        deactivationOfTheUser.start();
        messageTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                deactivationOfTheUser.interrupt();
                chatViewModel.notifyAboutStateOfTheUser(userNameLabel.getText(), Event.NotifyActiveUser);
                System.out.println("Somebody writes the message");
                deactivationOfTheUser = getNewThread();
                deactivationOfTheUser.start();
            }
        });
    }

    private void onNotifyActive(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                activeButton.setText("Active");
                activeButton.setTextFill(Color.GREEN);
            }
        });
    }

    private void onNotifyInactive(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                    activeButton.setText("Inactive");
                    activeButton.setTextFill(Color.RED);
            }
        });

    }


    private Thread getNewThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("The user is not active");
                    chatViewModel.notifyAboutStateOfTheUser(userNameLabel.getText(), Event.NotifyInactiveUser);

                } catch (InterruptedException e) {
                }
            }
        });
    }

    private void onAllNicknamesAreReceived(PropertyChangeEvent propertyChangeEvent) {
        usersTable.getItems().clear();
        usersTable.getItems().addAll((ArrayList<String>) propertyChangeEvent.getNewValue());
    }

    public void onSend(ActionEvent actionEvent) {
        if (nameOfTheUser.isEmpty()) {
            chatViewModel.sendMessage(messageTextField.textProperty().getValue());
        } else {
            chatViewModel.sendMessage(messageTextField.textProperty().getValue(), nameOfTheUser);
        }
        messageTextField.setText("");
        allMessagesTextArea.setScrollTop(Double.MAX_VALUE);
        nameOfTheUser.clear();
    }

    public void onTableClick(MouseEvent mouseEvent) {
        nameOfTheUser.add(usersTable.getSelectionModel().getSelectedItem());
    }
}
