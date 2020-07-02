import java.util.HashMap;

public class Interpreter extends CalculatorBaseVisitor<Double> {

   HashMap<String, Double> map = new HashMap<String, Double>();

   @Override public Double visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Double visitStat(CalculatorParser.StatContext ctx) {
      try{ System.out.println("result: "+visit(ctx.expr())+"\n"); }
      catch(NullPointerException e) { 
         try{ visit(ctx.assignment());}
         catch(NullPointerException e1){ System.err.println("ERROR: insert an expression\n"); } 
      }
      return null;
   }

   @Override public Double visitAssignment(CalculatorParser.AssignmentContext ctx) {
      String id = ctx.ID().getText();
      double value;
      try{ value = visit(ctx.expr()); }
      catch(NullPointerException e) { 
         return null;
      }
      map.put(id, value);
      System.out.println(id+": "+map.get(id)+"\n");
      return null;
   }

   @Override public Double visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      double op0 = visit(ctx.expr(0));
      double op1 = visit(ctx.expr(1));
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
      return result;
   }

   @Override public Double visitExprParent(CalculatorParser.ExprParentContext ctx) {
      String signal;
      try { signal = ctx.signal.getText(); }
      catch(NullPointerException e) {signal = "+"; }
      double result = visit(ctx.expr());
      if(signal.equals("-"))
         return result*-1;
      else
         return result;
   }

   @Override public Double visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      int aux = Integer.parseInt(ctx.getText());
      double result = aux;
      return result;
   }

   @Override public Double visitExprId(CalculatorParser.ExprIdContext ctx) {
      String id = ctx.ID().getText();
      if(map.containsKey(id))
         return map.get(id);
      else
         System.err.println("ERROR: variable "+id+" was not intialized");
      return null;
   }

   @Override public Double visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      double op0 = visit(ctx.expr(0));
      double op1 = visit(ctx.expr(1));
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
      return result;
   }
}
