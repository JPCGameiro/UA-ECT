package clientSide.entities;

import interfaces.*;
import java.rmi.RemoteException;
import genclass.GenericIO;

/**
 *    Chef thread.
 *
 *      It simulates the chef life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on remote calls under Java RMI.
 */
public class Chef extends Thread{

	/**
	 *	Chef state 
	 */
	private int chefState;
	
	/**
	 * Reference to the stub of the kitchen
	 */
	private final KitchenInterface kitStub;
	
	/**
	 * Reference to the stub of the bar
	 */
	private final BarInterface barStub;
	
	
	
	/**
	 * Set a new chef state
	 * @param chefState new state to be set
	 */
	public void setChefState(int chefState) { this.chefState = chefState; }	
	
	/**
	 * Get the chef's state
	 * @return chef state
	 */
	public int getChefState() { return chefState; }
	
	
	
	/**
	 * Instantiation of a Chef thread
	 * 	@param name thread name
	 * 	@param kitStub reference to the kitchen interface
	 * 	@param barStub reference to the bar interface
	 */
	public Chef(String name, KitchenInterface kitStub, BarInterface barStub) {
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
		
		watchTheNews();
		startPreparation();
		do
		{
			if(!firstCourse)
				continuePreparation();
			else
				firstCourse = false;
			
			proceedPreparation();
			alertWaiter();
			
			while(!haveAllPortionsBeenDelivered())
				haveNextPortionReady();
		}
		while(!hasOrderBeenCompleted());
		
		cleanUp();
	}
	
	
	
	
	/**
	 * 	Operation watch the news
	 * 	Remote operation.
	 * 	It is called by the chef, he waits for waiter to notify him of the order
	 */
	private void watchTheNews()
	{
		try 
		{ chefState = kitStub.watchTheNews();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on watchTheNews: " +e.getMessage());
			System.exit(1);
		}    		
	}
	
	
	
	/**
	 * 	Operation start presentation
	 * 	Remote operation.
	 * 	It is called by the chef after waiter has notified him of the order to be prepared 
	 * 	to signal that preparation of the course has started
	 */
	private void startPreparation()
	{
		try 
		{ chefState = kitStub.startPreparation();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on startPreparation: " +e.getMessage());
			System.exit(1);
		}    		
	}
	
	
	
	/**
	 * 	Operation continue preparation
	 *  Remote operation.
	 * 	It is called by the chef when all portions have been delivered, but the course has not been completed yet
	 */
	private void continuePreparation()
	{
		try 
		{ chefState = kitStub.continuePreparation();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on continuePreparation: " +e.getMessage());
			System.exit(1);
		} 		
	}
	
	
	
	/**
	 * 	Operation proceed presentation
	 *  Remote operation.
	 * 	It is called by the chef when a portion needs to be prepared
	 */
	private void proceedPreparation()
	{
		try 
		{ chefState = kitStub.proceedPreparation();		
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on proceedPreparation: " +e.getMessage());
			System.exit(1);
		} 			
	}
	
	
	
	/**
	 * Operation alert the waiter
	 * Remote operation.
	 * It is called by the chef to alert the waiter that a portion was dished
	 * 	A request is putted in the queue (chef id will be N+1)
	 */
	private void alertWaiter()
	{
		try 
		{ chefState = barStub.alertWaiter();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on alertWaiter: " +e.getMessage());
			System.exit(1);
		} 		
	}
	
	
	
	/**
	 * 	Operation have all portions been delivered
	 *  Remote operation.
	 * 	It is called by the chef when he finishes a portion and checks if another one needs to be prepared or not
	 * 	It is also here were the chef blocks waiting for waiter do deliver the current portion
	 * 	@return true if all portions have been delivered, false otherwise
	 */
	private boolean haveAllPortionsBeenDelivered()
	{
		boolean bValue = false;
		try 
		{ bValue = kitStub.haveAllPortionsBeenDelivered();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on haveAllPortionsBeenDelivered: " +e.getMessage());
			System.exit(1);
		}
		return bValue;
	}
	
	
	
	/**
	 * Operation have next portion ready
	 * Remote operation.
	 * It is called by the chef after a portion has been delivered and another one needs to be prepared
	 */
	private void haveNextPortionReady()
	{
		try 
		{ chefState = kitStub.haveNextPortionReady();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on haveNextPortionReady: " +e.getMessage());
			System.exit(1);
		} 		
	}
	
	
	
	/**
	 *	Operation has order been completed
	 *  Remote operation.
	 * 	It is called by the chef when he finishes preparing all courses to check if order has been completed or not
	 * 	@return true if all courses have been completed, false or not
	 */
	private boolean hasOrderBeenCompleted()
	{
		boolean bValue = false;
		try 
		{ bValue = kitStub.hasOrderBeenCompleted();
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception onhasOrderBeenCompleted: " +e.getMessage());
			System.exit(1);
		}
		return bValue;		
	}
	
	
	
	/**
	 * Operation clean up
	 * Remote operation.
	 * It is called by the chef when he finishes the order, to close service
	 */
	private void cleanUp()
	{
		try 
		{ chefState = kitStub.cleanUp();	
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Chef remote exception on cleanUp: " +e.getMessage());
			System.exit(1);
		} 		
	}
}
