public class LInterpreter extends CalculatorBaseListener {

   @Override public void exitProgram(CalculatorParser.ProgramContext ctx) {
   }

   @Override public void exitStat(CalculatorParser.StatContext ctx) {
      try { System.out.println("result: "+ctx.expr().res); }
      catch(NullPointerException e) { System.err.println("ERROR: insert an operation"); }
   }

   @Override public void exitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      double op0 = ctx.expr(0).res;
      double op1 = ctx.expr(1).res;
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
      ctx.res = result;
   }

   @Override public void exitExprParent(CalculatorParser.ExprParentContext ctx) {
      String signal;
      try{signal = ctx.signal.getText();}
      catch(NullPointerException e) { signal = "+"; }
      double result = ctx.expr().res;
      if(signal.equals("-"))
         ctx.res = result * -1;
      else  
         ctx.res = result;
   }

   @Override public void exitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      String signal;
      try{signal = ctx.signal.getText();}
      catch(NullPointerException e) { signal = "+"; }
      int aux = Integer.parseInt(ctx.getText());
      double result = aux;
      ctx.res = result;
   }

   @Override public void exitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      double op0 = ctx.expr(0).res;
      double op1 = ctx.expr(1).res;
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
      ctx.res = result;
   }

}
