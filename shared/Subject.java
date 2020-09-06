package handin_3.shared;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public interface Subject {
    void addListener(PropertyChangeListener listener);

    void addListener(String name, PropertyChangeListener listener);

    void removeListener(PropertyChangeListener listener);

    void removeListener(String name, PropertyChangeListener listener);
}
