package clientSide.entities;

import clientSide.stubs.*;
import serverSide.main.ExecuteConst;

/**
 *    Student thread.
 *
 *      It simulates the student life cycle.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public class Student extends Thread{
	
	/**
	 * 	Student id
	 */
	private int studentId;
	
	/**
	 * 	Student state
	 */
	private int studentState;
	
	/**
	 * Reference to the stub of the bar
	 */
	
	private final BarStub barStub;
	
	/**
	 * Reference to the stub of the table
	 */
	private final TableStub tabStub;
	
	
	
	/**
	 * Instantiation of a Student thread.
	 *  
	 * 	@param name thread name
	 * 	@param studentId student id
	 * 	@param barStub reference to the stub of the bar
	 * 	@param tabStub reference to the stub of the table
	 */
	public Student(String name, int studentId, BarStub barStub, TableStub tabStub) {
		super(name);
		this.studentId = studentId;
		this.studentState = StudentStates.GOING_TO_THE_RESTAURANT;
		this.barStub = barStub;
		this.tabStub = tabStub;
	}

	
	
	
	/**
	 * Set a new student id
	 * @param studentId id of the student to be set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	/**
	 * 	Get the student id
	 * 	@return student id
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * Set a new student state
	 * @param studentState new state to be set
	 */
	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}
	
	/**
	 * 	Get the student state
	 * 	@return student state
	 */
	public int getStudentState() {
		return studentState;
	}
	
	
	/**
	 *	Life cycle of the student
	 */
	@Override
	public void run ()
	{
		walkABit();
		barStub.enter();
		tabStub.readMenu();
		
		if(studentId == tabStub.getFirstToArrive())
		{
			tabStub.prepareOrder();
			while(!tabStub.everybodyHasChosen())
				tabStub.addUpOnesChoices();
			barStub.callWaiter();
			tabStub.describeOrder();
			tabStub.joinTalk();
		}
		else
			tabStub.informCompanion();
		
		int numCoursesEaten = 0;
		while(!tabStub.haveAllCoursesBeenEaten())
		{
			tabStub.startEating();
			tabStub.endEating();
			numCoursesEaten++;
			
			while(!tabStub.hasEverybodyFinishedEating());
			if(studentId == tabStub.getLastToEat() && numCoursesEaten != ExecuteConst.M)
				barStub.signalWaiter();
		}
		
		if(tabStub.shouldHaveArrivedEarlier()) 
		{
			barStub.signalWaiter();
			tabStub.honourBill();
		}
		barStub.exit();
	}
	
	
	/**
	 * Sleep for a random time
	 */
	private void walkABit()
	{
		try
		{ sleep ((long) (1 + 50 * Math.random ()));
		}
		catch (InterruptedException e) {}
	}
	
}
