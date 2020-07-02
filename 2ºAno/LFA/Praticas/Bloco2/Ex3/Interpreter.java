public class Interpreter extends CalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Double visitStat(CalculatorParser.StatContext ctx) {
      try { System.out.println("result: "+visit(ctx.expr())); }
      catch(NullPointerException e) { System.err.println("ERROR: insert an operation"); }
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
      try{signal = ctx.signal.getText();}
      catch(NullPointerException e) { signal = "+"; }
      double result = visit(ctx.expr());
      if(signal.equals("-"))
         return result * -1;
      else  
         return result;
   }

   @Override public Double visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      String signal;
      try{signal = ctx.signal.getText();}
      catch(NullPointerException e) { signal = "+"; }
      int aux = Integer.parseInt(ctx.getText());
      double result = aux;
      return result;
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

