package clientSide.entities;

import java.rmi.RemoteException;
import genclass.GenericIO;
import interfaces.*;
import serverSide.main.ExecuteConst;

/**
 *    Student thread.
 *
 *      It simulates the student life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on remote calls under Java RMI.
 */
public class Student extends Thread{
	
	/**
	 * Student identification
	 */
	private int studentId;
	
	/**
	 * Student state
	 */
	private int studentState;
	
	/**
	 * Reference to the stub of the bar
	 */
	
	private final BarInterface barStub;
	
	/**
	 * Reference to the stub of the table
	 */
	private final TableInterface tabStub;
	
	
	
	/**
	 * Instantiation of student thread.
	 * 
	 * 	@param name thread name
	 * 	@param studentId student id
	 * 	@param barStub remote reference to the bar
	 * 	@param tabStub remote reference to the table
	 */
	public Student(String name, int studentId, BarInterface barStub, TableInterface tabStub)
	{
		super(name);
		setStudentState(StudentStates.GOING_TO_THE_RESTAURANT);
		this.studentId = studentId;
		this.barStub = barStub;
		this.tabStub = tabStub;
	}
	

	
	/**
	 * Get Student state
	 * @return student state
	 */
	public int getStudentState() { return studentState; }


	/**
	 * Set student state
	 * @param studentState new state to be set
	 */
	public void setStudentState(int studentState) {	this.studentState = studentState; }



	/**
	 *	Life cycle of the student
	 */
	@Override
	public void run ()
	{
		walkABit();
		enter();
		readMenu();
		
		if(studentId == getFirstToArrive())
		{
			prepareOrder();
			while(!everybodyHasChosen())
				addUpOnesChoices();
			callWaiter();
			describeOrder();
			joinTalk();
		}
		else
			informCompanion();
		
		int numCoursesEaten = 0;
		while(!haveAllCoursesBeenEaten())
		{
			startEating();
			endEating();
			numCoursesEaten++;
			
			while(!hasEverybodyFinishedEating());
			if(studentId == getLastToEat() && numCoursesEaten != ExecuteConst.M)
				signalWaiter();
		}
		
		if(shouldHaveArrivedEarlier()) 
		{
			signalWaiter();
			honourBill();
		}
		exit();
	}
	

	
	/**
	 * Sleep for a random time
	 * Internal operation.
	 */
	private void walkABit()
	{
		try
		{ sleep ((long) (1 + 50 * Math.random ()));
		}
		catch (InterruptedException e) {}
	}
	
	
	
	/**
	 * Operation enter the restaurant
	 * Remote operation.
	 * It is called by the student to signal that he is entering the restaurant
	 */
	private void enter()
	{
		try 
		{ studentState = barStub.enter(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on enter: " +e.getMessage());
			System.exit(1);
		}
	}
	
	
    /**
     * Operation read the menu
     * Remote Operation. 
     * Called by the student to read a menu, wakes up waiter to signal that he already read the menu
     */	
	private void readMenu()
	{
		try 
		{ studentState = tabStub.readMenu(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on readMenu: " +e.getMessage());
			System.exit(1);			
		}
	}
	
	
	
	/**
	 * Operation call the waiter
	 * Remote operation.
	 * It is called by the first student to arrive the restaurant to call the waiter to describe the order
	 *
	 */
	private void callWaiter() 
	{
		try
		{ barStub.callWaiter(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on callWaiter: " +e.getMessage());
			System.exit(1);			
		}
	}
	
	
	
    /**
     * Obtain id of the first student to arrive
     * Remote operation.
     * @return id of the first student to arrive at the restaurant
     */
	private int getFirstToArrive()
	{
		int firstToArrive = -1;
		
		try 
		{ firstToArrive = tabStub.getFirstToArrive();
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on getFirstToArrive: " +e.getMessage());
			System.exit(1);				
		}
		if (firstToArrive == -1)
		{			
			GenericIO.writelnString("Invalid id received in getFirstToArrive");
			System.exit(1);	
		}
		return firstToArrive;
	}
	
	
	
    /**
     * Operation prepare the order
     * Remote operation.
     * Called by the student to begin the preparation of the order (options of his companions) 
     */	
	private void prepareOrder()
	{
		try
		{ studentState = tabStub.prepareOrder();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on prepareOrder: " +e.getMessage());
			System.exit(1);			
		}		
	}
	
	
	
    /**
     * Operation everybody has chosen
     * Remote operation.
     * 	Called by the first student to arrive to check if all his companions have choose or not
     * 	Blocks if not waiting to be waker up be a companion to give him his preference
     * 	@return true if everybody choose their course choice, false otherwise
     */
    private boolean everybodyHasChosen()
    {
    	boolean everybodyHasChosen = false;
    	
		try
		{ everybodyHasChosen = tabStub.everybodyHasChosen();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on everybodyHasChosen: " +e.getMessage());
			System.exit(1);			
		}
		
		return everybodyHasChosen;
    }

    
    
    /**
     * Operation add up ones choices
     * Remote operation.
     * 	Called by the first student to arrive to add up a companions choice to the order
     */
    private void addUpOnesChoices()
    {
		try
		{ tabStub.addUpOnesChoices();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on addUpOnesChoices: " +e.getMessage());
			System.exit(1);			
		}
    }

    
    
    /**
     * Operation describe the order
     * Remote operation.
     * 	Called by the first student to arrive to describe the order to the waiter
     * 	Blocks waiting for waiter to come with pad
     * 	Wake waiter up so he can take the order
     */
    private void describeOrder()
    {
		try
		{ tabStub.describeOrder();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on describeOrder: " +e.getMessage());
			System.exit(1);			
		}    	
    }
    
    
    
    /**
     * Operation join the talk
     * Remote operation.
     * 	Called by the first student to arrive so he can join his companions 
     * 	while waiting for the courses to be delivered
     */
    private void joinTalk()
    {
		try
		{ studentState = tabStub.joinTalk();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on joinTalk: " +e.getMessage());
			System.exit(1);			
		}    	
    }

    /**
     * Operation inform companion
     * Remote Operation.
     * 	Called by a student to inform the first student to arrive about their preferences 
     * 	Blocks if someone else is informing at the same time
     */
    private void informCompanion() 
    {
		try
		{ studentState = tabStub.informCompanion(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on informCompanion: " +e.getMessage());
			System.exit(1);			
		}   	
    }
    
    
    
    /**
     * 	Operation have all courses been eaten
     * 	Remote operation.
     * 	Called by the student to check if there are more courses to be eaten
     * 	Student blocks waiting for the course to be served
     * 	@return true if all courses have been eaten, false otherwise
     */
    private boolean haveAllCoursesBeenEaten()
    {
    	boolean haveAllCoursesEaten = false;
    	
		try
		{ haveAllCoursesEaten = tabStub.haveAllCoursesBeenEaten();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on haveAllCoursesBeenEaten: " +e.getMessage());
			System.exit(1);			
		}		
		return haveAllCoursesEaten;    	
    }
    
    
    
    /**
     * Operation start eating
     * Remote operation.
     * Called by the student to start eating the meal (During random time)
     */    
    private void startEating()
    {
		try
		{ studentState = tabStub.startEating(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on startEating: " +e.getMessage());
			System.exit(1);			
		}       	
    }
    
    
    /**
     * Operation end eating
     * Remote operation.
     * Called by the student to signal that he has finished eating his meal
     */
    private void endEating()
    {
		try
		{ studentState = tabStub.endEating(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on endEating: " +e.getMessage());
			System.exit(1);			
		}      	
    }
    
    
    /**
     * Operation has everybody finished eating
     * Remote operation.
     * Called by the student to wait for his companions to finish eating
     * @return true if everybody has finished eating, false otherwise
     */
    private boolean hasEverybodyFinishedEating()
    {
    	boolean hasEverybodyFinished = false;
    	
		try
		{ hasEverybodyFinished = tabStub.hasEverybodyFinishedEating(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on hasEverybodyFinishedEating: " +e.getMessage());
			System.exit(1);			
		}		
		return hasEverybodyFinished;   
    }
 
    
    
    /**
     * Obtain id of the last student to arrive
     * Remote operation.
     * @return id of the last student to finish eating a meal
     */
    private int getLastToEat()
    {
    	int lastToEat = -1;
    	
		try
		{ lastToEat = tabStub.getLastToEat();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on getLastToEat: " +e.getMessage());
			System.exit(1);			
		}		
		return lastToEat;  
    }

    
    
	/**
	 * Operation signal the waiter
	 * Remote operation.
	 * It is called by the last student to finish eating that next course can be brought 
	 * signal chef that he can put request in the queue and waiter that he proceed his executing to collect portions
	 * It is also used by last student to arrive to signal that he wishes to pay the bill
	 */
	private void signalWaiter()
	{
		try
		{ barStub.signalWaiter(studentId, studentState);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on signalWaiter: " +e.getMessage());
			System.exit(1);			
		}   		
	}
	
	
	
    /**
     * Operation should have arrived earlier
     * Remote operation.
     * Called by the student to check which one was last to arrive
     * @return True if current student was the last to arrive, false otherwise
     */
    private boolean shouldHaveArrivedEarlier()	
    {
    	ReturnBoolean res = null;
    	
		try
		{ res = tabStub.shouldHaveArrivedEarlier(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on shouldHaveArrivedEarlier: " +e.getMessage());
			System.exit(1);			
		}
		
		studentState = res.getIntStateVal();
		return res.getBoolVal();
    }

    
    
    /**
     * Operation honour the bill
     * Remote operation.
     * Called by the student to pay the bill
     * Student blocks waiting for bill to be presented and signals waiter when it's time to pay it
     */
    private void honourBill() 
    {
		try
		{ tabStub.honourBill();			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on honourBill: " +e.getMessage());
			System.exit(1);			
		}
    }
    
    
	/**
	 * Operation exit the restaurant
	 * Remote operation.
	 * It is called by a student when he leaves the restaurant
	 */    
    private void exit()
    {
		try
		{ studentState = barStub.exit(studentId);			
		}
		catch (RemoteException e)
		{
			GenericIO.writelnString("Student "+ studentId + " remote exception on exit: " +e.getMessage());
			System.exit(1);			
		}    	
    }
}


