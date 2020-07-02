import java.util.HashMap;

public class Interpreter extends QuestionsReaderBaseVisitor<Question> {

      HashMap<String, Question> map = new HashMap<String, Question>();

   @Override public Question visitProgram(QuestionsReaderParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Question visitStat(QuestionsReaderParser.StatContext ctx) {
      int i=0;
      try{
         while(true){
            System.out.println("Subject: "+visit(ctx.question(i)));
            i++;
         }
      }
      catch(NullPointerException e) {}
      return null;
   }

   @Override public Question visitQuestion(QuestionsReaderParser.QuestionContext ctx) {
      String subject = ctx.IDENTIFIER(0).getText();
      String questionId = ctx.IDENTIFIER(1).getText();
      String question = ctx.STRING(0).getText();
      map.put(subject, new Question(questionId, question));

      int i=1, j=0;
      try{
         while(true){
            map.get(subject).addAnswer(ctx.STRING(i).getText(), Integer.parseInt(ctx.INT(j).getText()));
            i++;
            j++;
         }
      }
      catch(NullPointerException e) {}
      return map.get(subject);
   }
}
