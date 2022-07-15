package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type Table.
 *
 *     It provides the functionality to access the Table.
 */
public interface TableInterface extends Remote{
	
	/**
     * Obtain id of the first student to arrive
     * @return id of the first student to arrive at the restaurant
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int getFirstToArrive() throws RemoteException;
    
    /**
     * Obtain id of the last student to arrive
     * @return id of the last student to finish eating a meal
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int getLastToEat() throws RemoteException;
    
    /**
     * Set id of the first student to arrive
     * @param firstToArrive id of the first student to arrive
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void setFirstToArrive(int firstToArrive) throws RemoteException;
    
    /**
     * Set id of the last student to arrive
     * @param lastToArrive if of the last student to arrive to the restaurant
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void setLastToArrive(int lastToArrive) throws RemoteException;
    

    
    /**
     * Operation salute the client
     * 
     * It is called by the waiter when a student enters the restaurant and needs to be saluted
     * Waiter waits for the student to take a seat (if he hasn't done it yet)
     * Waiter waits for student to finish reading the menu
     * @param studentIdBeingAnswered id of the student being answered
     * @return state of the waiter
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int saluteClient(int studentIdBeingAnswered) throws RemoteException;
    
    
    
    /**
     * Operation return to the bar
     * 
     * It is called by the waiter to return to the bar to the appraising situation state
     * @return state of the waiter
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int returnBar() throws RemoteException;
    
    
    
    /**
     * Operation get the pad
     * 
     * It is called by the waiter when an order is going to be described by the first student to arrive
     * Waiter Blocks waiting for student to describe him the order
     * @return state of the waiter
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int getThePad() throws RemoteException;
    
    
    
    /**
     * Operation have all clients been served
     * 
     * Called by the waiter to check if all clients have been served or not
     * @return true if all clients have been served, false otherwise
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public boolean haveAllClientsBeenServed() throws RemoteException;
    
    
    
    /**
     * Operation deliver portion
     * 
     * Called by the waiter, to deliver a portion
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void deliverPortion() throws RemoteException;
    
    
    
    /**
     * Operation present the bill
     * 
     * Called by the waiter to present the bill to the last student to arrive
     * @return waiter state
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int presentBill() throws RemoteException;
    
    
    /**
     * Operation siting at the table
     * 
     * Student comes in the table and sits (blocks) waiting for waiter to bring him the menu
     * Called by the student (inside enter method in the bar)
     * @param studentId id of the student
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void seatAtTable(int studentId) throws RemoteException;
    
    
    
    /**
     * Operation read the menu
     * 
     * Called by the student to read a menu, wakes up waiter to signal that he already read the menu
     * 	@param studentId id of the student
     * 	@return student state
     * 	@throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int readMenu(int studentId) throws RemoteException;
    
    
    
    /**
     * Operation prepare the order
     * 
     * Called by the student to begin the preparation of the order (options of his companions) 
     * @return student state
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int prepareOrder() throws RemoteException;   

    
    
    /**
     * Operation everybody has chosen
     * 
     * Called by the first student to arrive to check if all his companions have choose or not
     * Blocks if not waiting to be waker up be a companion to give him his preference
     * @return true if everybody choose their course choice, false otherwise
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public boolean everybodyHasChosen() throws RemoteException;
    
    
    
    /**
     * Operation add up ones choices
     * 
     * Called by the first student to arrive to add up a companions choice to the order
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void addUpOnesChoices() throws RemoteException;
    
    
    
    /**
     * Operation describe the order
     * 
     * Called by the first student to arrive to describe the order to the waiter
     * Blocks waiting for waiter to come with pad
     * Wake waiter up so he can take the order
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public void describeOrder() throws RemoteException;
    
    
    
    /**
     * Operation join the talk
     * 
     * Called by the first student to arrive so he can join his companions 
     * while waiting for the courses to be delivered
     * @return student state
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int joinTalk() throws RemoteException;    
    
    
    
    /**
     * Operation inform companion
     * 
     * Called by a student to inform the first student to arrive about their preferences 
     * Blocks if someone else is informing at the same time
     * @param studentId id of the student
     * @return state of the student
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int informCompanion(int studentId) throws RemoteException;
    
    
    
    /**
     * Operation start eating
     * 
     * Called by the student to start eating the meal (During random time)
     * @param studentId id of the student
     * @return state of the student
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */    
    public int startEating(int studentId) throws RemoteException;



	/**
     * Operation end eating
     * 
     * Called by the student to signal that he has finished eating his meal
     * @param studentId id of the student
     * @return state of the student
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public int endEating(int studentId) throws RemoteException;    
    
    
    
    /**
     * Operation has everybody finished eating
     * 
     * Called by the student to wait for his companions to finish eating
     * @param studentId id of the student
     * @return true if everybody has finished false otherwise
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public boolean hasEverybodyFinishedEating(int studentId) throws RemoteException;   
    
    
    
    /**
     * Operation honour the bill
     * 
     * Called by the student to pay the bill
     * Student blocks waiting for bill to be presented and signals waiter when it's time to pay it
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails	
     */
    public void honourBill() throws RemoteException;    
    
    
    
    /**
     * 	Operation have all courses been eaten
     * 
     * 	Called by the student to check if there are more courses to be eaten
     * 	Student blocks waiting for the course to be served
     * 	@return true if all courses have been eaten, false otherwise
     *	@throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public boolean haveAllCoursesBeenEaten() throws RemoteException;
    
    
    
    /**
     * Operation should have arrived earlier
     * 
     * Called by the student to check which one was last to arrive
     * @param studentId id of the student
     * @return True if current student was the last to arrive, false otherwise and student state
     * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
     */
    public ReturnBoolean shouldHaveArrivedEarlier(int studentId) throws RemoteException;
    
    
    
	/**
	 * 	Operation Table server shutdown
	 *	@throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void shutdown() throws RemoteException;
}
