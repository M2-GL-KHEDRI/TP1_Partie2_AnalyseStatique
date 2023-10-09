package GUI_Package;

import java.util.ArrayList;
import java.util.List;

public class Method {
    
    List<Method> listeMethode;
    String name;

    private int id = 0;
    static int idt = 0;

    public Method(String name) {
        idt++;
        this.id = idt;
        this.name = name;
        this.listeMethode = new ArrayList<Method>();
    }

    public Method(){
        idt++;
        this.id = idt;
        listeMethode = new ArrayList<>();
    }
    
    public void addCall(Method call) {
        this.listeMethode.add(call);
    }
    
    public List<Method> getAllCalls(){
        return this.listeMethode;
    }

    public List<Method> getListeMethode() {
        return listeMethode;
    }

    public void setListeMethode(List<Method> listeMethode) {
        this.listeMethode = listeMethode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
