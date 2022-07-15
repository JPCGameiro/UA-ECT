package entities;

import sharedRegions.Bar;
import sharedRegions.Table;
import main.ExecuteConst;

/**
 *   Student thread.
 *
 *   It simulates the student life cycle.
 */

public class Student extends Thread{
	
	/**
	 * 	Student identification
	 */
	private int studentId;
	
	/**
	 * 	Student state
	 */
	private int studentState;
	
	/**
	 * Reference to the bar
	 */
	
	private final Bar bar;
	
	/**
	 * Reference to the table
	 */
	private final Table tab;
	
	
	
	/**
	 * Student Thread instantiation
	 * 
	 * @param name Name of the thread
	 * @param studentId Id of the student
	 * @param bar reference to the bar
	 * @param tab reference to the table
	 */
	public Student(String name, int studentId, Bar bar, Table tab) {
		super(name);
		this.studentId = studentId;
		this.studentState = StudentStates.GOING_TO_THE_RESTAURANT;
		this.bar = bar;
		this.tab = tab;
	}

	
	
	
	/**
	 * 
	 * @param studentId id of the student to be set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	/**
	 * 	@return student id
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * 
	 * @param studentState new state to be set
	 */
	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}
	
	/**
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
		bar.enter();
		tab.readMenu();
		
		if(studentId == tab.getFirstToArrive())
		{
			tab.prepareOrder();
			while(!tab.everybodyHasChosen())
				tab.addUpOnesChoices();
			bar.callWaiter();
			tab.describeOrder();
			tab.joinTalk();
		}
		else
			tab.informCompanion();
		
		int numCoursesEaten = 0;
		while(!tab.haveAllCoursesBeenEaten())
		{
			tab.startEating();
			tab.endEating();
			numCoursesEaten++;
			
			while(!tab.hasEverybodyFinishedEating());
			if(studentId == tab.getLastToEat() && numCoursesEaten != ExecuteConst.M)
				bar.signalWaiter();
		}
		
		if(tab.shouldHaveArrivedEarlier()) 
		{
			bar.signalWaiter();
			tab.honourBill();
		}
		bar.exit();
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
