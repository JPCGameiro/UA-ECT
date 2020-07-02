package types;

public class IntegerType extends Type {
   public IntegerType() {
      super("int");
   }

   public boolean isNumeric() {
      return true;
   }
}

