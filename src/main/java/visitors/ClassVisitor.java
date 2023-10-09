package visitors;

import org.eclipse.jdt.core.dom.*;

import java.util.*;

/** TODO :
 * Visiting Classes
 * Visiting Attributes
 * Make next relations : Class-Attributes and Class-Methods
 * In this visitor i made 3 solutions to get attributes and make the relation Class-Attributes
 and every solution has its own pros and cons.
 */

public class ClassVisitor extends ASTVisitor{

    /**  TODO : SOL-Variables-1
     public static Map<String, FieldDeclaration[]> variables = new HashMap<String, FieldDeclaration[]>();
     */

    public static Map<String, MethodDeclaration[]> methods = new HashMap<>();
    public  Map<String, MethodDeclaration[]> getMethods() {return methods;}


    /** methodsV2 is for Question14 */
    public static List<MethodDeclaration> methodsV2 = new ArrayList<>() ;
    public List<MethodDeclaration> getMethodsvV2() {
        return methodsV2;
    }

    public boolean visit(TypeDeclaration node) {
        if (!node.isInterface()) {
            methods.put(node.getName().toString(), node.getMethods());

            for (MethodDeclaration m : node.getMethods()) methodsV2.add(m);

            /** TODO : SOL-Variables-1
             * The problem with [node.getFields] that there is a such cases like [public String firstName, lastName, city]
             it will consider it as one variable, so we need to make string treatment plus eliminating java reserved work
             like [static final int string ... etc].

             * The good thing here is that we can always make a relation between the variable and his class parent 'directly'.

             variables.put(node.getName().toString(), node.getFields());
             */
        }
        return super.visit(node);
    }

    /** TODO : SOL-Variables-1
     public Map<String, FieldDeclaration[]> getVariables() {
     return variables;
     }
     */



    /** TODO : SOL-Variables-2
     * Not good to work with [VariableDeclaration] cuz it's need to be casted to VariableDeclarationFragment or SingleVariableDeclaration

     public static Map<String, VariableDeclaration> variables = new HashMap<String, VariableDeclaration>();

     public boolean visit (VariableDeclarationFragment node) {
     variables.put(node.getParent().toString(),node);
     return super.visit((VariableDeclarationFragment) node);
     }
     public Map<String, VariableDeclaration> getVariables() {
     return variables;
     }
     */


    /** TODO : SOL-Variables-3
     * The good thing here is that we get all variables and separated, but to make relation between the variable
     and his parent we should do a little extra work :

     > adding this line in visit method : parents.add( ( (TypeDeclaration) node.getParent().getParent() ).getName().toString() );
     +
     > function getParentVariables()
     */


    public static List<String> variables = new ArrayList<String>();
    public static List<String> parents = new ArrayList<String>();
    public Map<String, List<String>> parentVariables = new HashMap<>();

    public boolean visit(VariableDeclarationFragment node) {
        if (node.getParent().getParent() instanceof TypeDeclaration) {
            variables.add(node.getName().toString());
            parents.add( ( (TypeDeclaration) node.getParent().getParent() ).getName().toString() );
        }
        return super.visit(node);
    }

    public List<String> getVariables() {
        return variables;
    }
    public List<String> getParent() {return parents;}

    public Map<String, List<String>> getParentVariables(boolean show) {
        pushParentVariables(show);
        return parentVariables;
    }

    public void pushParentVariables (boolean show) {
        List<List<String>> vars = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < variables.size(); i++) {
            temp.add(variables.get(i));
            if (i+1<variables.size())
                if (!parents.get(i).equals(parents.get(i+1))){
                    vars.add(temp);
                    temp = new ArrayList<>();
                }
        }
        vars.add(temp);
        List<String> clearParents = new ArrayList<>(new LinkedList<String>(parents));
        for (int k = 0; k < vars.size(); k++)
            parentVariables.put(clearParents.get(k), vars.get(k));

        if (show) {
            for (var entry : parentVariables.entrySet())
                System.out.println("class : "+entry.getKey()+" --- attributes : "+ entry.getValue()+"\n");
        }

    }
}