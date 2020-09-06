package handin_3.client.core;

import handin_3.client.view.ViewController;
import handin_3.shared.Subject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private Stage stage;

    public ViewHandler(ViewModelFactory viewModelFactory, Stage stage) {
        this.viewModelFactory = viewModelFactory;
        this.stage = stage;
    }

    public void start() throws IOException {
        System.out.println("GUI init");
        Scene scene = null;
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathToView("UserView")));
        root = loader.load();

        ViewController ctrl = loader.getController();
        ctrl.init(viewModelFactory.getUserViewModel(), this);

        stage.setTitle("Chat!");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String pathToView(String view) {
        return "../view/" + view + ".fxml";
    }

    public void openView(String view, Subject vm) throws IOException {
        System.out.println("The chat window should be opened");
        Scene scene = null;
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathToView(view)));
        root = loader.load();

        ViewController ctrl = loader.getController();
        ctrl.init(vm, this);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ViewModelFactory getVMFactory() {
        return viewModelFactory;
    }
}
