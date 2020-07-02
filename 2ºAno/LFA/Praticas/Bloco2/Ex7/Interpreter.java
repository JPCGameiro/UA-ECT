public class Interpreter extends CalculatorBaseVisitor<String> {

   @Override public String visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitStat(CalculatorParser.StatContext ctx) {
      try { System.out.println("result -> "+visit(ctx.expr())); }
      catch(NullPointerException e) { 
         try{ visit(ctx.assignment()); }
         catch(NullPointerException e1) { System.err.println("ERROR: insert an expression"); }
      }
      return null;
   }

   @Override public String visitAssignment(CalculatorParser.AssignmentContext ctx) {
      System.out.println("result -> "+ctx.ID()+" = "+visit(ctx.expr()));
      return null;
   }

   @Override public String visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      return visit(ctx.expr(0))+" "+visit(ctx.expr(1))+" "+ctx.op.getText();
   }

   @Override public String visitExprParent(CalculatorParser.ExprParentContext ctx) {
      String signal;
      try{ signal = "!"+ctx.signal.getText(); }
      catch(NullPointerException e) { signal=""; }
      return visit(ctx.expr())+" "+signal;
   }

   @Override public String visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      String signal;
      try{ signal = "!"+ctx.signal.getText(); }
      catch(NullPointerException e) {signal = ""; }
      return ctx.Integer().getText()+" "+signal;
   }

   @Override public String visitExprId(CalculatorParser.ExprIdContext ctx) {
      return ctx.getText();
   }

   @Override public String visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      return visit(ctx.expr(0))+" "+visit(ctx.expr(1))+" "+ctx.op.getText();
   }
}
