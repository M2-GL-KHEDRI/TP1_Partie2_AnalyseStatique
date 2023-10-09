package responses;

import visitors.ClassVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Exercice1_6 {

    public ClassVisitor classVisitor = new ClassVisitor();
    public int totalMethodsNbr = 0;
    public int totalMethodsLine = 0;

    public double getMethodsLines(){
        Exercice1_3And5And7 question3_5_7 = new Exercice1_3And5And7();
        totalMethodsNbr = question3_5_7.getTotalMethodsNumber();
        for (var entry : classVisitor.getMethods().entrySet()) {
            for (MethodDeclaration method : entry.getValue()) {
                //System.out.println(method.getName().toString());
                //System.out.println(method.getBody().toString());
                //System.out.println(method.getBody().toString().split("\n").length);
                //System.out.println("-----------------\n");
                totalMethodsLine += method.getBody().toString().split("\n").length;
            }
        }

        System.out.println("Explanation for how calculation on lines it goes on : \n" +
                "* We have :\n" +
                "  public static void main(String[] args) {\n" +
                "     Object object = new Object();\n" +
                "     \n" +
                "     object.KillAll();\n" +
                "     //help me!!\n" +
                "     System.out.println(\"good bay\");\n" +
                "  }\n" +
                "\n" +
                "* The body is :\n" +
                " {\n" +
                "    Object object = new Object();\n" +
                "    object.KillAll();\n" +
                "    System.out.println(\"good bay\");\n" +
                " }");
        return totalMethodsLine * 1.00 / totalMethodsNbr;
    }

}
