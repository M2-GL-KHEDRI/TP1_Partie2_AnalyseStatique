package visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import GUI_Package.Method;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationVisitor extends ASTVisitor {

    MethodDeclaration currentMethod;
    List<MethodInvocation> invocations = new ArrayList<>();

    public MethodInvocationVisitor(MethodDeclaration method) {
        currentMethod = method;
    }

    public boolean visit(MethodInvocation node) {
        invocations.add(node);
        return super.visit(node);
    }

    public Set<String> getMethodInvocationsNames() {
        Set<String> names = new HashSet<>();
        for (MethodInvocation invocation : invocations) {
            names.add(invocation.getName().toString());
        }
        return names;
    }

    public String getMethodReturnType() {
        if (currentMethod.getReturnType2() != null)
            return currentMethod.getReturnType2().toString();
        return null;
    }

    public void callGraph(Method method){

        Set<String> methodCalleds = getMethodInvocationsNames();
        if (methodCalleds.size() > 0) {
            for (String name : methodCalleds) {
                Method call = new Method();
                call.setName(name);
                method.addCall(call);
            }
        }
    }

    public String callGraph() {
        String data = "";
        //String returnType = getMethodReturnType();
        /*if (returnType != null)
            data = data + "\n" + "Method return type : " + returnType;
         */
        Set<String> methodCalleds = getMethodInvocationsNames();

        if (methodCalleds.size() > 0) {
            data = data + "\n";
            data = data + "List of called methods: \n";
            for (String name : methodCalleds) {
                data = data + "- " + name + "\n";
            }
        } else {
            data = data + "\n";
        }
        return data;
    }
}
