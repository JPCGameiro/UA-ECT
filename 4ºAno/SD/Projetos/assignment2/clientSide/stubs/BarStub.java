package clientSide.stubs;

import clientSide.entities.*;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the Bar.
 *
 *    It instantiates a remote reference to the bar.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class BarStub {
	/**
	 * Name of the platform where is located the bar server
	 */
	private String serverHostName;
	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNumb;
	
	
	/**
	 * Instantiation of a stub to the Bar.
	 * 
	 * @param serverHostName name of the platform where is located the bar server
	 * @param serverPortNumb port number for listening to service requests
	 */
	public BarStub(String serverHostName, int serverPortNumb)
	{
		this.serverHostName = serverHostName;
		this.serverPortNumb = serverPortNumb;
	}
	
	
	
	/**
	 * Return id of the student whose request is being answered
	 * @return Id of the student whose request is being answered
	 */
	public int getStudentBeingAnswered() 
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
		
		outMessage = new Message (MessageType.REQGETSTDBEIANSW);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPGETSTDBEIANSW)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getStudentBeingAnswered();
	}
	
	
	
	
	/**
	 * Operation alert the waiter
	 * 
	 * It is called by the chef to alert the waiter that a portion was dished
	 * 	A request is putted in the queue (chef id will be N+1)
	 */
	public void alertWaiter() 
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
		
		outMessage = new Message (MessageType.REQALRTWAIT, ((Chef) Thread.currentThread()).getChefState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPALRTWAIT)
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
	 * Operation look Around
	 * 
	 * It is called by the waiter, he checks for pending service requests and if not waits for them
	 * 	@return Character that represents the service to be executed
	 * 		'c' : means a client has arrived therefore needs to be presented with the menu by the waiter
	 * 		'o' : means that the waiter will hear the order and deliver it to the chef
	 * 		'p' : means that a portion needs to be delivered by the waiter
	 * 		'b' : means that the bill needs to be prepared and presented by the waiter
	 * 		'g' : means that some student wants to leave and waiter needs to say goodbye 
	 */
	public char lookAround() 
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
		
		outMessage = new Message (MessageType.REQLOOKARND);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPLOOKARND)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();	
		return inMessage.getRequestType();
	}
	
	
	
	/**
	 * Operation prepare the Bill
	 * 
	 * It is called the waiter to prepare the bill of the meal eaten by the students
	 */
	public void prepareBill() 
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
		
		outMessage = new Message (MessageType.REQPRPREBILL, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPPRPREBILL)
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
	 * Operation say Goodbye
	 * 
	 * It is called by the waiter to say goodbye to a student that's leaving the restaurant
	 * @return true if there are no more students at the restaurant, false otherwise
	 */
	public boolean sayGoodbye() 
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
		
		outMessage = new Message (MessageType.REQSAYGDBYE, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSAYGDBYE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}

		//Close communication channel
		com.close ();
		return inMessage.getStudentsAtRestaurant();
	}
	
	
	
	
	/**
	 * Operation enter the restaurant
	 * 
	 * It is called by the student to signal that he is entering the restaurant
	 */
	public void enter() 
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

		outMessage = new Message (MessageType.REQENTER, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message		
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPENTER)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId())
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student id!");
			GenericIO.writelnString (inMessage.toString ());
	        System.exit (1);
		}
		if(inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}

		((Student) Thread.currentThread ()).setStudentState (inMessage.getStudentState());
		//Close communication channel
		com.close ();		
	}
	
	
	
	
	/**
	 * Operation call the waiter
	 * 
	 * It is called by the first student to arrive the restaurant to call the waiter to describe the order
	 *
	 */
	public void callWaiter()
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
		
		outMessage = new Message (MessageType.REQCALLWAI, ((Student) Thread.currentThread()).getStudentId());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPCALLWAI)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId())
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student id!");
			GenericIO.writelnString (inMessage.toString ());
	        System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	
	/**
	 * Operation signal the waiter
	 * 
	 * It is called by the last student to finish eating that next course can be brought 
	 * signal chef that he can put request in the queue and waiter that he proceed his executing to collect portions
	 * It is also used by last student to arrive to signal that he wishes to pay the bill
	 */
	public void signalWaiter() 
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
		
		outMessage = new Message (MessageType.REQSIGWAI, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSIGWAI)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId())
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student id!");
			GenericIO.writelnString (inMessage.toString ());
	        System.exit (1);
		}
		if(inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Student) Thread.currentThread ()).setStudentState (inMessage.getStudentState());
		//Close communication channel
		com.close ();		
	}
	
	
	
	/**
	 * Operation exit the restaurant
	 * 
	 * It is called by a student when he leaves the restaurant
	 */
	public void exit() 
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
		
		outMessage = new Message (MessageType.REQEXIT, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPEXIT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentId() != ((Student) Thread.currentThread()).getStudentId())
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student id!");
			GenericIO.writelnString (inMessage.toString ());
	        System.exit (1);
		}
		if(inMessage.getStudentState() < StudentStates.GOING_TO_THE_RESTAURANT || inMessage.getStudentState() > StudentStates.GOING_HOME)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid student state!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		((Student) Thread.currentThread ()).setStudentState (inMessage.getStudentState());
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
		
		outMessage = new Message (MessageType.REQBARSHUT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPBARSHUT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();			
	}
	
}
