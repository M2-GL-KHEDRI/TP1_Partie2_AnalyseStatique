import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.Map;

//mvn clean compile assembly:single

public class Config {

    // Create AST parser :
    static CompilationUnit createOwnParse(char[] classSource, String projectSourcePath, String jrePath) {

        // handles JDK +1.6
        ASTParser parser = ASTParser.newParser(AST.JLS4);

		/*
		* Bindings : extended resolved information for several nodes of the AST.
		* various subclasses of ASTNode have binding information, retrieved by
		calling resolveBinding() on these classes.
		* These bindings draw connections between the different parts of a program,
		and generally afford a more powerful vantage point for clients who wish to
		analyze a program's structure more deeply.
		* Her we tell the AST parser to provide binding information to the created AST nodes.
		*/
        parser.setResolveBindings(true);

		/*
		* Specifies what kind of input the parser will parse :
		ASTParser.K_COMPILATION_UNIT: input = entire java source file
		(ICompilationUnit) or a char[] containing the entire source code (is a pointer to a Java file)
		* */
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        /*
         * When bindings recovery is enabled the compiler returns incomplete bindings.
         * This should be set to true only if bindings are resolved
         */
        parser.setBindingsRecovery(true);

		/*
		* JDT Core options control the behavior of core features such as the Java compiler,
		code formatter, code assist, and other core behaviors. The APIs for accessing
		the options are defined in JavaCore. Options can be accessed as a group as follows:
		JavaCore.getOptions() : return the current values of the options.
		*/
        Map options = JavaCore.getOptions();

        // Sets the compiler options to be used when parsing.
        parser.setCompilerOptions(options);

        parser.setUnitName("");

        String[] sources = { projectSourcePath };
        String[] classpath = { jrePath };

        parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
        parser.setSource(classSource);

		/*
		* Creates ASTs for a batch of compilation units, parse it and create a corresponding AST
		* CompilationUnit :
		Represents an entire Java compilation unit (source file with one of the Java-like extensions).
		*/
        return (CompilationUnit) parser.createAST(null);
    }

}
