package clientSide.stubs;

import commInfra.*;
import clientSide.entities.*;
import genclass.GenericIO;

/**
 *  Stub to the Kitchen.
 *
 *    It instantiates a remote reference to the barber shop.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class KitchenStub {
	/**
	 * Name of the platform where is located the kitchen server
	 */
	private String serverHostName;
	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNumb;
	
	
	/**
	 * Instantiation of a stub to the Kitchen.
	 * 
	 * @param serverHostName name of the platform where is located the kitchen server
	 * @param serverPortNumb port number for listening to service requests
	 */
	public KitchenStub(String serverHostName, int serverPortNumb)
	{
		this.serverHostName = serverHostName;
		this.serverPortNumb = serverPortNumb;
	}
	
	
	
	/**
	 * 	Operation watch the news
	 * 
	 * 	It is called by the chef, he waits for waiter to notify him of the order
	 */
	public void watchTheNews()	
	{
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQWATTNWS, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		if(inMessage.getMsgType() != MessageType.REPWATTNWS)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		com.close ();
	}
	
	
	
	/**
	 * 	Operation start presentation
	 * 
	 * 	It is called by the chef after waiter has notified him of the order to be prepared 
	 * 	to signal that preparation of the course has started
	 */
	public void startPreparation() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQSTRPR, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSTRPR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		//Close communication channel
		com.close ();
	}
	
	
	
	/**
	 * 	Operation proceed presentation
	 * 
	 * 	It is called by the chef when a portion needs to be prepared
	 */
	public void proceedPreparation() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQPROCPREP, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPPROCPREP)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		//Close communication channel
		com.close ();		
	}
	
	
	
	
	/**
	 * 	Operation have all portions been delivered
	 * 
	 * 	It is called by the chef when he finishes a portion and checks if another one needs to be prepared or not
	 * 	It is also here were the chef blocks waiting for waiter do deliver the current portion
	 * 	@return true if all portions have been delivered, false otherwise
	 */
	public boolean haveAllPortionsBeenDelivered() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQHVPRTDLVD);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPHVPRTDLVD)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getAllPortionsBeenDelivered();
	}
	
	
	
	
	/**
	 *	Operation has order been completed
	 * 
	 * 	It is called by the chef when he finishes preparing all courses to check if order has been completed or not
	 * 	@return true if all courses have been completed, false or not
	 */
	public boolean hasOrderBeenCompleted()
	{
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQHORDCOMPL);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPHORDCOMPL)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		
		//Close communication channel
		com.close ();
		return inMessage.getHasOrderBeenCompleted();
	}
	
	
	
	/**
	 * 	Operation continue preparation
	 * 
	 * 	It is called by the chef when all portions have been delivered, but the course has not been completed yet
	 */
	public void continuePreparation() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQCONTPREP, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPCONTPREP)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		//Close communication channel
		com.close ();
	}
	
	
	
	/**
	 * Operation have next portion ready
	 * 
	 * It is called by the chef after a portion has been delivered and another one needs to be prepared
	 */
	public void haveNextPortionReady() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQHAVNEXPORRD, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPHAVNEXPORRD)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		//Close communication channel
		com.close ();		
	}
	
	
	
	
	/**
	 * Operation clean up
	 * 
	 * It is called by the chef when he finishes the order, to close service
	 */
	public void cleanUp() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQCLEANUP, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPCLEANUP)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getChefState() < ChefStates.WAITING_FOR_AN_ORDER || inMessage.getChefState() > ChefStates.CLOSING_SERVICE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid chef state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Chef) Thread.currentThread ()).setChefState (inMessage.getChefState());
		//Close communication channel
		com.close ();			
	}
	
	
	
	
	/**
	 * Operation hand note to chef
	 * 
	 * Called by the waiter to wake chef up chef to give him the description of the order
	 */	
	public void handNoteToChef() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQHNDNOTCHEF, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPHNDNOTCHEF)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getWaiterState() < WaiterStates.APRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Waiter) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState());

		//Close communication channel
		com.close ();					
	}
	
	
	
	
	/**
	 * Operation return to the bar
	 * 
	 * Called by the waiter when he is the kitchen and returns to the bar
	 */
	public void returnToBar() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQRETURNTOBAR, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPRETURNTOBAR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getWaiterState() < WaiterStates.APRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Waiter) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState());
		
		//Close communication channel
		com.close ();			
	}
	
	
	
	/**
	 * Operation collect portion
	 * 
	 * Called by the waiter when there is a portion to be delivered. Collect and signal chef that the portion was delivered
	 */
	public void collectPortion() 
	{ 
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQCOLLPORT, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message

		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPCOLLPORT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getWaiterState() < WaiterStates.APRAISING_SITUATION || inMessage.getWaiterState() > WaiterStates.RECEIVING_PAYMENT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid waiter state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Waiter) Thread.currentThread ()).setWaiterState (inMessage.getWaiterState());
		//Close communication channel
		com.close ();	
	}
	
	
	
	/**
	 * Operation server shutdown
	 */
	public void shutdown()
	{
		ClientCom com;					//Client communication
		Message outMessage, inMessage; 	//outGoing and inGoing messages
		
		com = new ClientCom (serverHostName, serverPortNumb);
		//Wait for a connection to be established
		while(!com.open())
		{	try 
		  	{ Thread.currentThread ().sleep ((long) (10));
		  	}
			catch (InterruptedException e) {}
		}
		
		outMessage = new Message (MessageType.REQKITSHUT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPKITSHUT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();			
	}
}
