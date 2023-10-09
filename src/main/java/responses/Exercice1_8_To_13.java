package responses;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static responses.Exercice1_3And5And7.classVisitor;

/** TODO : Question : 8 9 10 11 12 13 */

public class Exercice1_8_To_13 {

    List<String> classes10methods;
    List<String> classes10attributes;
    List<String> classes10methodsAttributes =  new ArrayList<>();
    List<String> classesXmethods;
    int maxClasses;
    int maxMethods;

    Map<String, MethodDeclaration[]> methods = classVisitor.getMethods();


    public Exercice1_8_To_13(int totalClassesNbr, int totalMethodsNbr) {
        double doubleNumber = totalClassesNbr*10.0/100;
        maxClasses = (int) doubleNumber;
        if (maxClasses == 0 || (doubleNumber - maxClasses) >= 0.5 ) maxClasses += 1;

        doubleNumber = totalMethodsNbr*10.0/100;
        maxMethods = (int) doubleNumber;
        if (maxMethods == 0 || (doubleNumber - maxMethods) >= 0.5 ) maxMethods += 1;
    }

    public List<String> get10ClassesWithMaxMethods(boolean show) {
        classes10methods =  new ArrayList<>();
        Map<String, MethodDeclaration[]> methodsTemp = new HashMap<>();
        for (var entry : classVisitor.getMethods().entrySet()) methodsTemp.put(entry.getKey(),entry.getValue());

        int max = 0;
        String name = "";
        for (int i = 1; i <= maxClasses; i++) {
            for (var entry : methods.entrySet()) {
                if (max < entry.getValue().length) {
                    max = entry.getValue().length;
                    name = entry.getKey();
                }
            }
            classes10methods.add(name);
            max = 0;
            methodsTemp.remove(name);
        }
        if (show) classes10methods.forEach(classeName-> System.out.println("-"+classeName));
        return classes10methods;
    }

    public List<String> get10ClassesWithMaxAttirbutes(boolean show) {
        classes10attributes =  new ArrayList<>();
        Map<String, List<String>> attributesTemp = new HashMap<>();
        for (var entry : classVisitor.getParentVariables(false).entrySet()) attributesTemp.put(entry.getKey(),entry.getValue());

        int max = 0;
        String name = "";
        for (int i = 1; i <= maxClasses; i++) {
            for (var entry : attributesTemp.entrySet()) {
                if (max < entry.getValue().size()) {
                    max = entry.getValue().size();
                    name = entry.getKey();
                }
            }
            classes10attributes.add(name);
            max = 0;
            attributesTemp.remove(name);
        }
        if (show) classes10attributes.forEach(classeName-> System.out.println("-"+classeName));
        return classes10attributes;
    }

    public List<String> get10ClassesWithMaxMethodsAttributes() {
        get10ClassesWithMaxMethods(false);
        get10ClassesWithMaxAttirbutes(false);
        classes10methods.forEach(cm->{classes10attributes.forEach(ct->{if(cm.equals(ct))classes10methodsAttributes.add(cm);});});
        classes10methodsAttributes.forEach(classeName-> System.out.println("-"+classeName));
        return classes10methodsAttributes;
    }


    public List<String> getClassesWithMoreThanXMethods(int x) {
        classesXmethods =  new ArrayList<>();
        for (var entry : methods.entrySet()) if (x <= entry.getValue().length) classesXmethods.add(entry.getKey());
        classesXmethods.forEach(classeName-> System.out.println("-"+classeName));
        return classesXmethods;
    }


    public Map<String, List<String>> get10MethodsWithMaxLines() {

        Map<String, List<String>> methods10lines = new HashMap<>();
        Map<String, MethodDeclaration[]> methodsTemp = new HashMap<>();
        for (var entry : classVisitor.getMethods().entrySet()) methodsTemp.put(entry.getKey(),entry.getValue());
        List<String> temp = new ArrayList<>();
        int max = 0;
        String name = "";

        for (var entry : methodsTemp.entrySet()) {
            double doubleNumber = entry.getValue().length*10.0/100;
            int maxMethodsPerClass = (int) doubleNumber;
            if (maxMethodsPerClass == 0 || (doubleNumber - maxMethodsPerClass) >= 0.5 ) maxMethodsPerClass += 1;
            for (int i = 1; i <= maxMethodsPerClass; i++) {
                for (MethodDeclaration method : entry.getValue()) {
                    if (!method.getName().toString().equals(name))
                        if (max <= method.getBody().toString().split("\n").length) {
                            max = method.getBody().toString().split("\n").length;
                            name = method.getName().toString();
                        }
                }
                temp.add(name);
                max = 0;
            }
            methods10lines.put(entry.getKey(),temp);
            temp = new ArrayList<>();
        }

        for (var entry : methods10lines.entrySet()) {
            System.out.println("Classe name : "+entry.getKey());
            System.out.println("10% of Methods with largest number of lines of code are : ");
            for (String method : entry.getValue())
                System.out.println("-"+method);
        }
        return methods10lines;
    }

    public void getMaxNumberOfParamsInMethods() {
        String methodName = "";
        int max = 0;
        for (int i = 1; i <= maxMethods; i++)
            for (var entry : methods.entrySet())
                for (MethodDeclaration method : entry.getValue()) {
                    if (max <= method.parameters().size()) {
                        max = method.parameters().size();
                        methodName = method.getName().toString();
                    }
                }

        System.out.println("The maximum number of method parameters compared to all methods of the app = "+max);
        System.out.println("Method name that contain this max number : "+methodName);

    }

}
