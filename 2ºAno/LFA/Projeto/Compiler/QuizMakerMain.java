import static java.lang.System.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;
import types.*;

public class QuizMakerMain {
   public static void main(String[] args) throws Exception {
      if(args.length != 1){
         System.out.println("Usage: java QuizMakerMain <path2file>");
         System.exit(1);
      }
      if(!args[0].contains(".kuest")){
         System.out.println("Usage: java QuizMakerMain <path2file> (.kuest file)");
         System.exit(0);
      }
      try {
         File f = new File(args[0]);
         if(f.exists() && f.canRead()){
            // create a CharStream that reads from standard input:
            CharStream input = CharStreams.fromStream(new FileInputStream(args[0]));
            // create a lexer that feeds off of input CharStream:
            QuizMakerLexer lexer = new QuizMakerLexer(input);
            // create a buffer of tokens pulled from the lexer:
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer:
            QuizMakerParser parser = new QuizMakerParser(tokens);
            // replace error listener:
            //parser.removeErrorListeners(); // remove ConsoleErrorListener
            //parser.addErrorListener(new ErrorHandlingListener());
            // begin parsing at main rule:
            ParseTree tree = parser.main();
            if (parser.getNumberOfSyntaxErrors() == 0) {
               // print LISP-style tree:
               // System.out.println(tree.toStringTree(parser));
               SemCheck sc = new SemCheck();
               sc.visit(tree);
               if(ErrorHandling.error()) System.exit(0);
               QuizMakerParser.symbolTable.reverse();
               Compiler visitor0 = new Compiler();
               ST code = visitor0.visit(tree);

               String filename = "Output.java";
               try
               {
                  code.add("name", "Output");
                  PrintWriter pw = new PrintWriter(new File(filename));
                  pw.print(code.render());
                  pw.close();
               }
               catch(IOException e)
               {
                  err.println("ERROR: unable to write in file "+filename);
                  exit(3);
               }
            }
         }else{
            System.out.println("File does not exist or can't be read!");
            System.exit(1);
         }
      } catch (IOException e) {
         System.out.println("ERROR: in parsing file!");
      } 
   }
}
