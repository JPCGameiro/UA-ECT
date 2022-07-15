package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type Kitchen.
 *
 *     It provides the functionality to access the Kitchen.
 */
public interface KitchenInterface extends Remote{
	
	/**
	 * 	Operation watch the news
	 * 
	 * 	It is called by the chef, he waits for waiter to notify him of the order
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int watchTheNews() throws RemoteException;
	
	

	/**
	 * 	Operation start presentation
	 * 
	 * 	It is called by the chef after waiter has notified him of the order to be prepared 
	 * 	to signal that preparation of the course has started
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int startPreparation()	throws RemoteException;


	
	
	/**
	 * 	Operation proceed presentation
	 * 
	 * 	It is called by the chef when a portion needs to be prepared
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int proceedPreparation() throws RemoteException;

	
	
	/**
	 * 	Operation have all portions been delivered
	 * 
	 * 	It is called by the chef when he finishes a portion and checks if another one needs to be prepared or not
	 * 	It is also here were the chef blocks waiting for waiter do deliver the current portion
	 * 	@return true if all portions have been delivered, false otherwise
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public boolean haveAllPortionsBeenDelivered() throws RemoteException;

	
	
	/**
	 *	Operation has order been completed
	 * 
	 * 	It is called by the chef when he finishes preparing all courses to check if order has been completed or not
	 * 	@return true if all courses have been completed, false or not
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public boolean hasOrderBeenCompleted() throws RemoteException;
	
	
	
	/**
	 * 	Operation continue preparation
	 * 
	 * 	It is called by the chef when all portions have been delivered, but the course has not been completed yet
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int continuePreparation() throws RemoteException;
	
	
	
	/**
	 * Operation have next portion ready
	 * 
	 * It is called by the chef after a portion has been delivered and another one needs to be prepared
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int haveNextPortionReady() throws RemoteException;
	
	
	
	
	/**
	 * Operation clean up
	 * 
	 * It is called by the chef when he finishes the order, to close service
	 *  @return chef state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int cleanUp() throws RemoteException;
	
	
	
	
	/**
	 * Operation hand note to chef
	 * 
	 * Called by the waiter to wake chef up chef to give him the description of the order
	 *  @return waiter state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */	
	public int handNoteToChef() throws RemoteException;	
	
	
	
	/**
	 * Operation return to the bar
	 * 
	 * Called by the waiter when he is the kitchen and returns to the bar
	 *  @return waiter state
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int returnToBar() throws RemoteException;
	
	
	
	/**
	 * Operation collect portion
	 * 
	 * Called by the waiter when there is a portion to be delivered. Collect and signal chef that the portion was delivered
	 *  @return state of the waiter
	 *  @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int collectPortion() throws RemoteException;


	/**
	 * Operation kitchen server shutdown
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void shutdown() throws RemoteException;

}
