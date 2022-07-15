package commInfra;

import java.io.*;
import genclass.GenericIO;
import serverSide.main.ExecuteConst;

/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable
{
	/**
	 *  Serialization key.
	 */
	private static final long serialVersionUID = 2021L;

	/**
	 *  Message type.
	 */
	private int msgType = -1;

	/**
	 * Chef State
	 */
	private int chefState = -1;

	/**
	 * Waiter State
	 */
	private int waiterState = -1;

	/**
	 * Student State
	 */
	private int studentState = -1;

	/**
	 * Student Id
	 */
	private int studentId = -1;
	
	/**
	 * student Id Being Answered
	 */	
	private int studentIdBeingAnswered = -1;
	
	/**
	 * Boolean value to be transported that holds true if all portions have been delivered, false otherwise
	 */
	private boolean allPortionsDelivered;

	/**
	 * Boolean value to be transported that holds true if order has been completed, false otherwise
	 */
	private boolean orderCompleted;
	
	/**
	 * Holds true if there are no students at the restaurant, false otherwise
	 */
	private boolean studentsAtRestaurant;
	
	/**
	 * Holds the value of the type of request that must be answered by the waiter
	 */
	private char requestType;
	
	/**
	 * Holds the id of the student whose request is being answered by the waiter
	 */
	private int studentBeingAnswered;
	
	/**
	 * Holds true if all clients have been served, false otherwise
	 */
	private boolean allClientsBeenServed;
	
	/**
	 * Holds true if everybody has choose their preference, false otherwise
	 */
	private boolean everybodyHasChosen;
	
	/**
	 * Holds true if everybody has finished eating, false otherwise
	 */
	private boolean everybodyHasEaten;
	
	/**
	 * Holds true if all courses have been eaten, false otherwise
	 */
	private boolean haveAllCoursesBeenEaten;
	
	/**
	 * Used to check which student was the last to arrive in the Table
	 */
	private boolean shouldArrivedEarlier;
	
	/**
	 * Holds the id of the first student to arrive
	 */
	private int firstToArrive;
	
	/**
	 * Holds the id of the last student to eat
	 */
	private int lastToEat;
	
	/**
	 * Holds the id of the last student to arrive
	 */
	private int lastToArrive;
	
	/**
	 * Holds the number of courses served (to be used in general repo)
	 */
	private int nCourses;
	
	/**
	 * Holds the number of portions served (to be used in general repo)
	 */
	private int nPortions;
	
	/**
	 * Holds the value of a specific seat at the table
	 */
	private int seatAtTable;
	
	/**
	 * Holds a value true or false depending if is necessary to print a reportStatus line or not
	 */
	private boolean hold;
	

	/**
	 *  Message instantiation (form 1).
	 *
	 *     @param type message type
	 */
	public Message (int type)
	{
		msgType = type;
	}

	/**
	 *  Message instantiation (form 2).
	 *
	 *     @param type message type
	 *     @param stateOrId chef, waiter or student state. It can also hold student id or id of studentBeingAnswered 
	 *     	or nCourses value or nPortions value.
	 */
	public Message (int type, int stateOrId)
	{
		msgType = type;
		int entitie = getEntitieFromMessageType(type);

		if(entitie == 1) //Chef message
			chefState = stateOrId;
		else if (entitie == 2) //Waiter message
			waiterState = stateOrId;
		else if (entitie == 3) { //Student message
			if(msgType == MessageType.REQCALLWAI || msgType == MessageType.REPCALLWAI || msgType == MessageType.REQEVERYBDFINISHEAT)
				studentId = stateOrId;
			else if(msgType == MessageType.REQPREPORDER || msgType == MessageType.REPPREPORDER || 
				    msgType == MessageType.REQJOINTALK || msgType == MessageType.REPJOINTALK)
				studentState = stateOrId;
		}
		else if (entitie == 4) {  //Additional message
			if (msgType == MessageType.REPGETFRSTARR)
				firstToArrive = stateOrId;
			else if (msgType == MessageType.REPGETLSTEAT)
				lastToEat = stateOrId;
			else if (msgType == MessageType.REQSETFRSTARR)
				firstToArrive = stateOrId;
			else if (msgType == MessageType.REQSETLSTARR)
				lastToArrive = stateOrId;
			else if (msgType == MessageType.REPGETSTDBEIANSW)
				studentBeingAnswered = stateOrId;
		}
		else if (entitie == 5) {	//General repository messages
			if (msgType == MessageType.REQSETCHST)
				chefState = stateOrId;
			else if (msgType == MessageType.REQSETWAIST)
				waiterState = stateOrId;
			else if (msgType == MessageType.REQSETNCOURSES) {
				if ( stateOrId < 0 || stateOrId  > ExecuteConst.M) {	// Not a valid number of courses
					GenericIO.writelnString ("Invalid number of courses");
					System.exit (1);
				} 
				nCourses = stateOrId;
			}
			else if (msgType == MessageType.REQSETNPORTIONS) {
				if ( stateOrId < 0 || stateOrId  > ExecuteConst.N) {	// Not a valid number of portions
					GenericIO.writelnString ("Invalid number of portions");
					System.exit (1);
				}
				nPortions = stateOrId;
			}
			else if (msgType == MessageType.REQUPDSEATSTABLELV){
				if ( stateOrId < 0 || stateOrId  >= ExecuteConst.N) {	// Not a valid Student id
					GenericIO.writelnString ("Invalid student id");
					System.exit (1);
				}
				studentId = stateOrId;
			}
		}
		else { 
			GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation!");
			System.exit (1);
		}

	}

	/**
	 * Message instantiation (form 3).
	 * 
	 * 	@param type message type
	 * 	@param bValue boolean that can have haveAllPortionsBeenDelivered, hasOrderBeenCompleted, number of studentsAtRestaurant 
	 * 		or allBeenClientsServed value.
	 */
	public Message(int type, boolean bValue)
	{
		msgType = type;
		if (msgType == MessageType.REPHVPRTDLVD)
			allPortionsDelivered = bValue;
		else if (msgType == MessageType.REPHORDCOMPL)
			orderCompleted = bValue;   
		else if (msgType == MessageType.REPSAYGDBYE)
			studentsAtRestaurant = bValue;
		else if (msgType == MessageType.REPALLCLISERVED)
			allClientsBeenServed = bValue;
		else if (msgType == MessageType.REPEVERYBDYCHO)
			everybodyHasChosen = bValue;
		else if (msgType == MessageType.REPALLCOURBEENEAT)
			haveAllCoursesBeenEaten = bValue;
		else if (msgType == MessageType.REPEVERYBDFINISHEAT)
			everybodyHasEaten = bValue;

			
	}

	/**
	 *  Message instantiation (form 4).
	 *
	 *     @param type message type
	 *     @param id student identification or student being answered by the waiter identification
	 *     @param stateOrSeat student state, waiter state or seat at the table (when used in the general repos functions)
	 */

	public Message (int type, int id, int stateOrSeat)
	{
		msgType = type;
		int entity = getEntitieFromMessageType(type);
		
		//Update seats at the table (general repos)
		if (msgType == MessageType.REQUPDSEATSTABLE)
			seatAtTable = stateOrSeat;
		//salute a client (waiter in the table)
		else if (msgType == MessageType.REQSALUTCLI || msgType == MessageType.REPSALUTCLI){
			studentBeingAnswered = id;
			waiterState = stateOrSeat;
			return;
		}
		else 
		{
			if ((entity != 3) && msgType != MessageType.REQUPDTSTUST1) {	// Not a Student entity Type Message
				GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation on Student!");
				System.exit (1);
			}
			studentState = stateOrSeat;
		}
		
		//Update studentId
		if ( id < 0 || id  >= ExecuteConst.N) {	// Not a valid Student id
			GenericIO.writelnString ("Invalid student id");
			System.exit (1);
		}
		studentId = id;
	}
	
	
	/**
	 * Message instantiation (form 5).
	 * 
	 * 		@param type message type
	 * 		@param id id of the student
	 * 		@param everybodyEaten holds the value of everybody has eaten (true if yes, false otherwise)
	 */
	public Message (int type, int id, boolean everybodyEaten)
	{
		msgType = type;
		studentId = id;
		everybodyHasEaten = everybodyEaten;	

	}
	
	
	/**
	 *  Message instantiation (form 6).
	 *
	 *     @param type message type
	 *     @param id of the student
	 *     @param state student state
	 *     @param bValue that can hold shouldHaveArrivedEarlier value or hold value (used in general repos)
	 */

	public Message (int type, int id, int state, boolean bValue)
	{
		//Check if student id is valid
		if ( id < 0 || id  >= ExecuteConst.N) {	// Not a valid Student id
			GenericIO.writelnString ("Invalid student id");
			System.exit (1);
		}

		msgType = type;
		studentId = id;
		studentState = state;
		if(msgType == MessageType.REPSHOULDARREARLY)
			shouldArrivedEarlier = bValue;
		else if (msgType == MessageType.REQUPDTSTUST2)
			hold = bValue;
	}
	
	
	/**
	 * 	Message instantiation (form 7).
	 * 		@param type message type
	 * 		@param c character that identifies which request should the waiter attend
	 */
	public Message(int type, char c)
	{
		msgType = type;
		requestType = c;		
	}
	
	/**
	 *  Getting message type.
	 *     @return message type
	 */
	public int getMsgType () { return (msgType); }

	/**
	 * Getting chef state
	 * 	@return chef state
	 */
	public int getChefState() { return (chefState); }

	/**
	 * Getting waiter state
	 * 	@return waiter state
	 */
	public int getWaiterState() { return (waiterState); }

	/**
	 * Getting student state
	 * 	@return student state
	 */
	public int getStudentState() { return (studentState); }

	/**
	 * Getting student id
	 * 	@return student id
	 */
	public int getStudentId() { return (studentId); }
	
	/**
	 * Getting student being answered id
	 * @return studentIdBeingAnswered
	 */
	public int getStudentIdBeingAnswered() {return (studentIdBeingAnswered); }

	/**
	 * Get have all portions been delivered
	 * @return true if all portions have been delivered, false otherwise
	 */
	public boolean getAllPortionsBeenDelivered() { return (allPortionsDelivered); }

	/**
	 * Get has the order been completed value
	 * @return true if order has been completed, false otherwise
	 */
	public boolean getHasOrderBeenCompleted() { return (orderCompleted); }

	/**
	 * Get request type
	 * @return character that represents request type
	 */
	public char getRequestType() { return (requestType); }
	
	/**
	 * Get if there students at restaurant or not
	 * @return true if there aren't students at the restaurant, false otherwise
	 */
	public boolean getStudentsAtRestaurant() { return (studentsAtRestaurant); }
	
	/**
	 * Get id of the student whose request is being answered by the waiter
	 * @return id of the student
	 */
	public int getStudentBeingAnswered() { return (studentBeingAnswered); }
	
	/**
	 * Get the value of have all clients been served
	 * @return true if all clients have been served, false otherwise
	 */
	public boolean getAllClientsBeenServed() { return (allClientsBeenServed); }
	
	/**
	 * Get the value of everybody has chosen
	 * @return true if everybody has chosen their preference, false otherwise
	 */
	public boolean getEverybodyHasChosen() { return (everybodyHasChosen); }
	
	/**
	 * Get the value of everybody has finished eating
	 * @return true if everybody has eaten, false otherwise
	 */
	public boolean getHasEverybodyFinishedEating() { return (everybodyHasEaten); }
	
	/**
	 * Get the value of have all courses been eaten
	 * @return true if all courses have been eaten, false otherwise
	 */
	public boolean getAllCoursesEaten() { return (haveAllCoursesBeenEaten); }
	
	/**
	 * Get the value of should have arrived earlier
	 * @return value of shouldArrivedEarlier
	 */
	public boolean getArrivedEarlier() { return (shouldArrivedEarlier); }
	
	/**
	 * Get id of the first student to arrive
	 * @return id of the student
	 */
	public int getFirstToArrive() { return (firstToArrive); }
	
	/**
	 * Get id of the last student to eat
	 * @return id of the student
	 */
	public int getLastToEat() { return (lastToEat); }
	
	/**
	 * Get id of the last student to arrive
	 * @return id of the student
	 */
	public int getLastToArrive() { return (lastToArrive); }
	
	/**
	 * Get seatAtTable value
	 * @return the value of the variable seatAtTable
	 */
	public int getSeatAtTable() { return (seatAtTable); }
	
	/**
	 * Get nCourses value
	 * @return nCourses value
	 */
	public int getNCourses() { return (nCourses); }
	
	/**
	 * Get nPortions value
	 * @return nPortions value
	 */
	public int getNPortions() { return (nPortions); }
	
	/**
	 * Get hold variable value
	 * @return the value of hold variable used to specify if is necessary to print report status or not
	 */
	public boolean getHold() { return (hold); }
	
	
	
	
	/**
	 * For a given message type, get the entity that called it (chef, waiter or student) 
	 * @param messageType type of the message
	 * @return 1 if called by chef, 2 if called bye waiter and 3 if called by student
	 */
	public int getEntitieFromMessageType(int messageType)
	{
		///FALTAM AQUI MENSAGENS
		switch(messageType)
		{
			// Chef messages
			case MessageType.REQWATTNWS: 		case MessageType.REPWATTNWS:
			case MessageType.REQSTRPR: 			case MessageType.REPSTRPR:
			case MessageType.REQPROCPREP: 		case MessageType.REPPROCPREP:
			case MessageType.REQHVPRTDLVD: 		case MessageType.REPHVPRTDLVD:
			case MessageType.REQHORDCOMPL: 		case MessageType.REPHORDCOMPL:
			case MessageType.REQCONTPREP: 		case MessageType.REPCONTPREP :
			case MessageType.REQHAVNEXPORRD: 	case MessageType.REPHAVNEXPORRD:
			case MessageType.REQCLEANUP: 		case MessageType.REPCLEANUP:
			case MessageType.REQALRTWAIT: 		case MessageType.REPALRTWAIT:
				return 1;
			// Waiter messages
			case MessageType.REQHNDNOTCHEF:		case MessageType.REPHNDNOTCHEF:
			case MessageType.REQRETURNTOBAR: 	case MessageType.REPRETURNTOBAR:
			case MessageType.REQCOLLPORT: 		case MessageType.REPCOLLPORT:
			case MessageType.REQLOOKARND:		case MessageType.REPLOOKARND:
			case MessageType.REQPRPREBILL: 		case MessageType.REPPRPREBILL:
			case MessageType.REQSAYGDBYE: 		case MessageType.REPSAYGDBYE:
			case MessageType.REQSALUTCLI: 		case MessageType.REPSALUTCLI:
			case MessageType.REQRTRNBAR:		case MessageType.REPRTRNBAR:
			case MessageType.REQGETPAD:			case MessageType.REPGETPAD:
			case MessageType.REQALLCLISERVED:	case MessageType.REPALLCLISERVED:
			case MessageType.REQDELPOR:			case MessageType.REPDELPOR:
			case MessageType.REQPRESBILL:		case MessageType.REPPRESBILL:
				return 2;
			// Student messages
			case MessageType.REQENTER: 				case MessageType.REPENTER:
			case MessageType.REQCALLWAI: 			case MessageType.REPCALLWAI:
			case MessageType.REQSIGWAI: 			case MessageType.REPSIGWAI:
			case MessageType.REQEXIT: 				case MessageType.REPEXIT:
			case MessageType.REQSEATTABLE:			case MessageType.REPSEATTABLE:
			case MessageType.REQRDMENU:				case MessageType.REPRDMENU:
			case MessageType.REQPREPORDER:			case MessageType.REPPREPORDER:
			case MessageType.REQEVERYBDYCHO:		case MessageType.REPEVERYBDYCHO:
			case MessageType.REQADDUP1CHOI:			case MessageType.REPADDUP1CHOI:
			case MessageType.REQDESCRORDER:			case MessageType.REPDESCRORDER:
			case MessageType.REQJOINTALK:			case MessageType.REPJOINTALK:
			case MessageType.REQINFORMCOMP:			case MessageType.REPINFORMCOMP:
			case MessageType.REQSRTEATING:			case MessageType.REPSRTEATING:
			case MessageType.REQENDEATING:			case MessageType.REPENDEATING:
			case MessageType.REQEVERYBDFINISHEAT:	case MessageType.REPEVERYBDFINISHEAT:
			case MessageType.REQHONBILL:			case MessageType.REPHONBILL:
			case MessageType.REQALLCOURBEENEAT:		case MessageType.REPALLCOURBEENEAT:
			case MessageType.REQSHOULDARREARLY:		case MessageType.REPSHOULDARREARLY:
				return 3;
			//Additional Messages
			case MessageType.REQGETSTDBEIANSW:	case MessageType.REPGETSTDBEIANSW:
			case MessageType.REQGETFRSTARR:		case MessageType.REPGETFRSTARR:
			case MessageType.REQGETLSTEAT:		case MessageType.REPGETLSTEAT:
			case MessageType.REQSETFRSTARR:		case MessageType.REPSETFRSTARR:
			case MessageType.REQSETLSTARR:		case MessageType.REPSETLSTARR:
			case MessageType.REQKITSHUT:		case MessageType.REPKITSHUT:
			case MessageType.REQBARSHUT:		case MessageType.REPBARSHUT:
			case MessageType.REQTABSHUT:		case MessageType.REPTABSHUT:
				return 4;
			//GeneralRepo Message
			case MessageType.REQSETCHST:		 case MessageType.REPSETCHST:
			case MessageType.REQSETWAIST:		 case MessageType.REPSETWAIST:
			case MessageType.REQUPDTSTUST1:		 case MessageType.REPUPDTSTUST1:
			case MessageType.REQUPDTSTUST2:		 case MessageType.REPUPDTSTUST2:
			case MessageType.REQSETNCOURSES:	 case MessageType.REPSETNCOURSES:
			case MessageType.REQSETNPORTIONS:	 case MessageType.REPSETNPORTIONS:
			case MessageType.REQUPDSEATSTABLE:	 case MessageType.REPUPDSEATSTABLE:
			case MessageType.REQUPDSEATSTABLELV: case MessageType.REPUPDSEATSTABLELV:
			case MessageType.REQGENERALREPOSHUT: case MessageType.REPGENERALREPOSHUT:
				return 5;
			default:
				return -1;
		}
	}


	/**
	 *  Printing the values of the internal fields.
	 *
	 *  It is used for debugging purposes.
	 *  @return string containing, in separate lines, the pair field name - field value
	 */

	@Override
	public String toString ()
	{
		return ("Message type = " + msgType +
				"\nChef State = " + chefState +
				"\nWaiter State = " + waiterState +
				"\nStudentId = " + studentId + " StudentState = " + studentState + 
				"\nStudentIdBeingAnswered = " + studentIdBeingAnswered +
				"\nNumber of studentsAtRestaurant = " + studentsAtRestaurant +
				"\nAll Portions Been Delivered = " + allPortionsDelivered + 
				"\nHas the Order been completed = " + orderCompleted + 
				"\nRequest type = " + requestType + 
				"\nHave all clients been served = " + allClientsBeenServed +
				"\nEverybody has chosen = " + everybodyHasChosen +
				"\nEverybody has eaten = " + everybodyHasEaten + 
				"\nHave all courses been eaten = " + haveAllCoursesBeenEaten +
				"\nShould have arrived earlier = " + shouldArrivedEarlier +
				"\nFirst to arrive = " + firstToArrive + " Lat to arrive = " +lastToArrive +
				"\nLast to eat = " + lastToEat +
				"\nnCourses = " + nCourses + " nPortions = "+ nPortions +
				"\nHold = " + hold + " Seat at the table = " + seatAtTable +
				"");
	}
}
