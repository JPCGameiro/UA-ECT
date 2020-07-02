public class IntegerValue extends Value
{
   public IntegerValue(int val)
   {
      setIntValue(val);
   }

   @Override
   public Type type()
   {
      return type;
   }

   @Override
   public void setIntValue(int val)
   {
      this.val = val;
   }

   @Override
   public int intValue()
   {
      return val;
   }

   @Override
   public String toString()
   {
      return ""+val;
   }

   private int val;

   private static IntegerType type = new IntegerType();
}

