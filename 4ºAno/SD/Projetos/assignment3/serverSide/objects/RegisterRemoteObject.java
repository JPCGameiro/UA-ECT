package serverSide.objects;

import java.rmi.registry.*;
import java.rmi.*;
import interfaces.*;

/**
 *   Generic functionality to register in the local RMI registry service objects located
 *   in the same or other processing nodes of a parallel machine.
 *     Communication is based on Java RMI.
 */

public class RegisterRemoteObject implements Register
{
  /**
   *  Name of the local host (where the RMI registering service is supposed to be located).
   */

   private String rmiRegHostName;

  /**
   *  Port number where the local RMI registering service is listening to.
   */

   private int rmiRegPortNumb = 1099;

  /**
   *  Instantiation of a registering object.
   *
   *     @param rmiRegHostName name of local host
   *     @param rmiRegPortNumb port number where the local registering service is listening to
   */

   public RegisterRemoteObject (String rmiRegHostName, int rmiRegPortNumb)
   {
      if ((rmiRegHostName == null) || ("".equals (rmiRegHostName)))
         throw new NullPointerException ("RegisterRemoteObject: null parameter on instantiation!");
      this.rmiRegHostName = rmiRegHostName;
      if ((rmiRegPortNumb >= 4000) && (rmiRegPortNumb <= 65535))
         this.rmiRegPortNumb = rmiRegPortNumb;
   }

  /**
   *  Binds a remote reference to the specified name in this registry.
   *
   *     @param name the name to associate with the reference to the remote object
   *     @param ref reference to the remote object
   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                             service fails
   *     @throws AlreadyBoundException if the name is already registered
   */

   @Override
   public void bind (String name, Remote ref) throws RemoteException, AlreadyBoundException
   {
     Registry registry;

     if ((name == null) || (ref == null))
        throw new NullPointerException ("RegisterRemoteObject: null parameter(s) on on bind!");
     registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
     registry.bind (name, ref);
   }

  /**
   *  Removes the binding for the specified name in this registry.
   *
   *     @param name the name associated with the reference to the remote object
   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                             service fails
   *     @throws NotBoundException if the name is not in registered
   */

   @Override
   public void unbind (String name) throws RemoteException, NotBoundException
   {
     Registry registry;

     if ((name == null))
        throw new NullPointerException ("RegisterRemoteObject: null parameter(s) on unbind!");
     registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
     registry.unbind (name);
   }

  /**
   *  Replaces the binding for the specified name in this registry with the supplied remote reference.
   *
   *  If a previous binding for the specified name exists, it is discarded.
   *
   *    @param name the name to associate with the reference to the remote object
   *    @param ref reference to the remote object
   *    @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                            service fails
   */

   @Override
   public void rebind (String name, Remote ref) throws RemoteException
   {
     Registry registry;

     if ((name == null) || (ref == null))
        throw new NullPointerException ("RegisterRemoteObject: null parameter(s) on rebind!");
     registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
     registry.rebind (name, ref);
   }
}
