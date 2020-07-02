import java.util.HashMap;

public class Interpreter extends CalculatorBaseListener {

   HashMap<String, Double> map = new HashMap<String, Double>();

   @Override public void exitProgram(CalculatorParser.ProgramContext ctx) {
   }

   @Override public void exitStat(CalculatorParser.StatContext ctx) {
      try{ System.out.println("result: "+ctx.expr().result); }
      catch(NullPointerException e) { System.out.println("result: "+ctx.assignment().result);  }
   }

   @Override public void exitAssignment(CalculatorParser.AssignmentContext ctx) {
      String id = ctx.ID().getText();
      double value = 0;
      try{ value = ctx.expr().result; }
      catch(NullPointerException e) { ctx.result = null; }
      map.put(id, value);
      System.out.println(id+": "+map.get(id));
   }

   @Override public void exitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      double op0 = ctx.expr(0).result;
      double op1 = ctx.expr(1).result;
      String op = ctx.op.getText();
      double result = 0;
      switch(op){
         case "+":
            result = op0+op1;
            break;
         case "-":
            result = op0-op1;
            break;
      }
      ctx.result = result;
   }

   @Override public void exitExprParent(CalculatorParser.ExprParentContext ctx) {
      String signal;
      try { signal = ctx.signal.getText(); }
      catch(NullPointerException e) {signal = "+"; }
      double result = ctx.expr().result;
      if(signal.equals("-"))
         ctx.result = result*-1;
      else
         ctx.result = result;
   }

   @Override public void exitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      int aux = Integer.parseInt(ctx.getText());
      double result = aux;
      ctx.result = result;
   }

   @Override public void exitExprId(CalculatorParser.ExprIdContext ctx) {
      String id = ctx.ID().getText();
      if(map.containsKey(id))
         ctx.result = map.get(id);
      else
         System.err.println("ERROR: variable "+id+" was not intialized");
      ctx.result = null;
   }

   @Override public void exitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      double op0 = ctx.expr(0).result;
      double op1 = ctx.expr(1).result;
      String op = ctx.op.getText();
      double result = 0;
      switch(op){
         case "*":
            result = op0*op1;
            break;
         case "/":
            result = op0/op1;
            break;
         case "%":
            result = op0%op1;
            break;
      }
      ctx.result = result;
   }
}
