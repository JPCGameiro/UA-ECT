package clientSide.entities;

import java.rmi.RemoteException;

import genclass.GenericIO;
import interfaces.*;

/**
 *    Waiter thread.
 *
 *      It simulates the waiter life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on remote calls under Java RMI.
 */
public class Waiter extends Thread{
	/**
	 * 	Waiter state
	 */
	private int waiterState;
	
	/**
	 * Reference to the stub of the kitchen
	 */
	private final KitchenInterface kitStub;
	
	/**
	 * Reference to the stub of the bar
	 */
	private final BarInterface barStub;
	
	/**
	 * Reference to the stub of the table
	 */
	private final TableInterface tabStub;

	
	
	/**
	 * Instantiation of a Waiter thread
	 * 
	 * 	@param name thread name
	 * 	@param kitStub reference to the stub of the kitchen
	 * 	@param barStub reference to the stub of the bar
	 * 	@param tabStub reference to the stub of the table
	 */
	public Waiter(String name, KitchenInterface kitStub, BarInterface barStub, TableInterface tabStub) {
		super(name);
		this.waiterState = WaiterStates.APRAISING_SITUATION;
		this.kitStub = kitStub;
		this.barStub = barStub;
		this.tabStub = tabStub;
	}
	
	
	/**
	 * Set a new waiter state
	 * @param waiterState new state to be set
	 */
	public void setWaiterState(int waiterState) { this.waiterState = waiterState; }
	
	/**
	 * 	Get waiter state
	 * 	@return waiter state
	 */
	public int getWaiterState() { return waiterState; }
	
	
	
	/**
	 *	Life cycle of the waiter
	 */
	@Override
	public void run ()
	{
		//used to store the request that needs to be performed by the waiter
		char request;
		//used to check if simulation may stop or not
		boolean stop = false;
		
		while(true)
		{
			request = lookAround();
			switch(request)
			{
				case 'c':	//Client arriving, needs to be presented with the menu
					saluteClient(getStudentBeingAnswered());
					kitReturnBar();
					break;
				case 'o':	//Order will be described to the waiter
					getThePad();
					handNoteToChef();
					tabReturnBar();
					break;
				case 'p':	//Portions need to be collected and delivered
					while(!haveAllClientsBeenServed()) 
					{
						collectPortion();
						deliverPortion();
					}
					tabReturnBar();
					break;
				case 'b':	//Bill needs to be prepared so it can be payed by the student
					prepareBill();
					presentBill();
					tabReturnBar();
					break;
				case 'g':	//Goodbye needs to be said to a student
					stop = sayGoodbye();
					break;
			}
			//If the last student has left the restaurant, life cycle may terminate
			if (stop)
				break;
		}
	}
	
	
	
	
	/**
	 * Operation look Around
	 * Remote operation.
	 * It is called by the waiter, he checks for pending service requests and if not waits for them
	 * 	@return Character that represents the service to be executed
	 * 		'c' : means a client has arrived therefore needs to be presented with the menu by the waiter
	 * 		'o' : means that the waiter will hear the order and deliver it to the chef
	 * 		'p' : means that a portion needs to be delivered by the waiter
	 * 		'b' : means that the bill needs to be prepared and presented by the waiter
	 * 		'g' : means that some student wants to leave and waiter needs to say goodbye 
	 */
	private char lookAround()
	{
		char c = '\0';
		try 
		{ c = barStub.lookAround();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on lookAround: " +e.getMessage());
			System.exit(1);
		}
		if(c!='c' && c!='o' && c!='p' && c!='b' && c!='g')
		{
			GenericIO.writelnString("Invalid service type!");
			System.exit(1);			
		}
		return c;
	}
	
	
	
    /**
     * Operation salute the client
     * Remote operation.
     * It is called by the waiter when a student enters the restaurant and needs to be saluted
     * Waiter waits for the student to take a seat (if he hasn't done it yet)
     * Waiter waits for student to finish reading the menu
     * 	@param studentIdBeingAnswered id of the student whose request is being answered
     */
    private void saluteClient(int studentIdBeingAnswered)
    {
		try 
		{ waiterState = tabStub.saluteClient(studentIdBeingAnswered);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on saluteClient: " +e.getMessage());
			System.exit(1);
		}    	
    }
    
    
    
	/**
	 * Return id of the student whose request is being answered
	 * Remote operation.
	 * @return Id of the student whose request is being answered
	 */
	private int getStudentBeingAnswered()
	{
		int studentId = -1;
		try 
		{ studentId = barStub.getStudentBeingAnswered();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on getStudentBeingAnswered: " +e.getMessage());
			System.exit(1);
		}
		if(studentId == -1)
		{
			GenericIO.writelnString("Invalid student id!");
			System.exit(1);			
		}
		
		return studentId;
	}
	
	
	
	/**
	 * Operation return to the bar
	 * Remote operation.
	 * Called by the waiter when he is in the kitchen and returns to the bar
	 */
	private void kitReturnBar()
	{
		try 
		{ waiterState = kitStub.returnToBar();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on kitReturnToBar: " +e.getMessage());
			System.exit(1);
		}		
	}
	
	
	/**
	 * Operation return to the bar
	 * Remote operation.
	 * Called by the waiter when he is in the table and returns to the bar
	 */
	private void tabReturnBar()
	{
		try 
		{ waiterState = tabStub.returnBar();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on tabReturnToBar: " +e.getMessage());
			System.exit(1);
		}		
	}
	
	
	
    /**
     * Operation get the pad
     * Remote operation.
     * It is called by the waiter when an order is going to be described by the first student to arrive
     * Waiter Blocks waiting for student to describe him the order
     */
    private void getThePad()
    {
		try 
		{ waiterState = tabStub.getThePad();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on getThePad: " +e.getMessage());
			System.exit(1);
		}   	
    }
    
    
    
	/**
	 * Operation hand note to chef
	 * Remote operation.
	 * Called by the waiter to wake chef up chef to give him the description of the order
	 */	
	private void handNoteToChef()
	{
		try 
		{ waiterState = kitStub.handNoteToChef();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on handNoteToChef: " +e.getMessage());
			System.exit(1);
		}   		
	}
	
	
	
    /**
     * Operation have all clients been served
     * Remote operation.
     * Called by the waiter to check if all clients have been served or not
     * @return true if all clients have been served, false otherwise
     */
    private boolean haveAllClientsBeenServed()
    {
    	boolean value = false;
		try 
		{ value = tabStub.haveAllClientsBeenServed();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on haveAllClientsBeenServed: " +e.getMessage());
			System.exit(1);
		} 
		return value;
    }
    
    
    
	/**
	 * Operation collect portion
	 * Remote operation.
	 * Called by the waiter when there is a portion to be delivered. Collect and signal chef that the portion was delivered
	 */
	private void collectPortion()
	{
		try 
		{ waiterState = kitStub.collectPortion();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on collectPortion: " +e.getMessage());
			System.exit(1);
		} 		
	}
	
	
	
    /**
     * Operation deliver portion
     * Remote operation.
     * Called by the waiter, to deliver a portion
     */
    private void deliverPortion()
    {
		try 
		{ tabStub.deliverPortion();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on deliverPortion: " +e.getMessage());
			System.exit(1);
		}    	
    }
    
    
    
	/**
	 * Operation prepare the Bill
	 * Remote operation.
	 * It is called the waiter to prepare the bill of the meal eaten by the students
	 */
	private void prepareBill()
	{
		try 
		{ waiterState = barStub.prepareBill();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on prepareBill: " +e.getMessage());
			System.exit(1);
		} 		
	}
	
	
	
    /**
     * Operation present the bill
     * Remote operation.
     * Called by the waiter to present the bill to the last student to arrive
     */
    private void presentBill()
    {
		try 
		{ waiterState = tabStub.presentBill();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on presentBill: " +e.getMessage());
			System.exit(1);
		}    	
    }
    
    
    
	/**
	 * Operation say Goodbye
	 * Remote operation
	 * It is called by the waiter to say goodbye to a student that's leaving the restaurant
	 * @return true if there are no more students at the restaurant, false otherwise
	 */
	private boolean sayGoodbye()
	{
		boolean bValue = false;
		try 
		{ bValue = barStub.sayGoodbye();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Waiter remote exception on sayGoodbye: " +e.getMessage());
			System.exit(1);
		}
		return bValue;
	}
}
