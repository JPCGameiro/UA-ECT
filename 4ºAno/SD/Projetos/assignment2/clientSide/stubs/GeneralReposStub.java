package clientSide.stubs;

import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
 *  Stub to the General Repository.
 *
 *    It instantiates a remote reference to the general repository.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class GeneralReposStub {
	/**
	 * Name of the platform where is located the general repository server
	 */
	private String serverHostName;
	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNumb;
	
	
	/**
	 * Instantiation of a stub to the General Repository. 
	 * 
	 * @param serverHostName name of the platform where is located the general repository server
	 * @param serverPortNumb port number for listening to service requests
	 */
	public GeneralReposStub(String serverHostName, int serverPortNumb)
	{
		this.serverHostName = serverHostName;
		this.serverPortNumb = serverPortNumb;
	}
	

	
	
	
	/**
	 * Write in the logging file the new chef state
	 * @param state chef state to set
	 */
	public void setChefState(int state)
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
		
		outMessage = new Message (MessageType.REQSETCHST, state);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETCHST)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	/**
	 * Write in the logging file the new waiter state
	 * @param state waiter state to set
	 */
	public void setWaiterState(int state)
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
		
		outMessage = new Message (MessageType.REQSETWAIST, state);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETWAIST)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	/**
	 * Write in the logging file the updated student state
	 * @param id student id
	 * @param state student state to be set
	 */
	public void updateStudentState(int id, int state)
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
		
		outMessage = new Message (MessageType.REQUPDTSTUST1, id, state);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPUPDTSTUST1)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}


	/**
	 * Write in the logging file the updated student state
	 * @param id student id
	 * @param state student state to be set
	 * @param hold specifies if prints line of report status
	 */
	public void updateStudentState(int id, int state, boolean hold)
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
		
		outMessage = new Message (MessageType.REQUPDTSTUST2, id, state, hold);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPUPDTSTUST2)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}

	/**
	 * Set variable nCourses and report status in the logging file
	 * @param value nCourses value to set
	 */
	public void setnCourses(int value)
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
		
		outMessage = new Message (MessageType.REQSETNCOURSES, value);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETNCOURSES)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	/**
	 * Set variable nPortions and report status in the logging file
	 * @param value nCourses value to set
	 */
	public void setnPortions(int value)
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
		
		outMessage = new Message (MessageType.REQSETNPORTIONS, value);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETNPORTIONS)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	/**
	 * Write in the logging file the updated seats value at the table
	 * @param seat seat at the table
	 * @param id student id to sit
	 */
	public void updateSeatsAtTable(int seat, int id)
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
		
		outMessage = new Message (MessageType.REQUPDSEATSTABLE, id, seat);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPUPDSEATSTABLE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	/**
	 * Update the leaving of a student in the seats of the table
	 * @param id student id to leave table
	 */
	public void updateSeatsAtLeaving(int id)
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
		
		outMessage = new Message (MessageType.REQUPDSEATSTABLELV, id);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPUPDSEATSTABLELV)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();		
	}
	
	
	
	/**
	 *   Operation server shutdown.
	 *
	 *   New operation.
	 */
	public void shutdown ()
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
		
		outMessage = new Message (MessageType.REQGENERALREPOSHUT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPGENERALREPOSHUT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
	}
}
