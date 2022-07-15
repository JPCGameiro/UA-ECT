package entities;

import sharedRegions.Bar;
import sharedRegions.Kitchen;

/**
 *   Chef thread.
 *
 *   Used to simulate the Chef life cycle.
 */

public class Chef extends Thread{
	
	/**
	 *	Chef state 
	 */
	private int chefState;
	
	/**
	 * Reference to the kitchen
	 */
	private final Kitchen kit;
	
	/**
	 * Reference to the bar
	 */
	private final Bar bar;
	
	
	
	/**
	 * @param chefState new state of chef to be set
	 */
	public void setChefState(int chefState)
	{
		this.chefState = chefState;
	}	
	
	
	/**
	 * 	@return chef state
	 */
	public int getChefState()
	{
		return chefState;
	}
	
	
	/**
	 * Chef Thread instantiation
	 * 
	 * @param name Name of the thread
	 * @param kit Reference to the kitchen
	 * @param bar Reference to the bar
	 */
	public Chef(String name, Kitchen kit, Bar bar) {
		super(name);
		this.chefState = ChefStates.WAITING_FOR_AN_ORDER;
		this.kit = kit;
		this.bar = bar;
	}

	
	
	
	/**
	 * 	Life cycle of the chef
	 */
	@Override
	public void run ()
	{
		boolean firstCourse = true;
		
		kit.watchTheNews();
		kit.startPreparation();
		do
		{
			if(!firstCourse)
				kit.continuePreparation();
			else
				firstCourse = false;
			
			kit.proceedPreparation();
			bar.alertWaiter();
			
			while(!kit.haveAllPortionsBeenDelivered())
				kit.haveNextPortionReady();
		}
		while(!kit.hasOrderBeenCompleted());
		
		kit.cleanUp();
	}
}