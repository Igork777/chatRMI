package handin_3.client.core;

import handin_3.client.viewModel.ChatViewModel;
import handin_3.client.viewModel.UserViewModel;

public class ViewModelFactory {
    private UserViewModel userViewModel;
    private ChatViewModel chatViewModel;
    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory mf) {
        modelFactory = mf;
        this.userViewModel = new UserViewModel(modelFactory.getUserModel());
        this.chatViewModel = new ChatViewModel(modelFactory.getChatModel());
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public ModelFactory getModelFactory() {
        return modelFactory;
    }
}
