import java.util.HashMap;
import java.util.ArrayList;
import types.*;

public class SemanticCheck extends QuestionsReaderBaseVisitor<Boolean> {
   
   // garante id's diferentes dentro do mesmo tema
   // garante perguntas diferentes dentro do mesmo tema
   // garante respostas diferentes dentro da mesma pergunta
   // garante que o parametro dificuldade é um valor entre 1 e 5.

   private HashMap<String, HashMap<String, String>> quest = new HashMap<>();
   private HashMap<String, String> idperguntas = new HashMap<>();

   @Override public Boolean visitStat(QuestionsReaderParser.StatContext ctx) {
      for(QuestionsReaderParser.QuestionContext qc : ctx.question()){
         visit(qc);
      }

      return null;
   }

   @Override public Boolean visitQuestion(QuestionsReaderParser.QuestionContext ctx) {
      String tema = ctx.IDENTIFIER(0).getText();
      String id = ctx.IDENTIFIER(1).getText();
      String pergunta = ctx.STRING().getText();
      
      if(quest.containsKey(tema)){
         idperguntas = quest.get(tema);
         if(idperguntas.containsKey(id)){
            ErrorHandling.printError(ctx, "ERROR: Equal id in theme " + tema + "!");
         }
         ArrayList<String> perguntas = new ArrayList<>();
         for(String i : idperguntas.values()){
            perguntas.add(i);
         }
         for(int i = 0; i < perguntas.size(); i++){
            if(perguntas.get(i).equalsIgnoreCase(pergunta)){
               ErrorHandling.printError(ctx, "ERROR: Equal questions in theme " + tema + "!");
            }
         }
         idperguntas.put(id, pergunta);
      }else{
         idperguntas.put(id, pergunta);
         quest.put(tema, idperguntas);
      }

      int dificuldade = -1;
      try{
         dificuldade = Integer.parseInt(ctx.INT().getText());

         if(dificuldade < 1 || dificuldade > 5){
            ErrorHandling.printError(ctx, "ERROR: in question " + pergunta + ", dificuldade must be 1-2-3-4-5!\n");
         }

      }catch(NullPointerException e){
         ErrorHandling.printError(ctx, "Cannot convert dificuldade to integer!");
      }

      ArrayList<String> respostas = new ArrayList<>();

      for(QuestionsReaderParser.AnswerContext ans : ctx.answer()){
         String resp = ans.STRING().getText();

         if(respostas.contains(resp)){
            // verify if a question as 2 equal answers
            ErrorHandling.printError(ctx, "ERROR: Equal answers in question " + pergunta + "!");
         }else{
            respostas.add(resp);
         }
      }

      return null;
   }
}
