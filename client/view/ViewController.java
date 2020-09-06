package handin_3.client.view;
import handin_3.shared.Subject;
import handin_3.client.core.ViewHandler;


public interface ViewController {
    void init(Subject vm, ViewHandler vh);
}
