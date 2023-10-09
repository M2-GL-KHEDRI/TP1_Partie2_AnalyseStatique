package responses;

import visitors.ClassVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Exercice1_3And5And7 {

    CompilationUnit parse;
    public static ClassVisitor classVisitor = new ClassVisitor();

    public Exercice1_3And5And7(){}

    public Exercice1_3And5And7(CompilationUnit parse){
        this.parse = parse;
        parse.accept(classVisitor);
    }

    public int printMethods(){
        for (var entry : classVisitor.getMethods().entrySet()) {
            System.out.println("\n* Class name : "+entry.getKey()+"");
            System.out.println("* Class methods :");
            for (MethodDeclaration method : entry.getValue()) {
                System.out.println("- "+method.getName().toString());
            }
        }
        return getTotalMethodsNumber();
    }

    public int getTotalMethodsNumber() {return classVisitor.getMethodsvV2().size();}
    public int getTotalVariablesNumber() {
        return classVisitor.getVariables().size();
    }




}
