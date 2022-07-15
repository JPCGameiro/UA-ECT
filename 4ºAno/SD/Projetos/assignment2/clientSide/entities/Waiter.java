package clientSide.entities;

import clientSide.stubs.*;

/**
 *    Waiter thread.
 *
 *      It simulates the waiter life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public class Waiter extends Thread{

	/**
	 * 	Waiter state
	 */
	private int waiterState;
	
	/**
	 * Reference to the stub of the kitchen
	 */
	private final KitchenStub kitStub;
	
	/**
	 * Reference to the stub of the bar
	 */
	private final BarStub barStub;
	
	/**
	 * Reference to the stub of the table
	 */
	private final TableStub tabStub;
	

	/**
	 * Instantiation of a Waiter thread
	 * 
	 * 	@param name thread name
	 * 	@param kitStub reference to the stub of the kitchen
	 * 	@param barStub reference to the stub of the bar
	 * 	@param tabStub reference to the stub of the table
	 */
	public Waiter(String name, KitchenStub kitStub, BarStub barStub, TableStub tabStub) {
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
	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}
	
	/**
	 * 	Get waiter state
	 * 	@return waiter state
	 */
	public int getWaiterState() {
		return waiterState;
	}
	
	
	
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
			request = barStub.lookAround();
			switch(request)
			{
				case 'c':	//Client arriving, needs to be presented with the menu
					tabStub.saluteClient(barStub.getStudentBeingAnswered());
					tabStub.returnBar();
					break;
				case 'o':	//Order will be described to the waiter
					tabStub.getThePad();
					kitStub.handNoteToChef();
					kitStub.returnToBar();
					break;
				case 'p':	//Portions need to be collected and delivered
					while(!tabStub.haveAllClientsBeenServed()) 
					{
						kitStub.collectPortion();
						tabStub.deliverPortion();
					}
					tabStub.returnBar();
					break;
				case 'b':	//Bill needs to be prepared so it can be payed by the student
					barStub.prepareBill();
					tabStub.presentBill();
					tabStub.returnBar();
					break;
				case 'g':	//Goodbye needs to be said to a student
					stop = barStub.sayGoodbye();
					break;
			}
			//If the last student has left the restaurant, life cycle may terminate
			if (stop)
				break;
		}
	}
}
