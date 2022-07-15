package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type RegisterRemoteObject.
 *
 *     It provides the functionality to register remote objects in the local RMI registry service.
 */

public interface Register extends Remote
{
  /**
   *  Binds a remote reference to the specified name in this registry.
   *
   *     @param name the name to associate with the reference to the remote object
   *     @param ref reference to the remote object
   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                             service fails
   *     @throws AlreadyBoundException if the name is already registered
   */

   public void bind (String name, Remote ref) throws RemoteException, AlreadyBoundException;

 /**
   *  Removes the binding for the specified name in this registry.
   *
   *     @param name the name associated with the reference to the remote object
   *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
   *                             service fails
   *     @throws NotBoundException if the name is not in registered
   */

   public void unbind (String name) throws RemoteException, NotBoundException;

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

   public void rebind (String name, Remote ref) throws RemoteException;
}
