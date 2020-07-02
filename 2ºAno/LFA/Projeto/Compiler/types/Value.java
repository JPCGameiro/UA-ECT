package types;

abstract public class Value
{
   public Type type()
   {
      assert false;
      return null;
   }

   //SET
   public void setIntValue(int val)
   {
      assert false;
   }
   public void setBoolValue(boolean val)
   {
      assert false;
   }
   public void setStringValue(String val)
   {
      assert false;
   }
   public void setQuestionValue(Question val)
   {
      assert false;
   }

   //Values
   public int intValue()
   {
      assert false;
      return 0;
   }
   public boolean boolValue()
   {
      assert false;
      return false;
   }
   public String strValue()
   {
      assert false;
      return "";
   }
   public Question questValue()
   {
      assert false;
      return null;
   }

}

