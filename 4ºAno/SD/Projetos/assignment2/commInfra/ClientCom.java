package commInfra;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *   Communication manager - client side.
 *
 *   Communication is based on message passing over sockets using the TCP protocol.
 *   It supposes the setup of a communication channel between the two end points before data transfer can take place.
 *   Data transfer is bidirectional and is made through the transmission and the reception of objects in output and
 *   input streams, respectively.
 */

public class ClientCom
{
	
  /**
   *  Communication socket.
   */
   private Socket commSocket = null;

  /**
   *  Name of the computational system where the server is located.
   */
   private String serverHostName;

  /**
   *  Number of the listening port at the computational system where the server is located.
   */
   private int serverPortNumb;

  /**
   *  Input stream of the communication channel.
   */
   private ObjectInputStream in = null;

  /**
   *  Output stream of the communication channel.
   */
   private ObjectOutputStream out = null;

   
  /**
   *  Instantiation of a communication channel.
   *
   *    @param hostName name of the computational system where the server is located
   *    @param portNumb number of the listening port at the computational system where the server is located
   */

   public ClientCom (String hostName, int portNumb)
   {
      serverHostName = hostName;
      serverPortNumb = portNumb;
   }

  /**
   *  Open the communication channel.
   *
   *  Instantiation of the communication socket and its binding to the server address.
   *  The socket input and output streams are opened.
   *
   *    @return true, if the communication channel is opened -
   *            false, otherwise
   */

   public boolean open ()
   {
      boolean success = true;                                                                      // flag signaling
                                                                                                   // success on opening the communication channel
      SocketAddress serverAddress = new InetSocketAddress (serverHostName, serverPortNumb);        // inet address

      try
      { commSocket = new Socket();
        commSocket.connect (serverAddress);
      }
      catch (UnknownHostException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the name of the computational system where the server is located, is unknown: " +
                                 serverHostName + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (NoRouteToHostException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the name of the computational system where the server is located, is unreachable: " +
                                 serverHostName + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (ConnectException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the server does not respond at: " + serverHostName + "." + serverPortNumb + "!");
        if (e.getMessage ().equals ("Connection refused"))
           success = false;
           else { GenericIO.writelnString (e.getMessage () + "!");
                  e.printStackTrace ();
                  System.exit (1);
                }
      }
      catch (SocketTimeoutException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - time out has occurred in establishing the connection at: " +
                                 serverHostName + "." + serverPortNumb + "!");
        success = false;
      }
      catch (IOException e)                                // fatal error --- other reasons
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - an indeterminate error has occurred in establishing the connection at: " +
                                 serverHostName + "." + serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }

      if (!success) return (success);

      try
      { out = new ObjectOutputStream (commSocket.getOutputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to open the output stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { in = new ObjectInputStream (commSocket.getInputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to open the input stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      return (success);
   }

  
   /**
   *  Close the communication channel.
   *
   *  The socket input and output streams are closed.
   *  The communication socket is closed.
   */
   public void close ()
   {
      try
      { out.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the output stream!!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { in.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the input stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { commSocket.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the communication socket!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Object read from the communication channel.
   *
   *    @return reference to the object that was read
   */

   public Object readObject ()
   {
      Object fromServer = null;                            // object that is read

      try
      { fromServer = in.readObject ();
      }
      catch (InvalidClassException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the read object could not be deserialized!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - error on reading an object from the input stream!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (ClassNotFoundException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the read object belongs to an unknown data type!");
        e.printStackTrace ();
        System.exit (1);
      }

      return fromServer;
   }

  /**
   *  Object write to the communication channel.
   *
   *    @param toServer reference to the object to be written
   */

   public void writeObject (Object toServer)
   {
      try
      { out.writeObject (toServer);
      }
      catch (InvalidClassException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the object to be written can not be serialized!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (NotSerializableException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the object to be written does not implement the Serializable interface!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - error on writing an object to the output stream!");
        e.printStackTrace ();
        System.exit (1);
      }
   }
}
