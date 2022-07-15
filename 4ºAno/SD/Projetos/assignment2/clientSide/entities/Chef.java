package clientSide.entities;

import clientSide.stubs.*;

/**
 *    Chef thread.
 *
 *      It simulates the chef life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public class Chef extends Thread{
	
	/**
	 *	Chef state 
	 */
	private int chefState;
	
	/**
	 * Reference to the stub of the kitchen
	 */
	private final KitchenStub kitStub;
	
	/**
	 * Reference to the stub of the bar
	 */
	private final BarStub barStub;
	
	
	
	/**
	 * Set a new chef state
	 * @param chefState new state to be set
	 */
	public void setChefState(int chefState)
	{
		this.chefState = chefState;
	}	
	
	
	/**
	 * Get the chef's state
	 * @return chef state
	 */
	public int getChefState()
	{
		return chefState;
	}
	
	
	/**
	 * Instantiation of a Chef thread
	 * 	@param name thread name
	 * 	@param kitStub reference to the kitchen stub
	 * 	@param barStub reference to the bar stub
	 */
	public Chef(String name, KitchenStub kitStub, BarStub barStub) {
		super(name);
		this.chefState = ChefStates.WAITING_FOR_AN_ORDER;
		this.kitStub = kitStub;
		this.barStub = barStub;
	}

	
	
	
	/**
	 * 	Life cycle of the chef
	 */
	@Override
	public void run ()
	{
		boolean firstCourse = true;
		
		kitStub.watchTheNews();
		kitStub.startPreparation();
		do
		{
			if(!firstCourse)
				kitStub.continuePreparation();
			else
				firstCourse = false;
			
			kitStub.proceedPreparation();
			barStub.alertWaiter();
			
			while(!kitStub.haveAllPortionsBeenDelivered())
				kitStub.haveNextPortionReady();
		}
		while(!kitStub.hasOrderBeenCompleted());
		
		kitStub.cleanUp();
	}
}
