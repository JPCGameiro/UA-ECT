package clientSide.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import clientSide.entities.Waiter;
import genclass.GenericIO;
import interfaces.BarInterface;
import interfaces.GeneralReposInterface;
import interfaces.KitchenInterface;
import interfaces.TableInterface;

/**
 *    Client side of the Restaurant problem (waiter).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */
public class ClientRestaurantWaiter {

	/**
	 *	Main method. 
	 * 		@param args runtime arguments
	 * 			args[0] - name of the platform where is located the RMI registering service
	 * 			args[1] - port number where the registering service is listening to service requests 
	 */	
	public static void main(String[] args) 
	{
		//Name of the platform where is located the RMI registering service
		String rmiRegHostName;
		//Port number where the registering service is listening to service request
		int rmiRegPortNumb = -1;
		
		/* getting problem runtime parameters */
		
		if(args.length != 2) 
		{
			GenericIO.writelnString ("Wrong number of parameters!");
			System.exit (1);			
		}
		rmiRegHostName = args[0];
		
		try { 
			rmiRegPortNumb = Integer.parseInt(args[1]); 
		} catch(NumberFormatException e) {
			GenericIO.writelnString ("args[1] is not a valid port number!");
			System.exit (1);			
		}

		/* problem initialisation */

		String nameEntryGeneralRepos = "GeneralRepository";	//Public name of the General Repository object
		String nameEntryKitchen = "Kitchen";				//Public name of the Kitchen object 
		String nameEntryBar = "Bar";						//Public name of the Bar object
		String nameEntryTable = "Table";					//Public name of the Table object
		GeneralReposInterface reposStub = null;				//Remote reference to the General Repository object
		KitchenInterface kitStub = null;					//Remote reference for the Kitchen object
		BarInterface barStub = null;						//Remote reference for the Bar object
		TableInterface tabStub = null;						//Remote reference for the Table object
		Registry registry = null;							//Remote reference for registration in the RMI Registry service
		Waiter waiter;										//Waiter Thread
		
		//Locate RMI Registry server
		try {
			registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
		} catch (RemoteException e) {
			GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}

		//Locate GeneralRepos Server
		try {
			reposStub = (GeneralReposInterface) registry.lookup(nameEntryGeneralRepos);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("General Repository lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("General Repository not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}
		
		//Locate Kitchen Server
		try {
			kitStub = (KitchenInterface) registry.lookup(nameEntryKitchen);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("Kitchen lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("Kitchen not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}
		
		//Locate Bar Server
		try {
			barStub = (BarInterface) registry.lookup(nameEntryBar);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("Bar lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("Bar not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}

		//Locate Table Server
		try {
			tabStub = (TableInterface) registry.lookup(nameEntryTable);
		} catch( RemoteException e ) {
			GenericIO.writelnString ("Table lookup exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		} catch( NotBoundException e ) {
			GenericIO.writelnString ("Table not bound exception: " + e.getMessage ());
	        e.printStackTrace ();
	        System.exit (1);			
		}
		
		//initialise Waiter thread
		waiter = new Waiter("waiter", kitStub, barStub, tabStub);
		
		/* start of the simulation */
		waiter.start();
		
		/* waiting for the end of the simulation */
		try {
			waiter.join();
		} catch (InterruptedException e) {}
		GenericIO.writelnString ("The waiter thread has terminated.");
		
		//Kitchen shutdown
		try {
			kitStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Chef generator remote exception on Bar shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
		//Table shutdown
		try {
			tabStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Waiter generator remote exception on Table shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
		//Bar shutdown
		try {
			barStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Waiter generator remote exception on Kitchen shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
		//GeneralRepos shutdown
		try {
			reposStub.shutdown();
		} catch(RemoteException e) {
			GenericIO.writelnString ("Waiter generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	        System.exit (1);			
		}
	}

}
