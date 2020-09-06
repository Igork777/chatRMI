package handin_3.server.model;

import handin_3.shared.ClientCallBack;

import java.util.HashMap;

public class Storage {
    private static HashMap<String, ClientCallBack> users = new HashMap<>();

    public static HashMap<String, ClientCallBack> getUsersDatabase() {
        return users;
    }
}
