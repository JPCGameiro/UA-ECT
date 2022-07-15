package serverSide.entities;

import clientSide.entities.*;
import commInfra.Message;
import commInfra.MessageException;
import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.BarInterface;

/**
 *  Service provider agent for access to the Bar.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class BarClientProxy extends Thread implements ChefCloning, WaiterCloning, StudentCloning{

	/**
	 *  Number of instantiated threads.
	 */
	private static int nProxy = 0;

	/**
	 *  Communication channel.
	 */
	private ServerCom sconi;

	/**
	 *  Interface to the Bar.
	 */
	private BarInterface barInter;

	/**
	 * Chef State
	 */
	private int chefState;

	/**
	 * Waiter State
	 */
	private int waiterState;

	/**
	 * Student state
	 */
	private int studentState;

	/**
	 * Student id
	 */
	private int studentId;

	/**
	 *  Instantiation of a client proxy.
	 *
	 *     @param sconi communication channel
	 *     @param bar interface to the bar
	 */
	public BarClientProxy (ServerCom sconi, BarInterface bar)
	{
		super ("BarProxy_" + BarClientProxy.getProxyId ());
		this.sconi = sconi;
		this.barInter = bar;
	}

	/**
	 *  Generation of the instantiation identifier.
	 *
	 *     @return instantiation identifier
	 */
	private static int getProxyId ()
	{
		Class<?> cl = null;			// representation of the BarClientProxy object in JVM
		int proxyId;				// instantiation identifier

		try
		{ cl = Class.forName ("serverSide.entities.BarClientProxy");
		}
		catch (ClassNotFoundException e)
		{ GenericIO.writelnString ("Data type BarClientProxy was not found!");
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
	 * Set waiter state
	 * 	@param state state of the waiter
	 */
	public void setWaiterState(int state) { waiterState = state; }

	/**
	 * Get waiter state
	 * 	@return state of the waiter
	 */
	public int getWaiterState() { return waiterState; }

	/**
	 * Set chef state
	 * 	@param state chef state
	 */
	public void setChefState(int state) { chefState = state; }

	/**
	 * Get chef state
	 * 	@return state of the chef
	 */
	public int getChefState() {	return chefState; }


	/**
	 * Set student id
	 * 	@param id  id of the student
	 */
	public void setStudentId(int id) { studentId = id; }

	/**
	 * Get Student Id
	 * 	@return student id
	 */
	public int getStudentId() {	return studentId; }

	/**
	 * Set student state
	 * 	@param state of the student
	 */
	public void setStudentState(int state) {  studentState = state; }

	/**
	 * Get student state
	 * 	@return student state
	 */
	public int getStudentState() { return studentState; }

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
		{ outMessage = barInter.processAndReply (inMessage);         // process it
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
