package handin_3.client;

import handin_3.client.core.ClientFactory;
import handin_3.client.core.ModelFactory;
import handin_3.client.core.ViewHandler;
import handin_3.client.core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ClientApplication extends Application {
    private ClientFactory clientFactory;

    @Override
    public void start(Stage stage) throws Exception {

        clientFactory = new ClientFactory();
        ModelFactory modelFactory = new ModelFactory(clientFactory);
        ViewModelFactory VMFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(VMFactory, stage);
        viewHandler.start();
    }

    @Override
    public void stop() {
            clientFactory.getClient().removeUser();
    }
}
