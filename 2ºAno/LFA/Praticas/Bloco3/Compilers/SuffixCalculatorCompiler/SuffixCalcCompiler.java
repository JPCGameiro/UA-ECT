import org.stringtemplate.v4.*;
import java.util.Iterator;

public class SuffixCalcCompiler extends SuffixCalculatorBaseVisitor<ST> {

   private STGroup padrons = new STGroupFile("java.stg");
   private int numVars = 0;
   private String newVar() { numVars++; return "v"+numVars; }

   @Override public ST visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      ST result = padrons.getInstanceOf("module");
      Iterator<SuffixCalculatorParser.StatContext> it = ctx.stat().iterator();
      while(it.hasNext()){
         result.add("stat", visit(it.next()).render());
      }
      return result;
   }

   @Override public ST visitStat(SuffixCalculatorParser.StatContext ctx) {
      ST result = padrons.getInstanceOf("print");
      result.add("stat", visit(ctx.expr()).render());
      result.add("value", ctx.expr().res);
      return result;
   }

   @Override public ST visitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      ST result = padrons.getInstanceOf("declaration");
      ctx.res = newVar();
      result.add("type", "double");
      result.add("var", ctx.res);
      result.add("value", ctx.Number().getText());
      return result;
   }

   @Override public ST visitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
      ST result = padrons.getInstanceOf("operation");
      ctx.res = newVar();
      result.add("stat", visit(ctx.e1).render());
      result.add("stat", visit(ctx.e2).render());
      result.add("type", "double");
      result.add("var", ctx.res);
      result.add("e1", ctx.e1.res);
      result.add("op", ctx.op.getText());
      result.add("e2", ctx.e2.res);
      return result;
   }
}
