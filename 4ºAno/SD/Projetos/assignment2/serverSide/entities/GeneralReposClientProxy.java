package serverSide.entities;

import serverSide.sharedRegions.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Service provider agent for access to the General Repository of Information.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposClientProxy extends Thread
{
	/**
	 *  Number of instantiated threads.
	 */
	private static int nProxy = 0;

	/**
	 *  Communication channel.
	 */
	private ServerCom sconi;

	/**
	 *  Interface to the General Repository of Information.
	 */
	private GeneralReposInterface reposInter;

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
	 * value of either number courses, number portions, seat at table
	 */
	private int value;
	
	/**
	 *  Instantiation of a client proxy.
	 *
	 *     @param sconi communication channel
	 *     @param reposInter interface to the general repository of information
	 */
	public GeneralReposClientProxy (ServerCom sconi, GeneralReposInterface reposInter)
	{
		super ("GeneralReposProxy_" + GeneralReposClientProxy.getProxyId ());
		this.sconi = sconi;
		this.reposInter = reposInter;
	}

	/**
	 * Get chef state
	 * @return chef state
	 */
	public int getChefState() {
		return chefState;
	}

	/**
	 * Set chef state
	 * @param chefState state of the chef
	 */
	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

	/**
	 * Get waiter state
	 * @return waiter state
	 */
	public int getWaiterState() {
		return waiterState;
	}

	/**
	 * Set waiter state
	 * @param waiterState state of the waiter
	 */
	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	/**
	 * Get student state
	 * 	@return student state
	 */
	public int getStudentState() {
		return studentState;
	}

	/**
	 * Set student state
	 * 	@param studentState state of the student
	 */
	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	/**
	 * Get student id
	 * 	@return id of the student
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * Set student id
	 * @param studentId id of the student
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	/**
	 * Get value of number courses, portions or seat at table
	 * 	@return number of courses/portions/seat at table
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set value of number courses, portions or seat at table
	 * 	@param value number of courses/portions/seat at table
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 *  Generation of the instantiation identifier.
	 *
	 *     @return instantiation identifier
	 */
	private static int getProxyId ()
	{
		Class<?> cl = null;		// representation of the GeneralReposClientProxy object in JVM
		int proxyId;				// instantiation identifier

		try
		{ cl = Class.forName ("serverSide.entities.GeneralReposClientProxy");
		}
		catch (ClassNotFoundException e)
		{ GenericIO.writelnString ("Data type GeneralReposClientProxy was not found!");
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
		{ outMessage = reposInter.processAndReply (inMessage);         // process it
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
