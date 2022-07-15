package serverSide.objects;

import clientSide.entities.*;
import java.util.Objects;
import java.rmi.RemoteException;
import genclass.GenericIO;
import genclass.TextFile;
import interfaces.GeneralReposInterface;
import serverSide.main.ExecuteConst;
import serverSide.main.ServerRestaurantGeneralRepos;

/**
 *  General Repository of information.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */
public class GeneralRepos implements GeneralReposInterface{

	/**
	 *  Name of the logging file.
	 */
	private final String logFileName;

	/**
	 *  State of the Chef
	 */
	private int chefState;

	/**
	 *	State of the Waiter
	 */
	private int waiterState;

	/**
	 *  State of the Chef
	 */
	private int[] studentState;

	/**
	 *	Number of courses delivered (not sure)
	 */
	private int nCourses;

	/**
	 * 	Number of Portions delivered (not sure)
	 */
	private int nPortions;

	/**
	 * 	Seats at the table
	 */
	private int[] seatsAtTable;
	
	/**
	 * Number of entity groups requesting the shutdown
	 */
	private int nEntities;

	
	
	
	/**
	 *	Instantiation of a general repository object.
	 */
	public GeneralRepos ()
	{
		this("");
	}
	
	
	/**
	 *	Instantiation of a general repository object.
	 *
	 *	@param logFileName name of the logging file
	 */
	public GeneralRepos (String logFileName)
	{
		nEntities = 0;
		if ((logFileName == null) || Objects.equals (logFileName, ""))
			this.logFileName = "logger";
		else this.logFileName = logFileName;  		
		chefState = ChefStates.WAITING_FOR_AN_ORDER;
		waiterState = WaiterStates.APRAISING_SITUATION;
		studentState = new int[ExecuteConst.N];
		for (int i = 0; i < ExecuteConst.N; i++)
			studentState[i] = StudentStates.GOING_TO_THE_RESTAURANT;
		nCourses = 0;
		nPortions = 0;
		seatsAtTable = new int[ExecuteConst.N];
		for(int i = 0; i < ExecuteConst.N; i++)
		{
			seatsAtTable[i] = -1;
		}
		reportInitialStatus ();
	}	
	

	
	/**
	 *  Write the header to the logging file.
	 *
	 *  The chef and the waiter are sleeping and the students are going to the restaurant.
	 */
	private void reportInitialStatus ()
	{
		TextFile log = new TextFile ();                      // instantiation of a text file handler

		if (!log.openForWriting (".", logFileName))
		{ 
			GenericIO.writelnString ("The operation of creating the file " + logFileName + " failed!");
			System.exit (1);
		}
		log.writelnString ("                                        The Restaurant - Description of the internal state");
		log.writelnString (" Chef Waiter  Stu0  Stu1  Stu2  Stu3  Stu4  Stu5  Stu6  NCourse  NPortion                    Table");
		log.writelnString ("State State  State State State State State State State                     Seat0 Seat1 Seat2 Seat3 Seat4 Seat5 Seat6");
		if (!log.close ())
		{ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}
	}	
	
	
	
	/**
	 *  Write a state line at the end of the logging file.
	 *
	 *  The current state of the barbers and the customers is organized in a line to be printed.
	 *  Internal operation.
	 */
	private void reportStatus ()
	{
		TextFile log = new TextFile ();                  	// instantiation of a text file handler
		String line = "";                              		// state line to be printed
		if (!log.openForAppending (".", logFileName))
		{ 
			GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit (1);
		}
		switch(chefState)
		{
		case ChefStates.WAITING_FOR_AN_ORDER: line += "WAFOR "; break;
		case ChefStates.PREPARING_THE_COURSE: line += "PRPCS "; break;
		case ChefStates.DISHING_THE_PORTIONS: line += "DSHPT "; break;
		case ChefStates.DELIVERING_THE_PORTIONS: line += "DLVPT "; break;
		case ChefStates.CLOSING_SERVICE: line += "CLSSV "; break;
		}

		switch(waiterState)
		{
		case WaiterStates.APRAISING_SITUATION: line += "APPST  "; break;
		case WaiterStates.PRESENTING_THE_MENU: line += "PRSMN  "; break;
		case WaiterStates.TAKING_THE_ORDER: line += "TKODR  "; break;
		case WaiterStates.PLACING_ODER: line += "PCODR  "; break;
		case WaiterStates.WAITING_FOR_PORTION: line += "WTFPT  "; break;
		case WaiterStates.PROCESSING_THE_BILL: line += "PRCBL  "; break;
		case WaiterStates.RECEIVING_PAYMENT: line += "RECPM  "; break;
		}

		for(int i = 0; i < ExecuteConst.N; i++)
		{
			switch(studentState[i])
			{
			case StudentStates.GOING_TO_THE_RESTAURANT: line += "GGTRT "; break;
			case StudentStates.TAKING_A_SEAT_AT_THE_TABLE: line += "TKSTT "; break;
			case StudentStates.SELECTING_THE_COURSES: line += "SELCS "; break;
			case StudentStates.ORGANIZING_THE_ORDER: line += "OGODR "; break;
			case StudentStates.CHATING_WITH_COMPANIONS: line += "CHTWC "; break;
			case StudentStates.ENJOYING_THE_MEAL: line += "EJYML "; break;
			case StudentStates.PAYING_THE_BILL: line += "PYTBL "; break;
			case StudentStates.GOING_HOME: line += "GGHOM "; break;
			}
		}

		line += "    " + String.valueOf(nCourses);
		line += "        " + String.valueOf(nPortions);
		line += "        " + (seatsAtTable[0] >= 0 ? String.valueOf(seatsAtTable[0]) : "-");
		for(int i = 1; i < ExecuteConst.N; i++)
		{
			line += "     " + (seatsAtTable[i] >= 0 ? String.valueOf(seatsAtTable[i]) : "-");
		}

		log.writelnString (line);
		if (!log.close ())
		{ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}
	}
	
	
	
	/**
	 * Write in the logging file the legend
	 */
	private void reportLegend() {
		TextFile log = new TextFile ();                  	// instantiation of a text file handler
		String line = "";                              		// state line to be printed
		if (!log.openForAppending (".", logFileName))
		{ 
			GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit (1);
		}
		
		line += "\n\n";
		line += "Legend:\n";
		line += "Chef State   - state of the chef: WAFOR PRPCS DSHPT DLVPT CLSSV\n";
		line += "Waiter State - state of the waiter: APPST PRSMN TKODR PCODR WTFPT PRCBL RECPM\n";
		line += "Stu# State   - state of the student #: GGTRT TKSTT SELCS OGODR CHTWC EJYML PYTBL GGHOM\n";
		line += "NCourse      - number of the course: 0 upto M\n";
		line += "NPortion     - number of the portion of a course: 0 upto N\n";
		line += "Table Seat#  - id of the student sat at that chair\n";
		
		log.writelnString (line);
		if (!log.close ())
		{ 
			GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
			System.exit (1);
		}		
	}

	
	
	/**
	 * Write in the logging file the new chef state
	 * @param value chef state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void setChefState(int value) throws RemoteException {
		this.chefState = value;
		reportStatus();
	}

	
	
	
	/**
	 * Write in the logging file the new waiter state
	 * @param value waiter state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void setWaiterState(int value) throws RemoteException {
		this.waiterState = value;
		reportStatus();
	}

	
	
	/**
	 * Write in the logging file the updated student state
	 * @param id student id
	 * @param value student state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void updateStudentState(int id, int value) throws RemoteException {
		this.studentState[id] = value;
		reportStatus();		
	}

	
	
	/**
	 * Update student state
	 * @param id student id
	 * @param value student state to set
	 * @param hold specifies if prints line of report status
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void updateStudentState(int id, int value, boolean hold) throws RemoteException {
		this.studentState[id] = value;		
	}

	
	
	/**
	 * Set variable nCourses and report status in the logging file
	 * @param value nCourses value to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void setnCourses(int value, int chefState) throws RemoteException {
		this.nCourses = value;
		this.chefState = chefState;
		reportStatus();
	}

	
	
	/**
	 * Write the portion value in the logging file
	 * @param value nPortions value to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */	
	@Override
	public synchronized void setnPortions(int value, int chefState) throws RemoteException {
		this.nPortions = value;		
		this.chefState = chefState;
		reportStatus();
	}

	
	/**
	 * Update the chef state, the nPortion and nCourse values
	 * 
	 * @param nPortion number of the portion to be set
	 * @param nCourse number of the course to be set
	 * @param chefState chef state
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public void setnPortionsAndCourses(int nPortion, int nCourse, int chefState) throws RemoteException {
		this.nPortions = nPortion;
		this.nCourses = nCourse;
		this.chefState = chefState;
		reportStatus();		
	}
	
	
	
	/**
	 * Write to the logging file the updated seats values at the table 
	 * 
	 * @param seat seat at the table
	 * @param id student id to sit
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */	
	@Override
	public synchronized void updateSeatsAtTable(int seat, int id) throws RemoteException {
		this.seatsAtTable[seat] = id;
		reportStatus();
	}

	
	
	/**
	 * Update the leaving of a student in the seats of the table
	 * @param id student id to leave table
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void updateSeatsAtLeaving(int id) throws RemoteException {
		int seat = 0;
		
		for(int i=0; i < this.seatsAtTable.length; i++) {
			if(this.seatsAtTable[i] == id)
				seat = i;
		}
		
		this.seatsAtTable[seat] = -1;
	}

	
	
	/**
	 * Operation server shutdown.
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	@Override
	public synchronized void shutdown() throws RemoteException {
		nEntities += 1;
		if (nEntities >= ExecuteConst.S) {
			reportLegend();
			ServerRestaurantGeneralRepos.shutdown ();
		}
		notifyAll();
	}

}
