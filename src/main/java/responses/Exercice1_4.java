package responses;

import visitors.PackageVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.HashSet;
import java.util.Set;

public class Exercice1_4 {

    CompilationUnit parse;
    public PackageVisitor packageVisitor = new PackageVisitor();
    public static Set<String> packages = new HashSet<>();

    public Exercice1_4(CompilationUnit parse){
        this.parse = parse;
        parse.accept(packageVisitor);
        packages.add(parse.getPackage().getName().getFullyQualifiedName());
    }

    public int getTotalPackagesNumber(){
        int index = 1;
        System.out.println("");
        for (String pkg : packages) {System.out.println("Package "+index+" : "+pkg); index++;}
        return packages.size();
    }
}
