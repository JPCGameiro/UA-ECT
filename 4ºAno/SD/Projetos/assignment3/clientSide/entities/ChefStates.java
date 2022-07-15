package clientSide.entities;

/**
 *	Definition of the internal states of the Chef during his life cycle.
 */
public class ChefStates {

	/**
	 * 	blocking state while waiting for an order
	 */
	public static final int WAITING_FOR_AN_ORDER = 0;

	/**
	 * 	transition state, chef is preparing a course
	 */
	public static final int PREPARING_THE_COURSE = 1;

	/**
	 * 	chef is dishing a portion
	 */
	public static final int DISHING_THE_PORTIONS = 2;

	/**
	 * 	blocking state chef wait for waiter to collect the portion
	 */
	public static final int DELIVERING_THE_PORTIONS = 3;

	/**
	 * 	final state lifecycle termination
	 */
	public static final int CLOSING_SERVICE = 4;
	
	/**
	 *   It can not be instantiated.
	 */
	private ChefStates ()
	{ }
}