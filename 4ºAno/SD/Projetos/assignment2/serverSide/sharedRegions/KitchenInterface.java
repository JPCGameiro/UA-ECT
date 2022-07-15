package serverSide.sharedRegions;

import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the Kitchen
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Kitchen and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class KitchenInterface {

	/**
	 * Reference to the kitchen
	 */
	private final Kitchen kit;


	/**
	 * Instantiation of an interface to the kitchen.
	 * 	@param kit reference to the kitchen
	 */
	public KitchenInterface(Kitchen kit)
	{
		this.kit = kit;
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
		case MessageType.REQWATTNWS: 		// Watching the news request
		case MessageType.REQSTRPR: 			// Start preparation of a course request
		case MessageType.REQPROCPREP: 		// Proceed to presentation request
		case MessageType.REQHAVNEXPORRD:	// Have next portion ready
		case MessageType.REQCONTPREP: 		// Continue preparation
		case MessageType.REQCLEANUP: 		// Clean up
			if ((inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER) || (inMessage.getChefState() > ChefStates.CLOSING_SERVICE))
				throw new MessageException ("Invalid Chef state!", inMessage);
			break;

		// Chef Messages that require only type verification
		case MessageType.REQHVPRTDLVD: 		// Have all portions been delivered
		case MessageType.REQHORDCOMPL: 		// Has the order been completed
		case MessageType.REQKITSHUT:		//Kitchen shutdown
			break;

		// Waiter Messages that require type and state verification	
		case MessageType.REQHNDNOTCHEF: 	// Hand note to chef
		case MessageType.REQRETURNTOBAR: 	// Return to bar
		case MessageType.REQCOLLPORT: 		// Collect portion
			if(inMessage.getWaiterState() < WaiterStates.APRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
				throw new MessageException ("Invalid Waiter state!", inMessage);
			break;
		default:
			throw new MessageException ("Invalid message type!", inMessage);
		}

		/* Processing */
		switch(inMessage.getMsgType())
		{
		case MessageType.REQWATTNWS: //Watching the news request
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.watchTheNews();
			outMessage = new Message(MessageType.REPWATTNWS, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;
		case MessageType.REQSTRPR: //Start preparation of a course request
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.startPreparation();
			outMessage = new Message(MessageType.REPSTRPR, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;
		case MessageType.REQPROCPREP: //Proceed to presentation request
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.proceedPreparation();
			outMessage = new Message(MessageType.REPPROCPREP, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;	
		case MessageType.REQHVPRTDLVD: //Have all portions been delivered request
			boolean portionsDelivered = kit.haveAllPortionsBeenDelivered();
			outMessage = new Message(MessageType.REPHVPRTDLVD, portionsDelivered);
			break;
		case MessageType.REQHORDCOMPL: //Has the order been completed request
			boolean orderCompleted = kit.hasOrderBeenCompleted();
			outMessage = new Message(MessageType.REPHORDCOMPL, orderCompleted);
			break;
		case MessageType.REQCONTPREP: //Continue preparation
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.continuePreparation();
			outMessage = new Message(MessageType.REPCONTPREP, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;
		case MessageType.REQHAVNEXPORRD: //Have next portion ready
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.haveNextPortionReady();
			outMessage = new Message(MessageType.REPHAVNEXPORRD, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;
		case MessageType.REQCLEANUP: //clean up
			((KitchenClientProxy) Thread.currentThread()).setChefState(inMessage.getChefState());
			kit.cleanUp();
			outMessage = new Message(MessageType.REPCLEANUP, ((KitchenClientProxy) Thread.currentThread()).getChefState());
			break;
		case MessageType.REQHNDNOTCHEF: //hand note to chef
			((KitchenClientProxy) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
			kit.handNoteToChef();
			outMessage = new Message(MessageType.REPHNDNOTCHEF, ((KitchenClientProxy) Thread.currentThread()).getWaiterState());
			break;
		case MessageType.REQRETURNTOBAR: //return to bar
			((KitchenClientProxy) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
			kit.returnToBar();
			outMessage = new Message(MessageType.REPRETURNTOBAR, ((KitchenClientProxy) Thread.currentThread()).getWaiterState());
			break;
		case MessageType.REQCOLLPORT: //collect portion
			((KitchenClientProxy) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
			kit.collectPortion();
			outMessage = new Message(MessageType.REPCOLLPORT, ((KitchenClientProxy) Thread.currentThread()).getWaiterState());
			break;
		case MessageType.REQKITSHUT: //Kitchen shutdown
			kit.shutdown();
			outMessage = new Message(MessageType.REPKITSHUT);
			break;
		}

		return (outMessage);
	}

}
