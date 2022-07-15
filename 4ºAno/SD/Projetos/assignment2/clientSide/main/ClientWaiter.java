package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.stubs.*;
import genclass.GenericIO;

/**
 *  Client side of the Restaurant problem (waiter).
 *
 *	Implementation of a client-server model of type 2 (server replication).
 *	Communication is based on a communication channel under the TCP protocol.
 */
public class ClientWaiter {
	
	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments
	 *        args[0] - name of the platform where is located the kitchen server
	 *        args[1] - port number for listening to service requests
	 *        args[2] - name of the platform where is located the bar server
	 *        args[3] - port number for listening to service requests
	 *        args[4] - name of the platform where is located the table server
	 *        args[5] - port number for listening to service requests
     *		  args[6] - name of the platform where is located the general repository server
	 *        args[7] - port number for listening to service requests
	 */
	public static void main(String[] args) {

		Waiter waiter;					//Waiter thread
		KitchenStub kitStub;			//remote reference to the kitchen stub
		BarStub barStub;				//remote reference to the bar stub
		TableStub tabStub;				//remote reference to the table stub
		GeneralReposStub genReposStub;	//remote reference to the general repository
		
		//Name of the platforms where kitchen and bar servers are located
		String kitServerHostName, barServerHostName, tabServerHostName, genRepoServerHostName;
		//Port numbers for listening to service requests
		int kitServerPortNumb = -1, barServerPortNumb = -1, tabServerPortNumb = -1, genRepoServerPortNumb = -1;
		
		/* Getting problem runtime parameters */
		if(args.length != 8) {
			GenericIO.writelnString ("Wrong number of parameters!");
			System.exit(1);
		}
		//Get kitchen parameters
		kitServerHostName = args[0];
		try {
			kitServerPortNumb = Integer.parseInt (args[1]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[1] is not a number!");
			System.exit(1);
		}
		if( (kitServerPortNumb < 22110) || (kitServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[1] is not a valid port number!");
			System.exit(1);			
		}
		
		//Get bar parameters
		barServerHostName = args[2];
		try {
			barServerPortNumb = Integer.parseInt (args[3]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[3] is not a number!");
			System.exit(1);
		}
		if( (barServerPortNumb < 22110) || (barServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[3] is not a valid port number!");
			System.exit(1);			
		}

		//Get table parameters
		tabServerHostName = args[4];
		try {
			tabServerPortNumb = Integer.parseInt (args[5]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[5] is not a number!");
			System.exit(1);
		}
		if( (tabServerPortNumb < 22110) || (tabServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[5] is not a valid port number!");
			System.exit(1);			
		}
		
		
		//Get general repo parameters
		genRepoServerHostName = args[6];
		try {
			genRepoServerPortNumb = Integer.parseInt (args[7]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[7] is not a number!");
			System.exit(1);
		}
		if( (genRepoServerPortNumb < 22110) || (genRepoServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[7] is not a valid port number!");
			System.exit(1);			
		}
		
		
		/* problem initialisation */
		kitStub = new KitchenStub(kitServerHostName, kitServerPortNumb);
		barStub = new BarStub(barServerHostName, barServerPortNumb);
		tabStub = new TableStub(tabServerHostName, tabServerPortNumb);
		genReposStub = new GeneralReposStub(genRepoServerHostName, genRepoServerPortNumb);
		waiter = new Waiter("waiter", kitStub, barStub, tabStub);
		
		/* start simulation */
		GenericIO.writelnString ("Launching Waiter Thread");
		waiter.start();
		
		/* waiting for the end of the simulation */
		try {
			waiter.join();
		}catch(InterruptedException e) {}
		GenericIO.writelnString ("The waiter thread has terminated.");
		barStub.shutdown();
		tabStub.shutdown();
		genReposStub.shutdown();

	}

}
