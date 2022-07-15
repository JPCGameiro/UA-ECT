package serverSide.entities;

import clientSide.entities.*;
import commInfra.Message;
import commInfra.MessageException;
import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.KitchenInterface;

/**
 *  Service provider agent for access to the Kitchen.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class KitchenClientProxy extends Thread implements ChefCloning, WaiterCloning{

	/**
	 *  Number of instantiated threads.
	 */
	private static int nProxy = 0;

	/**
	 *  Communication channel.
	 */
	private ServerCom sconi;

	/**
	 *  Interface to the Kitchen.
	 */
	private KitchenInterface kitInter;

	/**
	 * Chef state
	 */
	private int chefState;

	/**
	 * Waiter state
	 */
	private int waiterState;

	/**
	 *  Instantiation of a client proxy.
	 *
	 *     @param sconi communication channel
	 *     @param kit interface to the kitchen
	 */
	public KitchenClientProxy (ServerCom sconi, KitchenInterface kit)
	{
		super ("KitchenProxy_" + KitchenClientProxy.getProxyId ());
		this.sconi = sconi;
		this.kitInter = kit;
	}

	/**
	 *  Generation of the instantiation identifier.
	 *
	 *     @return instantiation identifier
	 */

	private static int getProxyId ()
	{
		Class<?> cl = null;			// representation of the KitchenClientProxy object in JVM
		int proxyId;				// instantiation identifier

		try
		{ cl = Class.forName ("serverSide.entities.KitchenClientProxy");
		}
		catch (ClassNotFoundException e)
		{ GenericIO.writelnString ("Data type KitchenClientProxy was not found!");
		e.printStackTrace ();
		System.exit (1);
		}
		synchronized (cl)
		{ proxyId = nProxy;
		nProxy += 1;
		}
		return proxyId;
	}

	/**
	 * Set chef state
	 * @param state state of the chef
	 */
	public void setChefState(int state) { this.chefState = state; }

	/**
	 * Get chef state
	 * @return chef state
	 */
	public int getChefState() { return this.chefState; }

	/**
	 * Set waiter state
	 * @param state state of the waiter
	 */
	public void setWaiterState(int state) { this.waiterState = state; }

	/**
	 * Get waiter state
	 * @return waiter state
	 */
	public int getWaiterState() { return this.waiterState; }

	/**
	 *  Life cycle of the service provider agent.
	 */
	@Override
	public void run ()
	{
		Message inMessage = null,                                      // service request
				outMessage = null;                                     // service reply

		/* service providing */

		inMessage = (Message) sconi.readObject ();                     // get service request
		try
		{ outMessage = kitInter.processAndReply (inMessage);         // process it
		}
		catch (MessageException e)
		{ GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
		GenericIO.writelnString (e.getMessageVal ().toString ());
		System.exit (1);
		}
		sconi.writeObject (outMessage);                                // send service reply
		sconi.close ();                                                // close the communication channel
	}
}
