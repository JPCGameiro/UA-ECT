import java.util.HashMap;

public class LInterpreter extends NumberTranslatorBaseListener {

   HashMap<Integer, String> map = new HashMap<Integer, String>();

   @Override public void exitProgram(NumberTranslatorParser.ProgramContext ctx) {
   }

   @Override public void exitAssignment(NumberTranslatorParser.AssignmentContext ctx) {
      int number = Integer.parseInt(ctx.INT().getText());
      String value = ctx.ID().getText();
      map.put(number, value);
      System.out.println("["+number+"]-["+map.get(number)+"]");
      ctx.result = "";
   }
}
