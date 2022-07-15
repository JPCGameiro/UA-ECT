package clientSide.stubs;

import clientSide.entities.*;
import commInfra.ClientCom;
import commInfra.Message;
import commInfra.MessageType;
import genclass.GenericIO;

/**
*  Stub to the Table.
 *
 *    It instantiates a remote reference to the barber shop.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class TableStub {
	/**
	 * Name of the platform where is located the table server
	 */
	private String serverHostName;
	/**
	 * Port number for listening to service requests
	 */
	private int serverPortNumb;
	
	
	/**
	 * Instantiation of a stub to the Table. 
	 * 
	 * @param serverHostName name of the platform where is located the kitchen server
	 * @param serverPortNumb port number for listening to service requests
	 */
	public TableStub(String serverHostName, int serverPortNumb)
	{
		this.serverHostName = serverHostName;
		this.serverPortNumb = serverPortNumb;
	}

	
	
	
    /**
     * Obtain id of the first student to arrive
     * @return id of the first student to arrive at the restaurant
     */
    public int getFirstToArrive() 
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
		
		outMessage = new Message (MessageType.REQGETFRSTARR);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPGETFRSTARR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getFirstToArrive();
    }
    
    
    
    
    /**
     * Obtain id of the last student to arrive
     * @return id of the last student to finish eating a meal
     */
    public int getLastToEat() 
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
		
		outMessage = new Message (MessageType.REQGETLSTEAT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPGETLSTEAT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getLastToEat();
    }
    
    
    /**
     * Set id of the first student to arrive
     * @param firstToArrive id of the first student to arrive
     */
    public void setFirstToArrive(int firstToArrive) 
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
		
		outMessage = new Message (MessageType.REQSETFRSTARR, firstToArrive);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETFRSTARR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    /**
     * Set id of the last student to arrive
     * @param lastToArrive if of the last student to arrive to the restaurant
     */
    public void setLastToArrive(int lastToArrive)
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
		
		outMessage = new Message (MessageType.REQSETLSTARR, lastToArrive);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSETLSTARR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    
    
    
    
    
    /**
     * Operation salute the client
     * 
     * It is called by the waiter when a student enters the restaurant and needs to be saluted
     * Waiter waits for the student to take a seat (if he hasn't done it yet)
     * Waiter waits for student to finish reading the menu
     */
    public void saluteClient(int studentIdBeingAnswered) 
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
		outMessage = new Message (MessageType.REQSALUTCLI, studentIdBeingAnswered, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message

		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSALUTCLI)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentBeingAnswered() != -1)
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
     * It is called by the waiter to return to the bar to the appraising situation state
     */
    public  void returnBar() 
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
		
		outMessage = new Message (MessageType.REQRTRNBAR, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPRTRNBAR)
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
     * Operation get the pad
     * 
     * It is called by the waiter when an order is going to be described by the first student to arrive
     * Waiter Blocks waiting for student to describe him the order
     */
    public  void getThePad() 
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
		
		outMessage = new Message (MessageType.REQGETPAD, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPGETPAD)
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
     * Operation have all clients been served
     * 
     * Called by the waiter to check if all clients have been served or not
     * @return true if all clients have been served, false otherwise
     */
    public  boolean haveAllClientsBeenServed() 
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

		outMessage = new Message (MessageType.REQALLCLISERVED);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPALLCLISERVED)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		
		//Close communication channel
		com.close ();
		return inMessage.getAllClientsBeenServed();
    }
    
    
    
    /**
     * Operation deliver portion
     * 
     * Called by the waiter, to deliver a portion
     */
    public  void deliverPortion() 
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
		
		outMessage = new Message (MessageType.REQDELPOR);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPDELPOR)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    
    
    
    /**
     * Operation present the bill
     * 
     * Called by the waiter to present the bill to the last student to arrive
     */
    public  void presentBill() 
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
		
		outMessage = new Message (MessageType.REQPRESBILL, ((Waiter) Thread.currentThread()).getWaiterState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPPRESBILL)
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
     * Operation siting at the table
     * 
     * Student comes in the table and sits (blocks) waiting for waiter to bring him the menu
     * Called by the student (inside enter method in the bar)
     */
    public  void seatAtTable() 
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

		outMessage = new Message (MessageType.REQSEATTABLE, ((StudentCloning) Thread.currentThread()).getStudentId(),((StudentCloning) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSEATTABLE)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		if(inMessage.getStudentId() != ((StudentCloning) Thread.currentThread()).getStudentId())
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
		((StudentCloning) Thread.currentThread ()).setStudentState (inMessage.getStudentState());

		//Close communication channel
		com.close ();
    }
    
    
    
    /**
     * Operation read the menu
     * 
     * Called by the student to read a menu, wakes up waiter to signal that he already read the menu
     */
    public  void readMenu() 
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
		
		outMessage = new Message (MessageType.REQRDMENU, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPRDMENU)
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
     * Operation prepare the order
     * 
     * Called by the student to begin the preparation of the order (options of his companions) 
     */
    public  void prepareOrder() 
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
		outMessage = new Message (MessageType.REQPREPORDER,((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPPREPORDER)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
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
     * Operation everybody has chosen
     * 
     * Called by the first student to arrive to check if all his companions have choose or not
     * Blocks if not waiting to be waker up be a companion to give him his preference
     * @return true if everybody choose their course choice, false otherwise
     */
    public  boolean everybodyHasChosen() 
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
		
		outMessage = new Message (MessageType.REQEVERYBDYCHO);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPEVERYBDYCHO)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getEverybodyHasChosen();
    }
    
    
    /**
     * Operation add up ones choices
     * 
     * Called by the first student to arrive to add up a companions choice to the order
     */
    public  void addUpOnesChoices()
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
		
		outMessage = new Message (MessageType.REQADDUP1CHOI);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPADDUP1CHOI)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    
    
    /**
     * Operation describe the order
     * 
     * Called by the first student to arrive to describe the order to the waiter
     * Blocks waiting for waiter to come with pad
     * Wake waiter up so he can take the order
     */
    public  void describeOrder() 
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
		
		outMessage = new Message (MessageType.REQDESCRORDER);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPDESCRORDER)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    /**
     * Operation join the talk
     * 
     * Called by the first student to arrive so he can join his companions 
     * while waiting for the courses to be delivered
     */
    public  void joinTalk() 
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
		
		outMessage = new Message (MessageType.REQJOINTALK,((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPJOINTALK)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
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
     * Operation inform companion
     * 
     * Called by a student to inform the first student to arrive about their preferences 
     * Blocks if someone else is informing at the same time
     */
    public  void informCompanion() 
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
		
		outMessage = new Message (MessageType.REQINFORMCOMP, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPINFORMCOMP)
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
     * Operation start eating
     * 
     * Called by the student to start eating the meal (During random time)
     */    
    public void startEating() 
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
		
		outMessage = new Message (MessageType.REQSRTEATING, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSRTEATING)
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
     * Operation end eating
     * 
     * Called by the student to signal that he has finished eating his meal
     */
    public  void endEating() 
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
		
		outMessage = new Message (MessageType.REQENDEATING, ((Student) Thread.currentThread()).getStudentId(),((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPENDEATING)
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
     * Operation has everybody finished eating
     * 
     * Called by the student to wait for his companions to finish eating
     */
    public  boolean hasEverybodyFinishedEating() 
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

		outMessage = new Message (MessageType.REQEVERYBDFINISHEAT, ((Student) Thread.currentThread()).getStudentId());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message

		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPEVERYBDFINISHEAT)
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
		return inMessage.getHasEverybodyFinishedEating();
    }
    
    
    
    /**
     * Operation honour the bill
     * 
     * Called by the student to pay the bill
     * Student blocks waiting for bill to be presented and signals waiter when it's time to pay it
     */
    public  void honourBill() 
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
		
		outMessage = new Message (MessageType.REQHONBILL);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPHONBILL)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
    }
    
    
    
    
    /**
     * 	Operation have all courses been eaten
     * 
     * 	Called by the student to check if there are more courses to be eaten
     * 	Student blocks waiting for the course to be served
     * 	@return true if all courses have been eaten, false otherwise
     */
    public  boolean haveAllCoursesBeenEaten() 
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
		
		outMessage = new Message (MessageType.REQALLCOURBEENEAT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPALLCOURBEENEAT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();
		return inMessage.getAllCoursesEaten();
    }
    
    
    
    /**
     * Operation should have arrived earlier
     * 
     * Called by the student to check which one was last to arrive
     * @return True if current student was the last to arrive, false otherwise
     */
    public  boolean shouldHaveArrivedEarlier() 
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
		
		outMessage = new Message (MessageType.REQSHOULDARREARLY, ((Student) Thread.currentThread()).getStudentId(), ((Student) Thread.currentThread()).getStudentState());
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPSHOULDARREARLY)
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
		return inMessage.getArrivedEarlier();
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
		
		outMessage = new Message (MessageType.REQTABSHUT);
		com.writeObject (outMessage); 			//Write outGoing message in the communication channel
		inMessage = (Message) com.readObject(); //Read inGoing message
		
		//Validate inGoing message type and arguments
		if(inMessage.getMsgType() != MessageType.REPTABSHUT)
		{
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		//Close communication channel
		com.close ();			
	}
}
