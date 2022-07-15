package serverSide.objects;

import java.rmi.RemoteException;
import clientSide.entities.*;
import interfaces.GeneralReposInterface;
import interfaces.KitchenInterface;
import serverSide.main.ExecuteConst;
import serverSide.main.ServerRestaurantKitchen;

/**
 * 	Kitchen
 * 
 * 	It is responsible for keeping track of portions prepared and delivered
 *  Is implemented as an implicit monitor.
 *  All public methods are executed in mutual exclusion.
 *	Synchronization points include:
 *		Chef has to wait for the note that describes the order given by the waiter
 *		Chef has to wait for waiter to collect portions
 *		Waiter has to wait for chef to start preparing the order
 *		Waiter has to wait for portions from the chef
 *	Implementation of a client-server model of type 2 (server replication).
 *  Communication is based on Java RMI.	
 */
public class Kitchen implements KitchenInterface
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
	private final GeneralReposInterface reposStub;

	/**
	 * Number of entities that requested shutdown
	 */
	private int nEntities; 


	/**
	 * Kitchen Instantiation.
	 * 
	 * @param reposStub Reference to the stub of the general repository
	 */
	public Kitchen(GeneralReposInterface reposStub)
	{
		this.numberOfPortionsReady = 0;
		this.numberOfPortionsDelivered = 0;
		this.numberOfCoursesDelivered = 0;
		this.numberOfPortionsPrepared = 0;
		this.reposStub = reposStub;
	}


	/**
	 * 	Operation watch the news
	 * 
	 * 	It is called by the chef, he waits for waiter to notify him of the order
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int watchTheNews() throws RemoteException
	{
		//Set chef state
		reposStub.setChefState(ChefStates.WAITING_FOR_AN_ORDER);

		//Block waiting for waiter to notify of the order
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ChefStates.WAITING_FOR_AN_ORDER;
	}


	/**
	 * 	Operation start presentation
	 * 
	 * 	It is called by the chef after waiter has notified him of the order to be prepared 
	 * 	to signal that preparation of the course has started
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int startPreparation() throws RemoteException
	{
		//Update new Chef State
		reposStub.setnCourses(numberOfCoursesDelivered+1, ChefStates.PREPARING_THE_COURSE);
		//((Chef) Thread.currentThread()).setChefState(ChefStates.PREPARING_THE_COURSE);
		//reposStub.setChefState(ChefStates.PREPARING_THE_COURSE);

		//Notify Waiter that the preparation of the order has started
		notifyAll();

		return ChefStates.PREPARING_THE_COURSE;
	}


	/**
	 * 	Operation proceed presentation
	 * 
	 * 	It is called by the chef when a portion needs to be prepared
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */	
	@Override
	public synchronized int proceedPreparation() throws RemoteException
	{
		//Update new Chef state
		numberOfPortionsPrepared++;
		reposStub.setnPortions(numberOfPortionsPrepared, ChefStates.DISHING_THE_PORTIONS);
		//reposStub.setChefState(ChefStates.DISHING_THE_PORTIONS);

		//Update numberOfPortionsReady
		numberOfPortionsReady++;

		return ChefStates.DISHING_THE_PORTIONS;
	}


	/**
	 * 	Operation have all portions been delivered
	 * 
	 * 	It is called by the chef when he finishes a portion and checks if another one needs to be prepared or not
	 * 	It is also here were the chef blocks waiting for waiter do deliver the current portion
	 * 	@return true if all portions have been delivered, false otherwise
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized boolean haveAllPortionsBeenDelivered() throws RemoteException
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
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized boolean hasOrderBeenCompleted() throws RemoteException
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
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int continuePreparation() throws RemoteException
	{
		//Update chefs state
		//reposStub.setnCourses(numberOfCoursesDelivered+1);
		numberOfPortionsPrepared = 0;
		reposStub.setnPortionsAndCourses(numberOfPortionsPrepared, numberOfCoursesDelivered+1,ChefStates.PREPARING_THE_COURSE);
		
		//reposStub.setChefState(ChefStates.PREPARING_THE_COURSE);

		return ChefStates.PREPARING_THE_COURSE;
	}


	/**
	 * Operation have next portion ready
	 * 
	 * It is called by the chef after a portion has been delivered and another one needs to be prepared
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int haveNextPortionReady() throws RemoteException
	{
		//Update chefs state
		numberOfPortionsPrepared++;		
		reposStub.setnPortions(numberOfPortionsPrepared, ChefStates.DISHING_THE_PORTIONS);
		//reposStub.setChefState(ChefStates.DISHING_THE_PORTIONS);

		//Update numberOfPortionsReady
		numberOfPortionsReady++;

		//Update chefs state
		reposStub.setChefState(ChefStates.DELIVERING_THE_PORTIONS);

		//Notify Waiter that there is a portion waiting to be delivered
		notifyAll();
		return ChefStates.DELIVERING_THE_PORTIONS;
	}


	/**
	 * Operation clean up
	 * 
	 * It is called by the chef when he finishes the order, to close service
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int cleanUp() throws RemoteException
	{
		//Update chefs state to terminate life cycle
		reposStub.setChefState(ChefStates.CLOSING_SERVICE);
		return ChefStates.CLOSING_SERVICE;
	}


	/**
	 * Operation hand note to chef
	 * 
	 * Called by the waiter to wake chef up chef to give him the description of the order
	 *  @returns waiter state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */	
	@Override
	public synchronized int handNoteToChef() throws RemoteException
	{
		//Update waiter state
		reposStub.setWaiterState(WaiterStates.PLACING_ODER);

		//Notify chef that he can start the preparation of the order
		notifyAll();

		//Block waiting for chef to start the preparation of the order
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return WaiterStates.PLACING_ODER;
	}


	/**
	 * Operation return to the bar
	 * 
	 * Called by the waiter when he is the kitchen and returns to the bar
	 *  @return waiter state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int returnToBar() throws RemoteException
	{
		//Update waiter state
		reposStub.setWaiterState(WaiterStates.APRAISING_SITUATION);
		return WaiterStates.APRAISING_SITUATION;
	}


	/**
	 * Operation collect portion
	 * 
	 * Called by the waiter when there is a portion to be delivered. Collect and signal chef that the portion was delivered
	 *  @return state of the waiter
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int collectPortion() throws RemoteException
	{
		reposStub.setWaiterState(WaiterStates.WAITING_FOR_PORTION);

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

		return WaiterStates.WAITING_FOR_PORTION;
	}


	/**
	 * Operation kitchen server shutdown
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void shutdown() throws RemoteException
	{
		nEntities += 1;
		if(nEntities >= ExecuteConst.E)
			ServerRestaurantKitchen.shutdown();
		notifyAll();
	}
}
