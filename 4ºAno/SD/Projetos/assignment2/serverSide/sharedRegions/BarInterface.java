package serverSide.sharedRegions;

import clientSide.entities.ChefStates;
import clientSide.entities.StudentStates;
import clientSide.entities.WaiterStates;
import commInfra.Message;
import commInfra.MessageException;
import commInfra.MessageType;
import serverSide.entities.BarClientProxy;


/**
 *  Interface to the Bar
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Bar and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class BarInterface {
	/**
	 * Reference to the Bar
	 */
	private final Bar bar;


	/**
	 * Instantiation of an interface to the Bar.
	 * 	@param bar reference to the bar
	 */
	public BarInterface(Bar bar)
	{
		this.bar = bar;
	}


	/**
	 * Processing of the incoming messages
	 * Validation, execution of the corresponding method and generation of the outgoing message.
	 * 
	 * 	@param inMessage service request
	 * 	@return service reply
	 * 	@throws MessageException if incoming message was not valid
	 */
	public Message processAndReply (Message inMessage) throws MessageException
	{
		//outGoing message
		Message outMessage = null;

		/* Validation of the incoming message */

		switch(inMessage.getMsgType())
		{
			// Chef Messages that require type and state verification
			case MessageType.REQALRTWAIT: 		// Alert the Waiter Request
				if (inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
					throw new MessageException ("Invalid Chef state!", inMessage);
				break;
			
			//Waiter Messages that require only type verification
			case MessageType.REQLOOKARND: 		// Look around Request
			case MessageType.REQBARSHUT:		// Bar shutdown 
				break;
			// Waiter Messages that require type and state verification
			case MessageType.REQPRPREBILL: 		// Prepare the bill Request
			case MessageType.REQSAYGDBYE: 		// Say goodbye Request
				if (inMessage.getWaiterState() < WaiterStates.APRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
					throw new MessageException("Inavlid Waiter state!", inMessage);
				break;
			
			//Student Messages that require only type and id verification (already done in Message Constructor)
			case MessageType.REQCALLWAI:		// Call the waiter Request
				break;
			// Student Messages that require type, state and id verification (done in Message Constructor)
			case MessageType.REQENTER:			// Enter Request
			case MessageType.REQSIGWAI:			// Signal the waiter Request
			case MessageType.REQEXIT:			// exit Request
				if (inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME)
					throw new MessageException("Invalid Student state!", inMessage);
				break;
			
			//Additional Messages
			case MessageType.REQGETSTDBEIANSW:
				break;
			default:
				throw new MessageException ("Invalid message type!", inMessage);
		}

		/* Processing of the incoming message */

		switch(inMessage.getMsgType())
		{
			case MessageType.REQALRTWAIT:
				((BarClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
				bar.alertWaiter();
				outMessage = new Message(MessageType.REPALRTWAIT, ((BarClientProxy) Thread.currentThread()).getChefState());
				break;
			case MessageType.REQLOOKARND:
				char c = bar.lookAround();
				outMessage = new Message(MessageType.REPLOOKARND, c);
				break;
			case MessageType.REQPRPREBILL:
				((BarClientProxy) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
				bar.prepareBill();
				outMessage = new Message(MessageType.REPPRPREBILL, ((BarClientProxy) Thread.currentThread()).getWaiterState());
				break;
			case MessageType.REQSAYGDBYE:
				((BarClientProxy) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
				boolean b = bar.sayGoodbye();
				outMessage = new Message(MessageType.REPSAYGDBYE, b);
				break;
			case MessageType.REQENTER:
				((BarClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
				((BarClientProxy) Thread.currentThread()).setStudentState(inMessage.getStudentState());
				bar.enter();
				outMessage = new Message(MessageType.REPENTER, ((BarClientProxy) Thread.currentThread()).getStudentId(), ((BarClientProxy) Thread.currentThread()).getStudentState());
				break;
			case MessageType.REQCALLWAI:
				((BarClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
				bar.callWaiter();
				outMessage = new Message(MessageType.REPCALLWAI, ((BarClientProxy) Thread.currentThread()).getStudentId());
				break;
			case MessageType.REQSIGWAI:
				((BarClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
				((BarClientProxy) Thread.currentThread()).setStudentState(inMessage.getStudentState());
				bar.signalWaiter();
				outMessage = new Message(MessageType.REPSIGWAI, ((BarClientProxy) Thread.currentThread()).getStudentId(), ((BarClientProxy) Thread.currentThread()).getStudentState());
				break;
			case MessageType.REQEXIT:
				((BarClientProxy) Thread.currentThread()).setStudentState(inMessage.getStudentState());
				((BarClientProxy) Thread.currentThread()).setStudentId(inMessage.getStudentId());
				bar.exit();
				outMessage = new Message(MessageType.REPEXIT, ((BarClientProxy) Thread.currentThread()).getStudentId(), ((BarClientProxy) Thread.currentThread()).getStudentState());
				break;
			case MessageType.REQGETSTDBEIANSW:
				int id = bar.getStudentBeingAnswered();
				outMessage = new Message(MessageType.REPGETSTDBEIANSW, id);
				break;
			case MessageType.REQBARSHUT:
				bar.shutdown();
				outMessage = new Message(MessageType.REPBARSHUT);
				break;
		}

		return (outMessage);
	}
}
