import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class QuestionsReaderMain {
   public static void main(String[] args) throws Exception {
      // create a CharStream that reads from standard input:
      CharStream input = CharStreams.fromStream(System.in);
      // create a lexer that feeds off of input CharStream:
      QuestionsReaderLexer lexer = new QuestionsReaderLexer(input);
      // create a buffer of tokens pulled from the lexer:
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      // create a parser that feeds off the tokens buffer:
      QuestionsReaderParser parser = new QuestionsReaderParser(tokens);
      // replace error listener:
      // parser.removeErrorListeners(); // remove ConsoleErrorListener
      // parser.addErrorListener(new ErrorHandlingListener());
      // begin parsing at program rule:
      ParseTree tree = parser.program();
      if (parser.getNumberOfSyntaxErrors() == 0) {
         // print LISP-style tree:
         // System.out.println(tree.toStringTree(parser));
         // SemanticCheck for .q files
         SemanticCheck semanticCheck = new SemanticCheck();
         semanticCheck.visit(tree);
         Interpreter visitor0 = new Interpreter();
         visitor0.visit(tree);
      }
   }

   public HashMap<String, ArrayList<Question>> getKuestsFromDirectory() throws IOException {
      return getKuestsFromDirectory(".");
   }

   // walk through a directory, and sub-directorys and apply Interpreter for each file .kuest
   // return HashMap<theme, Question[]>
   public HashMap<String, ArrayList<Question>> getKuestsFromDirectory(String sourceDir) {
      Path dir = Paths.get(sourceDir);
      MyFileVisitor mfv = new MyFileVisitor(".kuest");
      try {
         Files.walkFileTree(dir, mfv);
      } catch (IOException e) {
         System.out.printf("ERROR: %s, is not a valid Directory\n", sourceDir);
         e.printStackTrace();
         System.exit(0);
      }
      return mfv.allKuests;
   }
   // usefull to parse a Kuest during programming
   // return HashMap<theme, Question[]>
   public HashMap<String, ArrayList<Question>> parseKuest(String k){
      // create a CharStream that reads from standard input:
      CharStream input = CharStreams.fromString(k);
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
      if (parser.getNumberOfSyntaxErrors() == 0) {
         // print LISP-style tree:
         // System.out.println(tree.toStringTree(parser));
         // SemanticCheck for .q files
         SemanticCheck semanticCheck = new SemanticCheck();
         semanticCheck.visit(tree);
         Interpreter visitor0 = new Interpreter();
         visitor0.visit(tree);
         if(visitor0.quest != null){
            return visitor0.quest;
         }
      }
      return null;
   }

   // inner class to iterate over an directory
   private class MyFileVisitor extends SimpleFileVisitor<Path>{
      private String search;
      public HashMap<String, ArrayList<Question>> allKuests;
      public MyFileVisitor(String s){
         search = s;
         allKuests = new HashMap<>();
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException{
         if(file.toString().contains(search)){
            // create a CharStream that reads from standard input:
            CharStream input = CharStreams.fromStream(new FileInputStream(file.toString()));
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
            if (parser.getNumberOfSyntaxErrors() == 0) {
               // print LISP-style tree:
               // System.out.println(tree.toStringTree(parser));
               // SemanticCheck for .q files
               SemanticCheck semanticCheck = new SemanticCheck();
               semanticCheck.visit(tree);
               Interpreter visitor0 = new Interpreter();
               visitor0.visit(tree);
               allKuests = QuestionsReaderMain.mergeHashMaps(allKuests, visitor0.quest);
               System.out.printf("File %s is OK!\n", file.getFileName().toString());
            }
         }
         return FileVisitResult.CONTINUE;
      }
   }

   // merge 2 hashmaps
   private static HashMap<String, ArrayList<Question>> mergeHashMaps(HashMap<String, ArrayList<Question>> primary, HashMap<String, ArrayList<Question>> to_join){
      Set<String> keys = to_join.keySet();
      for(String key : keys){
         if(primary.containsKey(key)){
            ArrayList<Question> primary_kuest = primary.get(key);
            for(Question q : to_join.get(key)){
               primary_kuest.add(q);
            }
         }else{
            primary.put(key, to_join.get(key));
         }
      }
      return primary;
   }
}
