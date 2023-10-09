import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import GUI_Package.GUIManager;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import responses.*;

public class Parser {

    static Exercice1_1And2 question1_2 = null;
    static Exercice1_3And5And7 question3_5_7 = null;
    static Exercice1_4 question4 = null;
    static Exercice1_6 question6 = new Exercice1_6();
    static Exercice1_8_To_13 question8To13;
    static Exercice2_1 question14 = new Exercice2_1();

    public static void main(String[] args) throws IOException {

        Scanner sc= new Scanner(System.in);

        System.out.println("\n Bienvenue dans l'application d'analyse statique, vous devez fournir le chemin du projet et le chemin jdk:");
        System.out.println("\n Fournissez le chemin de votre projet s'il vous plaît: ");
        String projectPath = sc.next();

        System.out.println("\n Fournissez votre chemin JDK s'il vous plaît: ");
        String jrePath = sc.next();
        String projectSourcePath = projectPath + "\\src";

        // read java files
        final File folder = new File(projectSourcePath);
        ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
        for (File fileEntry : javaFiles) {
            String content = FileUtils.readFileToString(fileEntry);
            CompilationUnit parse = Config.createOwnParse(content.toCharArray(), projectSourcePath,jrePath);
            question1_2 = new Exercice1_1And2(parse); // fill package visitor
            question3_5_7 = new Exercice1_3And5And7(parse); // fill class visitor
            question4 = new Exercice1_4(parse);
        }
        question8To13 = new Exercice1_8_To_13(question1_2.getTotalClasses(), question3_5_7.getTotalMethodsNumber());
        menu();
    }

    static void menu(){

        Scanner sc= new Scanner(System.in);
        /// Reponse de la l'exercice 1 question 2
        System.out.println("\n Exercice 1 Menu : ");
        System.out.println("1 : Nombre de classes de l’application.**");
        System.out.println("2 : Nombre de lignes de code de l’application. **");
        System.out.println("3 : Nombre total de méthodes de l’application. **");
        System.out.println("4 : Nombre total de packages de l’application. **");
        System.out.println("5 : Nombre moyen de méthodes par classe **");
        System.out.println("6 : Nombre moyen de lignes de code par méthode **");
        System.out.println("7 : Nombre moyen d’attributs par classe **");
        System.out.println("8 : Les 10% des classes qui possèdent le plus grand nombre de méthodes **");
        System.out.println("9 : Les 10% des classes qui possèdent le plus grand nombre d’attributs **");
        System.out.println("10 : Les classes qui font partie en même temps des deux catégories précédentes **");
        System.out.println("11 : Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).  **");
        System.out.println("12 : Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par \n" +
                "classe) **");
        System.out.println("13 : Le nombre maximal de paramètres par rapport à toutes les méthodes de\n" +
                "l’application **");
        System.out.println("\n Exercice 2 Menu : ");
        System.out.println("14 : Methods of call graph**");
        System.out.println("0 : Exit**");

        System.out.print("Choisissez le numéro de question (01-14) : ");
        int input = sc.nextInt();
        while(input > 14 || input < 0 ){
            System.out.print("Mauvaise saisie, choisissez à nouveau : ");
            input = sc.nextInt();
        }

        if (input==0){
            System.exit(0);
        }
        if (input==1){
            System.out.println("");
            System.out.println("\nClasses totales = " + question1_2.printClasses());
        }
        else if (input==2){
            question1_2.getTotalLines();
        }
        else if (input==3){
            System.out.println("\nMéthodes totales = " + question3_5_7.printMethods());
        }
        else if (input==4){
            System.out.println("\nTotal des packages = " + question4.getTotalPackagesNumber());
        }
        else if (input==5){
            Double MoyMethodsPerClass = (question3_5_7.getTotalMethodsNumber() * 1.00 ) / question1_2.getTotalClasses();
            System.out.println("\nNombre moyen de méthodes par classe = " + MoyMethodsPerClass);
        }
        else if (input==6){
            System.out.println("\nNombre moyen de lignes de code par méthode : " + question6.getMethodsLines());
        }
        else if (input==7){
            Double MoyAttributesPerClass = question3_5_7.getTotalVariablesNumber()* 1.00/ question1_2.getTotalClasses();
            System.out.println("\nNombre moyen d'attributs par classe = " + MoyAttributesPerClass);
        }
        else if (input==8){
            System.out.println("\nLes 10% de classes qui ont le plus de méthodes sont :");
            question8To13.get10ClassesWithMaxMethods(true);
        }
        else if (input==9){
            System.out.println("\nLes 10% de classes qui possèdent le plus d'attributs sont : ");
            question8To13.get10ClassesWithMaxAttirbutes(true);
        }
        else if (input==10){
            System.out.println("\nLes classes qui appartiennent à la fois aux deux catégories précédentes (8 et 9) sont : ");
            question8To13.get10ClassesWithMaxMethodsAttributes();
        }
        else if (input==11){
            System.out.print("Enter la value de X : ");
            input = sc.nextInt();
            question8To13.getClassesWithMoreThanXMethods(input);
        }
        else if (input==12){
            question8To13.get10MethodsWithMaxLines();
        }
        else if (input==13){
            question8To13.getMaxNumberOfParamsInMethods();
        }
        else if (input==14){
            new GUIManager().startProcess();
            System.out.println(question14.callGraph());
        }

        System.out.print("\nSouhaitez-vous effectuer un autre test? (y/n): ");
        String response = sc.next();
        if (response.equals("y")) menu();
    }

    // read all java files from specific folder
    public static ArrayList<File> listJavaFilesForFolder(final File folder) {
        ArrayList<File> javaFiles = new ArrayList<File>();
        System.out.println(folder);
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                javaFiles.addAll(listJavaFilesForFolder(fileEntry));
            } else if (fileEntry.getName().contains(".java")) {
                javaFiles.add(fileEntry);
            }
        }
        return javaFiles;
    }

}
