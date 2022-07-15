package serverSide.entities;

import clientSide.entities.*;
import commInfra.Message;
import commInfra.MessageException;
import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.TableInterface;

/**
 *  Service provider agent for access to the Table.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class TableClientProxy extends Thread implements WaiterCloning, StudentCloning {

	/**
	 *  Number of instantiated threads.
	 */
	private static int nProxy = 0;

	/**
	 *  Communication channel.
	 */
	private ServerCom sconi;

	/**
	 *  Interface to the Table
	 */
	private TableInterface tabInter;

	/**
	 * State of the waiter
	 */
	private int waiterState;

	/**
	 * State of the student
	 */
	private int studentState;

	/**
	 * Id of the student
	 */
	private int studentId;

	/**
	 * Id of the student whose request the waiter is taking care of
	 */
	private int studentBeingAnswered;

	
	/**
	 *  Instantiation of a client proxy.
	 *
	 *     @param sconi communication channel
	 *     @param tab interface to the table
	 */
	public TableClientProxy (ServerCom sconi, TableInterface tab)
	{
		super ("TableProxy_" + TableClientProxy.getProxyId ());
		this.sconi = sconi;
		this.tabInter = tab;
	}

	
	/**
	 *  Generation of the instantiation identifier.
	 *
	 *     @return instantiation identifier
	 */
	private static int getProxyId ()
	{
		Class<?> cl = null;			// representation of the TableClientProxy object in JVM
		int proxyId;				// instantiation identifier

		try
		{ cl = Class.forName ("serverSide.entities.TableClientProxy");
		}
		catch (ClassNotFoundException e)
		{ GenericIO.writelnString ("Data type TableClientProxy was not found!");
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
	 * Set student Id
	 * 	@param id id of the student
	 */
	public void setStudentId(int id) { studentId = id; }

	/**
	 * Get student id
	 * 	@return id of the student
	 */
	public int getStudentId() {	return studentId; }

	/**
	 * Set student state
	 * 	@param state state of the student
	 */
	public void setStudentState(int state) { studentState = state; }

	/**
	 * Get student state
	 * 	@return student state
	 */
	public int getStudentState() { return studentState; }

	/**
	 * Set waiter state
	 * 	@param state state of the waiter
	 */
	public void setWaiterState(int state) {	waiterState = state; }

	/**
	 * Get waiter state
	 *	@return state of the waiter
	 */
	public int getWaiterState() { return waiterState; }

	/**
	 * Set studentBeingAnswered Id
	 * 	@param id studentBeingAnswered ID
	 */
	public void setStudentBeingAnswered(int id) {	studentBeingAnswered = id; }

	/**
	 * Get studentBeingAnswered Id
	 *	@return id studentBeingAnswered
	 */
	public int getStudentBeingAnswered() { return studentBeingAnswered;	}

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
		{ outMessage = tabInter.processAndReply (inMessage);         // process it
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
