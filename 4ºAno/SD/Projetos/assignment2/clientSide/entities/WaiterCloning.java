package clientSide.entities;

/**
 *    Waiter cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public interface WaiterCloning {
	
	/**
	 * Set waiter state
	 * 	@param state new state of the waiter
	 */
	public void setWaiterState(int state);
	
	/**
	 * Get waiter state
	 * 	@return state of the waiter
	 */
	public int getWaiterState();
}
