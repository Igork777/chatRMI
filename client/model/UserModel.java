package handin_3.client.model;

import handin_3.shared.Subject;


public interface UserModel extends Subject {
    boolean createNewUser(String nickname);

    void userJoinedAnnounce(String userName);
}
