import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import types.*;

public class Interpreter extends QuestionsReaderBaseVisitor<Question> {

   public HashMap<String, ArrayList<Question>> quest = new HashMap<>();

   @Override public Question visitProgram(QuestionsReaderParser.ProgramContext ctx) {
      visitChildren(ctx);

      // print memory!
      //Util.printMemory(quest);
      return null;
   }

   @Override public Question visitStat(QuestionsReaderParser.StatContext ctx) {
      for(QuestionsReaderParser.QuestionContext qc : ctx.question()){
         visit(qc);
      }

      return null;
   }

   @Override public Question visitQuestion(QuestionsReaderParser.QuestionContext ctx) {
      String tema = ctx.IDENTIFIER(0).getText();
      String id = ctx.IDENTIFIER(1).getText();

      int dificuldade = -1;
      try{
         dificuldade = Integer.parseInt(ctx.INT().getText());

         if(dificuldade < 0){
            System.out.println("Sintax ERROR: dificuldade cannot be negative value!");
            System.exit(1);
         }

      }catch(NullPointerException e){
         System.out.println("Cannot convert dificuldade to integer!");
         System.exit(1);
      }
      String pergunta = ctx.STRING().getText();

      HashMap<String,Integer> respostas = new HashMap<>();

      for(QuestionsReaderParser.AnswerContext ans : ctx.answer()){
         String resp = ans.STRING().getText();
         int cotation = Integer.parseInt(ans.INT().getText());

         if(!respostas.containsKey(resp)){
            respostas.put(resp, cotation);
         }
      }

      Question q = new Question(tema, id, dificuldade, respostas, pergunta);

      ArrayList<Question> questTheme = new ArrayList<>();

      if(!quest.containsKey(tema)){

         questTheme.add(q);

         quest.put(tema, questTheme);

      }else{
         questTheme = quest.get(tema);

         questTheme.add(q);
      }

      return null;
   }

   @Override public Question visitAnswer(QuestionsReaderParser.AnswerContext ctx) {
      return visitChildren(ctx);
   }
}
