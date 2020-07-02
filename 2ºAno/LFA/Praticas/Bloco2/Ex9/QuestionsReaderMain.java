import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.Scanner;

public class QuestionsReaderMain {
   public static void main(String[] args) throws Exception {

   
      File initialFile = new File(args[0]);
      InputStream targetStream = null;
      try{  targetStream = new FileInputStream(initialFile); }
      catch(FileNotFoundException e) { System.err.println("ERROR: file does not exist"); System.exit(0);}

      // create a CharStream that reads from standard input:
      CharStream input = CharStreams.fromStream(targetStream);
      // create a lexer that feeds off of input CharStream:
      QuestionsReaderLexer lexer = new QuestionsReaderLexer(input);
      // create a buffer of tokens pulled from the lexer:
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      // create a parser that feeds off the tokens buffer:
      QuestionsReaderParser parser = new QuestionsReaderParser(tokens);
      // replace error listener:
      //parser.removeErrorListeners(); // remove ConsoleErrorListener
      //parser.addErrorListener(new ErrorHandlingListener());
      // begin parsing at program rule:
      ParseTree tree = parser.program();
      Interpreter visitor0 = null;
      if (parser.getNumberOfSyntaxErrors() == 0) {
         // print LISP-style tree:
         // System.out.println(tree.toStringTree(parser));
         visitor0 = new Interpreter();
         visitor0.visit(tree);
      }


      int n = Integer.parseInt(args[2]);
      String subject = args[1];
      String result = "";
      try{  result = visitor0.map.get(subject).printNAnswers(n); }
      catch(NullPointerException e) { System.out.println("ERROR: invalid subject"); System.exit(0);}
      result = result.replace("\"", "");
      System.out.println("Subject:"+subject);
      System.out.println(result);

   }
}
