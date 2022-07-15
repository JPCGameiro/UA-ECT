package environment;

import genclass.GenericIO;
import java.util.Properties;
import java.util.Set;
import java.util.Map;
import java.util.Objects;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 *   Basic features of the running environment.
 */

public class CollectEnvironmentData
{
  /**
   *    Main program, implemented by the <code>main</code> method of the data type.
   *
   *      @param args runtime parameter list
   */

    public static void main (String [] args)
    {
       Runtime run = Runtime.getRuntime ();                          // execution environment
       Properties sysProp = System.getProperties ();                 // execution environment properties
       Set<String> setProp = sysProp.stringPropertyNames ();         // set of the names of the properties of the
                                                                     //   execution environment
       Map<String,String> mapEnv = System.getenv();                  // mapping of the pairs name/value of the variables
                                                                     //   of the execution environment
       Set<Map.Entry<String,String>> setEnvPair = mapEnv.entrySet(); // set of the pairs name/value of the variables
                                                                     //   of the execution environment


       GenericIO.writelnString ();
       GenericIO.writelnString ("Characterization of Java Virtual Machine (JVM)");
       GenericIO.writelnString ();
       GenericIO.writelnString ("N. of available processors = " + run.availableProcessors());
       GenericIO.writelnString ("Size of dynamic memory presently free (in bytes) = " + run.freeMemory ());
       GenericIO.writelnString ("Size of total dynamic memory (in bytes) = " + run.totalMemory ());
       GenericIO.writelnString ("Maximum size of available main memory of the hardware platform where " +
                                "Java virtual machine is installed (in bytes) = " + run.maxMemory ());

       GenericIO.writelnString ();
       GenericIO.writelnString ("Properties of the execution environment");
       GenericIO.writelnString ();
       for (String prop : setProp)
       { if (Objects.equals (prop, "line.separator") || Objects.equals (prop, "sun.cpu.isalist") ||
             Objects.equals (prop, "user.timezone"))
            { GenericIO.writeString (prop);
              GenericIO.writeString (" (" + prop.length () + " - ");
              try
              { ByteArrayOutputStream bOut = new ByteArrayOutputStream (4096);      // byte array based representation
                ObjectOutputStream oOut = new ObjectOutputStream (bOut);            // object based representation
                oOut.writeObject (System.getProperty (prop));                       // convert property value
                oOut.flush ();                                                      // send it to lower level
                byte [] bProp = bOut.toByteArray ();                                // property value in bytes
                String value = "";                                                  // string representation of
                                                                                    //   property value
                GenericIO.writeString (bProp.length + ") = ");
                for (int i = 0; i < bProp.length; i++)
                  value += convByteToHexa (bProp[i]);
                GenericIO.writelnString (value);
              }
              catch (IOException e)
              { GenericIO.writelnString (e.toString());
                e.printStackTrace ();
                System.exit (1);
              }
            }
            else GenericIO.writelnString (prop + " = " + System.getProperty (prop));
       }

       GenericIO.writelnString ();
       GenericIO.writelnString ("Variables of the execution environment");
       GenericIO.writelnString ();
       for (Map.Entry<String,String> pair : setEnvPair)
       { GenericIO.writelnString (pair.getKey () + " = " + pair.getValue ());
       }
    }


  /**
   *    Convert byte value in binary to a hexadecimal representation.
   *
   *      @param b byte to be converted
   *
   *      @return hexadecimal representation
   */

    private static String convByteToHexa (byte b)
    {
       byte mask = (byte) 0x0F;
       byte [] hexa = new byte[2];
       String s;

       hexa[0] = (((b >> 4) & mask) > 0x9) ? (byte) (0x37 + ((b >> 4) & mask)) : (byte) (0x30 + ((b >> 4) & mask));
       hexa[1] = ((b & mask) > 0x9) ? (byte) (0x37 + (b & mask)) : (byte) (0x30 + (b & mask));
       s = new String (hexa);

       return s;
    }
}
