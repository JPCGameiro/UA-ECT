package commInfra;

import genclass.GenericIO;
import java.io.*;
import java.net.*;

/**
 *   Communication manager - server side.
 *
 *   Communication is based on message passing over sockets using the TCP protocol.
 *   It supposes the setup of a communication channel between the two end points before data transfer can take place.
 *   Data transfer is bidirectional and is made through the transmission and the reception of objects in output and
 *   input streams, respectively.
 */

public class ServerCom
{
  /**
   *  Listening socket.
   */

   private ServerSocket listeningSocket = null;

  /**
   *  Communication socket.
   */

   private Socket commSocket = null;

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
   *  Instantiation of a communication channel (form 1).
   *
   *    @param portNumb number of the listening port at the computational system where the server is located
   */

   public ServerCom (int portNumb)
   {
      serverPortNumb = portNumb;
   }

  /**
   *  Instantiation of a communication channel (form 2).
   *
   *    @param portNumb number of the listening port at the computational system where the server is located
   *    @param lSocket listening socket
   */

   public ServerCom (int portNumb, ServerSocket lSocket)
   {
      serverPortNumb = portNumb;
      listeningSocket = lSocket;
   }

  /**
   *  Service setup.
   *
   *  Instantiation of the listening socket, its binding to the public server address and port number and
   *  fixing a listening timeout.
   */

   public void start ()
   {
      try
      { listeningSocket = new ServerSocket (serverPortNumb);
        listeningSocket.setSoTimeout (1000);                                   // fixing a 1 s time out in listening to a connection request
      }
      catch (BindException e)                              // fatal error --- port already in use
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible the association of the listening socket to the port: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (SocketException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - an error has occurred on fixing a listening timeout!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)                                // fatal error --- other reasons
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - an indeterminate error has occurred in establishing the connection at: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Service shutdown.
   *
   *  The listening socket is closed.
   */

   public void end ()
   {
      try
      { listeningSocket.close ();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the listening socket!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Listening process.
   *
   *  Instantiation of a communication channel for a pending request.
   *  Instantiation of the communication socket and its binding to the client address.
   *  The socket input and output streams are opened.
   *
   *    @return reference to the commmunication channel
   *    @throws SocketTimeoutException when a timeout is reached on the listening process
   */

   public ServerCom accept () throws SocketTimeoutException
   {
      ServerCom scon;                                      // communication channel

      scon = new ServerCom(serverPortNumb, listeningSocket);
      try
      { scon.commSocket = listeningSocket.accept();
      }
      catch (SocketTimeoutException e)
      { throw new SocketTimeoutException ("Listening timeout!");
      }
      catch (SocketException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - the listening socket was closed during the listening process!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to instantiate a communication channel for the pending request!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.in = new ObjectInputStream (scon.commSocket.getInputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to open the input stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.out = new ObjectOutputStream (scon.commSocket.getOutputStream ());
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to open the output stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      return scon;
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
      { in.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the input stream!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { out.close();
      }
      catch (IOException e)
      { GenericIO.writelnString (Thread.currentThread ().getName () +
                                 " - it was not possible to close the output stream!");
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
      Object fromClient = null;;                           // object that is read

      try
      { fromClient = in.readObject ();
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

      return fromClient;
   }

  /**
   *  Object write to the communication channel.
   *
   *    @param toClient reference to the object to be written
   */

   public void writeObject (Object toClient)
   {
      try
      { out.writeObject (toClient);
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
