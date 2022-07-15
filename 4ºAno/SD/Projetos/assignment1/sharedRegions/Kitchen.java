package sharedRegions;

import entities.*;
import main.*;

/**
 * 	Kitchen
 * 
 * 	It is responsible for keeping track of portions prepared and delivered
 *  Is implemented as an implicit monitor.
 *  All public methods are executed in mutual exclusion.
 *	Synchronisation points include:
 *		Chef has to wait for the note that describes the order given by the waiter
 *		Chef has to wait for waiter to collect portions
 *		Waiter has to wait for chef to start preparing the order
 *		Waiter has to wait for portions from the chef
 *
 */

public class Kitchen
{
	/**
	 *	Number of portions ready
	 */
	private int numberOfPortionsReady;

	/**
	 *	Number of portions delivered in at each course
	 */
	private int numberOfPortionsDelivered;

	/**
	 *	Number of courses delivered
	 */
	private int numberOfCoursesDelivered;
	
	/**
	 * Number of portions prepared by the chef
	 */
	private int numberOfPortionsPrepared;
	
	/**
     * Reference to the General Repository.
     */
    private final GeneralRepos repos;

	
    
    /**
     * Kitchen instantiation
     * 
     * @param repos reference to general repository
     */
	public Kitchen(GeneralRepos repos)
	{
		this.numberOfPortionsReady = 0;
		this.numberOfPortionsDelivered = 0;
		this.numberOfCoursesDelivered = 0;
		this.numberOfPortionsPrepared = 0;
		this.repos = repos;
	}

	
	
	/**
	 * 	Operation watch the news
	 * 
	 * 	It is called by the chef, he waits for waiter to notify him of the order
	 */
	public synchronized void watchTheNews()
	{
		//Set chef state
		((Chef) Thread.currentThread()).setChefState(ChefStates.WAITING_FOR_AN_ORDER);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
		
		//Block waiting for waiter to notify of the order
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 	Operation start presentation
	 * 
	 * 	It is called by the chef after waiter has notified him of the order to be prepared 
	 * 	to signal that preparation of the course has started
	 */
	public synchronized void startPreparation()
	{
		//Update new Chef State
		repos.setnCourses(numberOfCoursesDelivered+1);
		((Chef) Thread.currentThread()).setChefState(ChefStates.PREPARING_THE_COURSE);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
		
		//Notify Waiter that the preparation of the order has started
		notifyAll();
	}


	
	
	/**
	 * 	Operation proceed presentation
	 * 
	 * 	It is called by the chef when a portion needs to be prepared
	 */
	public synchronized void proceedPreparation()
	{
		//Update new Chef state
		numberOfPortionsPrepared++;
		repos.setnPortions(numberOfPortionsPrepared);
		((Chef) Thread.currentThread()).setChefState(ChefStates.DISHING_THE_PORTIONS);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
		
		//Update numberOfPortionsReady
		numberOfPortionsReady++;
	}

	
	
	
	
	
	/**
	 * 	Operation have all portions been delivered
	 * 
	 * 	It is called by the chef when he finishes a portion and checks if another one needs to be prepared or not
	 * 	It is also here were the chef blocks waiting for waiter do deliver the current portion
	 * 	@return true if all portions have been delivered, false otherwise
	 */
	public synchronized boolean haveAllPortionsBeenDelivered()
	{
		//Wait for waiter to collect the portion
		while( numberOfPortionsReady != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Check if all portions of the course have been delivered or not
		if(numberOfPortionsDelivered == ExecuteConst.N) 
		{
			//If all portions have been delivered means that a course was completed
			numberOfCoursesDelivered++;
			return true;
		}
		return false;

	}

	
	
	
	/**
	 *	Operation has order been completed
	 * 
	 * 	It is called by the chef when he finishes preparing all courses to check if order has been completed or not
	 * 	@return true if all courses have been completed, false or not
	 */
	public synchronized boolean hasOrderBeenCompleted()
	{
		//Check if all courses have been delivered
		if (numberOfCoursesDelivered == ExecuteConst.M)
			return true;
		return false;
	}

	
	
	
	/**
	 * 	Operation continue preparation
	 * 
	 * 	It is called by the chef when all portions have been delivered, but the course has not been completed yet
	 */
	public synchronized void continuePreparation()
	{
		//Update chefs state
		repos.setnCourses(numberOfCoursesDelivered+1);
		numberOfPortionsPrepared = 0;
		repos.setnPortions(numberOfPortionsPrepared);
		
		((Chef) Thread.currentThread()).setChefState(ChefStates.PREPARING_THE_COURSE);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
	}
	
	
	
	
	/**
	 * Operation have next portion ready
	 * 
	 * It is called by the chef after a portion has been delivered and another one needs to be prepared
	 */
	public synchronized void haveNextPortionReady()
	{	
		//Update chefs state
		numberOfPortionsPrepared++;		
		repos.setnPortions(numberOfPortionsPrepared);
		((Chef) Thread.currentThread()).setChefState(ChefStates.DISHING_THE_PORTIONS);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
		
		//Update numberOfPortionsReady
		numberOfPortionsReady++;
		
		//Update chefs state
		((Chef) Thread.currentThread()).setChefState(ChefStates.DELIVERING_THE_PORTIONS);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
		
		//Notify Waiter that there is a portion waiting to be delivered
		notifyAll();
	}
	
	
	
	
	/**
	 * Operation clean up
	 * 
	 * It is called by the chef when he finishes the order, to close service
	 */
	public synchronized void cleanUp()
	{	
		//Update chefs state to terminate life cycle
		((Chef) Thread.currentThread()).setChefState(ChefStates.CLOSING_SERVICE);
		repos.setChefState(((Chef) Thread.currentThread()).getChefState());
	}
	
	
	
	
	
	/**
	 * Operation hand note to chef
	 * 
	 * Called by the waiter to wake chef up chef to give him the description of the order
	 */	
	public synchronized void handNoteToChef()
	{		
		//Update waiter state
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PLACING_ODER);
		repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
		
		//Notify chef that he can start the preparation of the order
		notifyAll();
		
		//Block waiting for chef to start the preparation of the order
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/**
	 * Operation return to the bar
	 * 
	 * Called by the waiter when he is the kitchen and returns to the bar
	 */
	public synchronized void returnToBar()
	{
		//Update waiter state
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APRAISING_SITUATION);
		repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
	}
	
	
	
	
	
	/**
	 * Operation collect portion
	 * 
	 * Called by the waiter when there is a portion to be delivered. Collect and signal chef that the portion was delivered
	 */
	public synchronized void collectPortion()
	{
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.WAITING_FOR_PORTION);
		repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
		
		//If there are no portions to deliver waiter must block
		while (numberOfPortionsReady == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Update number of portions ready and delivered
		numberOfPortionsReady--;
		numberOfPortionsDelivered++;
		
		//If a new course is being delivered then numberOfPortionsDelivered must be "reseted"
		if(numberOfPortionsDelivered > ExecuteConst.N)
			numberOfPortionsDelivered = 1;

		
		//Signal chef that portion was delivered
		notifyAll();
		
	}

}
