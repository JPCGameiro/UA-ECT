public class LInterpreter extends SuffixCalculatorBaseListener {


   @Override public void exitProgram(SuffixCalculatorParser.ProgramContext ctx) {
   }

   @Override public void exitStat(SuffixCalculatorParser.StatContext ctx) {
      Double res;
      try{ 
         res = ctx.expr().result; 
         if(res!=null)
            System.out.println("result: "+res);
      }
      catch(NullPointerException e) { System.err.println("ERROR: usage method <number><number><op>"); }

   }

   @Override public void exitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      ctx.result = Double.parseDouble(ctx.getText());

   }

   @Override public void exitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
      double op0 = ctx.expr(0).result;
      double op1 = ctx.expr(1).result;
      String op = ctx.op.getText();
      double result=0;
      
      switch(op){
         case "+":
            result = op0+op1;
            break;
         case "-":
            result = op0-op1;
            break;
         case "/":
            result = op0/op1;
            break;
         case "*":
            result = op0*op1;
            break;
         default:
            result = 0;
            break;
      }
      ctx.result = result;
   }
}
