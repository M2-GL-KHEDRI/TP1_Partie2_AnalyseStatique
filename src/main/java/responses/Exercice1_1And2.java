package responses;

import visitors.PackageVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class Exercice1_1And2 {

    CompilationUnit parse;
    public static PackageVisitor  packageVisitor = new PackageVisitor();

    public Exercice1_1And2(CompilationUnit parse){
        this.parse = parse;
        parse.accept(packageVisitor);
    }

    public int printClasses(){
        int index = 1;
        for (TypeDeclaration Class : this.packageVisitor.getClasses()) {System.out.println("Class "+index+" name: "+Class.getName()); index++;}
        return this.packageVisitor.getClasses().size();
    }

    public int getTotalClasses(){
        return this.packageVisitor.getClasses().size();
    }

    public int getTotalLines(){
        List<TypeDeclaration> classes = new ArrayList<>();
        for (TypeDeclaration classe : packageVisitor.getClasses()) classes.add(classe);
        int total = 0;
        for (TypeDeclaration classe : classes)
            if (classe.getParent().getClass().getName().toString().equals("org.eclipse.jdt.core.dom.CompilationUnit")) {
                total += classe.getParent().toString().split("\n\r|\n|\r").length;
                System.out.println("-class : "+classe.getName().toString()+" has "+classe.getParent().toString().split("\n\r|\n|\r").length+" lines");
            }
        System.out.println("\nTotal lines = "+total);
        return total;
    }
}