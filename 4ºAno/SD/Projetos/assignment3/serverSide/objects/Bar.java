package serverSide.objects;

import java.rmi.RemoteException;

import clientSide.entities.*;
import commInfra.MemException;
import commInfra.MemFIFO;
import commInfra.Request;
import interfaces.*;
import serverSide.main.*;

/**
 * 
 * Bar
 *
 *	It is responsible for keeping track of the several requests that must be full filled by the waiter
 *	Implemented as an implicit monitor.
 *	Public methods executed in mutual exclusion
 *	Synchronization points include:
 *		Waiter waits for pending requests if there are none
 *		When a student has to wait for the waiter to say goodbye to him so he can leave the restaurant
 *		Chef must wait for everybody to eat before alerting the waiter
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */

public class Bar implements BarInterface
{
	/**
	 *	Used to control number of students present in the restaurant
	 */	
	private int numberOfStudentsAtRestaurant;

	/**
	 *  Used to control number of pending requests to be answered by the waiter
	 */
	private int numberOfPendingRequests;

	/**
	 * Boolean variable used to store if a course was finished or not
	 */
	private boolean courseFinished;

	/**
	 * Queue of pending Requests
	 */
	private MemFIFO<Request> pendingServiceRequestQueue;

	/**
	 *  State of the students.
	 */
	private final int [] studentState;

	/**
	 * Reference to the stub of the general repository
	 */
	private final GeneralReposInterface reposStub;

	/**
	 * Auxiliary variable to keep track of the id of the student whose request is being answered by waiter
	 */
	private int studentBeingAnswered;

	/**
	 * Array of booleans to keep track of the students which the waiter has already said goodbye
	 */
	private boolean[] studentsGreeted;

	/**
	 * Reference to the stub of the table
	 */
	private final TableInterface tabStub;

	/**
	 * Number of entity groups requesting the shutdown
	 */
	private int nEntities;


	/**
	 * Bar instantiation
	 *  
	 * @param reposStub reference to the stub of the general repository
	 * @param tabStub reference to the stub of the table
	 */
	public Bar(GeneralReposInterface reposStub, TableInterface tabStub)
	{
		//Initialization of the queue of pending requests
		try {
			pendingServiceRequestQueue = new MemFIFO<> (new Request [ExecuteConst.N * ExecuteConst.M]);
		} catch (MemException e) {
			pendingServiceRequestQueue = null;
			System.exit (1);
		}
		this.courseFinished = true;
		this.studentBeingAnswered = -1;
		this.reposStub = reposStub;
		this.tabStub = tabStub;
		this.nEntities = 0;
		this.studentsGreeted = new boolean[ExecuteConst.N];
		this.studentState = new int[ExecuteConst.N];
		for(int i = 0 ;i < ExecuteConst.N; i++) {
			studentsGreeted[i] = false;
			studentState[i] = StudentStates.GOING_TO_THE_RESTAURANT;
		}
	}


	/**
	 * Return id of the student whose request is being answered
	 * @return Id of the student whose request is being answered
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public int getStudentBeingAnswered() throws RemoteException { return studentBeingAnswered; }


	
	/**
	 * Operation alert the waiter
	 * 
	 * It is called by the chef to alert the waiter that a portion was dished
	 * 	A request is putted in the queue (chef id will be N+1)
	 * @return chef state
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int alertWaiter() throws RemoteException
	{
		//Chef must not alert Waiter while previous course is not finished by the students
		while(!courseFinished)
		{
			try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		Request r = new Request(ExecuteConst.N+1,'p');

		//Add a new service request to queue of pending requests (portion to be collected)
		try {
			pendingServiceRequestQueue.write(r);
		} catch (MemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Update number of pending requests and set courseFinished to false
		numberOfPendingRequests++;
		courseFinished = false;

		//Update chefs state
		reposStub.setChefState(ChefStates.DELIVERING_THE_PORTIONS);

		//Signal waiter that there is a pending request
		notifyAll();

		return ChefStates.DELIVERING_THE_PORTIONS;
	}



	/**
	 * Operation prepare the Bill
	 * 
	 * It is called the waiter to prepare the bill of the meal eaten by the students
	 * @return waiter state
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int prepareBill() throws RemoteException
	{
		//Update Waiter state
		reposStub.setWaiterState(WaiterStates.PROCESSING_THE_BILL);

		return WaiterStates.PROCESSING_THE_BILL;
	}


	
	/**
	 * Operation enter the restaurant
	 * It is called by the student to signal that he is entering the restaurant
	 * 	@param studentId id of the student
	 * 	@return state of the student
	 * 	@throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public int enter(int studentId) throws RemoteException
	{
		synchronized(this)
		{
			//Update student state
			studentState[studentId] = StudentStates.GOING_TO_THE_RESTAURANT;
			reposStub.updateStudentState(studentId, studentState[studentId], true);
			
			numberOfStudentsAtRestaurant++;

			//Register first and last to arrive
			if(numberOfStudentsAtRestaurant == 1)
				tabStub.setFirstToArrive(studentId);
			else if (numberOfStudentsAtRestaurant == ExecuteConst.N)
				tabStub.setLastToArrive(studentId);

			//Add a new pending requests to the queue
			try {
				pendingServiceRequestQueue.write(new Request(studentId, 'c'));
			} catch (MemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Update number of pending requests
			numberOfPendingRequests++;

			//Update student state
			studentState[studentId] = StudentStates.TAKING_A_SEAT_AT_THE_TABLE;
			reposStub.updateStudentState(studentId, studentState[studentId], true);
			//register seat at the general repos
			reposStub.updateSeatsAtTable(numberOfStudentsAtRestaurant-1, studentId);

			//Signal waiter that there is a pending request
			notifyAll();
		}

		//Seat student at table and block it
		tabStub.seatAtTable(studentId);

		return studentState[studentId];
	}


	
	/**
	 * Operation call the waiter
	 * 
	 * It is called by the first student to arrive the restaurant to call the waiter to describe the order
	 * @param studentId id of the student
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void callWaiter(int studentId) throws RemoteException
	{
		Request r = new Request(studentId,'o');

		//Add a new service request to queue of pending requests (portion to be collected)
		try {
			pendingServiceRequestQueue.write(r);
		} catch (MemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Update number of pending requests
		numberOfPendingRequests++;	

		//Signal waiter that there is a pending request
		notifyAll();
	}


	
	
	/**
	 * Operation signal the waiter
	 * 
	 * It is called by the last student to finish eating that next course can be brought 
	 * signal chef that he can put request in the queue and waiter that he proceed his execution to collect portions
	 * It is also used by last student to arrive to signal that he wishes to pay the bill
	 * @param studentId id of the student
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void signalWaiter(int studentId, int stuState) throws RemoteException
	{
		studentState[studentId] = stuState;

		if(studentState[studentId] == StudentStates.PAYING_THE_BILL)
		{		
			//Add a new pending requests to the queue (Bill needs to be prepared so it can be payed by the student)
			try {
				pendingServiceRequestQueue.write(new Request(studentId, 'b'));
			} catch (MemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Update number of pending requests
			numberOfPendingRequests++;	

			//Signal waiter that there is a pending request
			notifyAll();
		}
		else
		{
			courseFinished = true;
			// Wake chef up because he is waiting to tell waiter to collect portion
			// and waiter so he can collect a new portion
			notifyAll();
		}

	}


	
	/**
	 * Operation exit the restaurant
	 * 
	 * It is called by a student when he leaves the restaurant
	 * @param studentId id of the student
	 * @return state of the student
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized int exit(int studentId) throws RemoteException
	{
		Request r = new Request(studentId,'g');

		//Add a new service request to queue of pending requests (Goodbye needs to be said to a student)
		try {
			pendingServiceRequestQueue.write(r);
		} catch (MemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Update number of pending requests
		numberOfPendingRequests++;
		//Update student state
		studentState[studentId] = StudentStates.GOING_HOME;
		reposStub.updateStudentState(studentId, studentState[studentId]);

		//notify waiter that there is a pending request
		notifyAll();

		//Block until waiter greets the student goodbye
		while(studentsGreeted[studentId] == false) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return studentState[studentId];
	}

	
	
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
	@Override
	public synchronized char lookAround() throws RemoteException
	{
		Request r;

		//While there are no pending request, waiter blocks
		while(numberOfPendingRequests == 0)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try 
		{
			//If there is a pending request take it of the queue of pending requests
			r = pendingServiceRequestQueue.read();
			//Update number of pending requests
			numberOfPendingRequests--;
		} catch (MemException e) {	
			e.printStackTrace();
			return 0;
		}		
		//Register student id in studentBeingAnswered
		studentBeingAnswered = r.id;

		return r.type;
	}


	
	/**
	 * Operation say Goodbye
	 * 
	 * It is called by the waiter to say goodbye to a student that's leaving the restaurant
	 * @return true if there are no more students at the restaurant, false otherwise
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized boolean sayGoodbye() throws RemoteException
	{
		//Student was greeted
		studentsGreeted[studentBeingAnswered] = true;
		//Wake up student that is waiting to be greeted by waiter
		notifyAll();

		//Update number of students at the restaurant
		numberOfStudentsAtRestaurant--;
		// seat at table becomes empty after waiter greets the student
		reposStub.updateSeatsAtLeaving(studentBeingAnswered);
		studentBeingAnswered = -1;

		reposStub.setWaiterState(WaiterStates.APRAISING_SITUATION);

		if(numberOfStudentsAtRestaurant == 0)
			return true;
		return false;
	}

	
	
	
	/**
	 * Operation bar server shutdown
	 * @throws Remote Exception if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void shutdown() throws RemoteException
	{
		nEntities += 1;
		if (nEntities >= ExecuteConst.S)
			ServerRestaurantBar.shutdown ();
		notifyAll();
	}
}



