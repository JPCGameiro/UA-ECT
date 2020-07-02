import static java.lang.System.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;

public class CalcMain
{

   public static void main(String[] args)
   {
      boolean compile = true;
      int i;
      for(i = 0; i < args.length && args[i].startsWith("-"); i++)
      {
         switch(args[i].toLowerCase())
         {
            case "-interpreter":
               compile = false;
               break;
            case "-compiler":
               compile = true;
               break;
            default:
               err.println("ERROR: invalid program argument \""+args[i]+"\"");
               exit(1);
               break;
         }
      }

      if (i+1 != args.length)
      {
         out.println("Usage: antlr4-java -ea CalcMain [-compiler | -interpreter] <source-file>");
         exit(0);
      }
      if (compile)
         runCompiler(args[i]);
      else
         runInterpreter(args[i]);
   }

   public static void runCompiler(String sourceFile)
   {
      assert sourceFile != null && !sourceFile.isEmpty();

      try
      {
         // create a CharStream that reads from standard input:
         CharStream input = CharStreams.fromStream(new FileInputStream(sourceFile));
         // create a lexer that feeds off of input CharStream:
         CalcLexer lexer = new CalcLexer(input);
         // create a buffer of tokens pulled from the lexer:
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         // create a parser that feeds off the tokens buffer:
         CalcParser parser = new CalcParser(tokens);
         // replace error listener:
         //parser.removeErrorListeners(); // remove ConsoleErrorListener
         //parser.addErrorListener(new ErrorHandlingListener());
         // begin parsing at main rule:
         ParseTree tree = parser.main();
         if (parser.getNumberOfSyntaxErrors() == 0) {
            // print LISP-style tree:
            // System.out.println(tree.toStringTree(parser));
            CalcSemanticCheck semanticCheck = new CalcSemanticCheck();
            CalcCompiler compiler = new CalcCompiler();
            semanticCheck.visit(tree);
            if (!ErrorHandling.error())
            {
               String[] targets = {"java","c"};
               for(String target: targets)
               {
                  if (!compiler.validTarget(target))
                  {
                     err.println("ERROR: template group file for target "+target+" not found!");
                     exit(2);
                  }
                  compiler.setTarget(target);
                  ST code = compiler.visit(tree);
                  String filename = "Output." + target;
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
            }
         }
      }
      catch(IOException e)
      {
         err.println("ERROR: unable to read from file "+sourceFile);
         exit(4);
      }
   }

   public static void runInterpreter(String sourceFile)
   {
      assert sourceFile != null && !sourceFile.isEmpty();

      try
      {
         Scanner sc = new Scanner(new FileInputStream(sourceFile));
         String lineText = null;
         int lineNum = 1;
         if (sc.hasNextLine())
            lineText = sc.nextLine();
         CalcParser parser = new CalcParser(null);
         // replace error listener:
         //parser.removeErrorListeners(); // remove ConsoleErrorListener
         //parser.addErrorListener(new ErrorHandlingListener());
         CalcSemanticCheck semanticCheck = new CalcSemanticCheck();
         CalcInterpreter interpreter = new CalcInterpreter();
         while(lineText != null) {
            // create a CharStream that reads from standard input:
            CharStream input = CharStreams.fromString(lineText + "\n");
            // create a lexer that feeds off of input CharStream:
            CalcLexer lexer = new CalcLexer(input);
            lexer.setLine(lineNum);
            lexer.setCharPositionInLine(0);
            // create a buffer of tokens pulled from the lexer:
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer:
            parser.setInputStream(tokens);
            // begin parsing at main rule:
            ParseTree tree = parser.main();
            if (parser.getNumberOfSyntaxErrors() == 0) {
               // print LISP-style tree:
               // System.out.println(tree.toStringTree(parser));
               semanticCheck.visit(tree);
               if (!ErrorHandling.error())
                  interpreter.visit(tree);
            }
            if (sc.hasNextLine())
               lineText = sc.nextLine();
            else
               lineText = null;
            lineNum++;
         }
      }
      catch(IOException e)
      {
         err.println("ERROR: unable to read from file "+sourceFile);
         exit(4);
      }
   }
}

