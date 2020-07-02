public class Execute extends HelloBaseVisitor<String> {

   @Override public String visitTop(HelloParser.TopContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitGreetings(HelloParser.GreetingsContext ctx) {
      System.out.println("Olá "+ctx.ID().getText());
      return visitChildren(ctx);
   }

   @Override public String visitBye(HelloParser.ByeContext ctx) {
      System.out.println("Adeus "+ctx.ID().getText());
      return visitChildren(ctx);
   }
}
