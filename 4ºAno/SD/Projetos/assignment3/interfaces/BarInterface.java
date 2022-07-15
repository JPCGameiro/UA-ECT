package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type Bar.
 *
 *     It provides the functionality to access the Bar.
 */
public interface BarInterface extends Remote {
	
	/**
	 * Return id of the student whose request is being answered
	 * @return Id of the student whose request is being answered
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int getStudentBeingAnswered() throws RemoteException;
	

	
	/**
	 * Operation alert the waiter
	 * 
	 * It is called by the chef to alert the waiter that a portion was dished
	 * 	A request is putted in the queue (chef id will be N+1)
	 * @return chef state
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int alertWaiter() throws RemoteException;	
	
	
	
	/**
	 * Operation look Around
	 * 
	 * It is called by the waiter, he checks for pending service requests and if not waits for them
	 * 	@return Character that represents the service to be executed
	 * 		'c' : means a client has arrived therefore needs to be presented with the menu by the waiter
	 * 		'o' : means that the waiter will hear the order and deliver it to the chef
	 * 		'p' : means that a portion needs to be delivered by the waiter
	 * 		'b' : means that the bill needs to be prepared and presented by the waiter
	 * 		'g' : means that some student wants to leave and waiter needs to say goodbye 
	 *	@throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public char lookAround() throws RemoteException;
	
	
		
	/**
	 * Operation prepare the Bill
	 * 
	 * It is called the waiter to prepare the bill of the meal eaten by the students
	 * @return waiter state
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int prepareBill() throws RemoteException;
	
	
		
	/**
	 * Operation say Goodbye
	 * 
	 * It is called by the waiter to say goodbye to a student that's leaving the restaurant
	 * @return true if there are no more students at the restaurant, false otherwise
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public boolean sayGoodbye() throws RemoteException;
	
	
	
	/**
	 * Operation enter the restaurant
	 * It is called by the student to signal that he is entering the restaurant
	 * 	@param studentId id of the student
	 * 	@return state of the student
	 * 	@throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int enter(int studentId) throws RemoteException;
	
	
	
	/**
	 * Operation call the waiter
	 * 
	 * It is called by the first student to arrive the restaurant to call the waiter to describe the order
	 * @param studentId id of the student
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void callWaiter(int studentId) throws RemoteException;
	
	
	
	
	/**
	 * Operation signal the waiter
	 * 
	 * It is called by the last student to finish eating that next course can be brought 
	 * signal chef that he can put request in the queue and waiter that he proceed his execution to collect portions
	 * It is also used by last student to arrive to signal that he wishes to pay the bill
	 * @param studentId id of the student
	 * @param studentState state of the student 
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void signalWaiter(int studentId, int studentState) throws RemoteException;
	
	
		
	/**
	 * Operation exit the restaurant
	 * 
	 * It is called by a student when he leaves the restaurant
	 * @param studentId id of the student
	 * @return state of the student
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int exit(int studentId) throws RemoteException;
	
	
	
	/**
	 * Operation bar server shutdown
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void shutdown() throws RemoteException;
}
