package clientSide.entities;

/**
 *    Chef cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public interface ChefCloning {
	
	/**
	 * Set chef id
	 * @param state id of the chef
	 */
	public void setChefState(int state);
	
	/**
	 * Get chef state
	 * @return state of the chef
	 */
	public int getChefState();
	
	
}
