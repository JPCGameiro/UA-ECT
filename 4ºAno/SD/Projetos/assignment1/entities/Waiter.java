package entities;

import sharedRegions.*;


/**
 *   Waiter thread.
 *
 *   Used to simulate the Waiter life cycle.
 */

public class Waiter extends Thread{

	/**
	 * 	Waiter state
	 */
	private int waiterState;
	
	/**
	 * Reference to the kitchen
	 */
	private final Kitchen kit;
	
	/**
	 * Reference to the bar
	 */
	private final Bar bar;
	
	/**
	 * Reference to the table
	 */
	private final Table tab;
	

	/**
	 * Waiter thread instantiation
	 * 
	 * @param name Name of the thread
	 * @param kit reference to the kitchen
	 * @param bar reference to the bar
	 * @param tab reference to the table
	 */
	public Waiter(String name, Kitchen kit, Bar bar, Table tab) {
		super(name);
		this.waiterState = WaiterStates.APRAISING_SITUATION;
		this.kit = kit;
		this.bar = bar;
		this.tab = tab;
	}
	
	
	/**
	 * 
	 * @param waiterState new state to be set
	 */
	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}
	
	/**
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
			request = bar.lookAround();
			
			switch(request)
			{
				case 'c':	//Client arriving, needs to be presented with the menu
					tab.saluteClient(bar.getStudentBeingAnswered());
					tab.returnBar();
					break;
				case 'o':	//Order will be described to the waiter
					tab.getThePad();
					kit.handNoteToChef();
					kit.returnToBar();
					break;
				case 'p':	//Portions need to be collected and delivered
					while(!tab.haveAllClientsBeenServed()) 
					{
						kit.collectPortion();
						tab.deliverPortion();
					}
					tab.returnBar();
					break;
				case 'b':	//Bill needs to be prepared so it can be payed by the student
					bar.preprareBill();
					tab.presentBill();
					tab.returnBar();
					break;
				case 'g':	//Goodbye needs to be said to a student
					stop = bar.sayGoodbye();
					break;
			}
			//If the last student has left the restaurant, life cycle may terminate
			if (stop)
				break;
		}
	}
}
