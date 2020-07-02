import java.util.HashMap;
public class Interpreter extends NumberTranslatorBaseVisitor<String> {

   HashMap<Integer, String> map = new HashMap<Integer, String>();

   @Override public String visitProgram(NumberTranslatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitAssignment(NumberTranslatorParser.AssignmentContext ctx) {
      int number = Integer.parseInt(ctx.INT().getText());
      String value = ctx.ID().getText();
      map.put(number, value);
      System.out.println("["+number+"]-["+map.get(number)+"]");
      return "";
   }
}
