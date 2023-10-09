package GUI_Package;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import responses.Exercice1_3And5And7;
import visitors.MethodInvocationVisitor;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    List<Method> listMethod = new ArrayList<>();

    public void startProcess(){
        callGraph(this.listMethod);
        new GUI(listMethod).setVisible(true);
    }


    public MethodInvocationVisitor createMethodVisitor(MethodDeclaration method) {
        MethodInvocationVisitor methodVisitor = new MethodInvocationVisitor(method);
        Block block = method.getBody();
        block.accept(methodVisitor);
        return methodVisitor;
    }

    public void callGraph(List<Method> listMethod) {

        List<MethodDeclaration> methods = Exercice1_3And5And7.classVisitor.getMethodsvV2();

        if (methods.size() > 0) {
            for (MethodDeclaration method : methods) {

                Method stMethod = new Method();
                stMethod.setName(method.getName().toString());

                MethodInvocationVisitor methodVisitor = createMethodVisitor(method);

                methodVisitor.callGraph(stMethod);
                listMethod.add(stMethod);

            }
        }
    }


}
