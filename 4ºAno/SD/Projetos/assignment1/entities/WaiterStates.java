package entities;

/**
 *    Definition of the internal states of the waiter during his life cycle.
 */

public class WaiterStates {
	
	/**
	 * waiter checks for pending requests and blocks if there are none
	 */
	public static final int APRAISING_SITUATION = 0;
	
	/**
	 * waiter presents the menu and wait for student to read it
	 */
	public static final int PRESENTING_THE_MENU = 1;
	
	/**
	 * waiter waits for the first student to describe the order
	 */
	public static final int TAKING_THE_ORDER = 2;
	
	/**
	 * waiter gives order to chef and waits for chef to start preparing it
	 */
	public static final int PLACING_ODER = 3;
	
	/**
	 * waiter waits for until all portions are delivered
	 */
	public static final int WAITING_FOR_PORTION = 4;
	
	/**
	 * waiter prepares the bill after last student to arrive signals the waiter
	 */
	public static final int PROCESSING_THE_BILL = 5;
	
	/**
	 * waiter wait for last student to arrive to pay
	 */
	public static final int RECEIVING_PAYMENT = 6;
	
	/**
	 *   It can not be instantiated.
	 */
	private WaiterStates ()
	{ }
}
