public abstract class Symbol
{
   public Symbol(String name, Type type) {
      assert name != null;
      assert type != null;

      this.name = name;
      this.type = type;
   }

   public void setValue(Value value) {
      assert value != null;

      this.value = value;
   }

   public String name(){
      return name;
   }

   public void setVarName(String varName) {
      assert varName != null;

      this.varName = varName;
   }

   public String varName(){
      return varName;
   }

   public Type type(){
      return type;
   }

   public void setValueDefined(){
      valueDefined = true;
   }

   public boolean valueDefined(){
      return valueDefined;
   }

   public Value value(){
      assert valueDefined();

      return value;
   }

   protected final String name;
   protected final Type type;
   protected Value value;
   protected boolean valueDefined;
   protected String varName;
}

