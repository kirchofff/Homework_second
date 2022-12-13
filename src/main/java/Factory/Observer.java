package Factory;

import Helpers.Listener;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    private List <Listener> listeners = new ArrayList<>();

    public List<Listener> getListeners() {
        return listeners;
    }
    public void addListeners(Listener l) {
        if (!listeners.contains(l)){
            listeners.add(l);
            System.out.println("Listener added: "+l);
        }

    }
    public void removeListeners (Listener l){
        System.out.println("Listener removed: "+l);
        listeners.remove(l);
    }
    public void notifyListeners (){
        System.out.println("All listeners: "+listeners.toString());
    }
}
