package clientSide.main;

import clientSide.entities.Student;
import clientSide.stubs.*;
import serverSide.main.ExecuteConst;
import genclass.GenericIO;

/**
 *  Client side of the Restaurant problem (student).
 *
 *	Implementation of a client-server model of type 2 (server replication).
 *	Communication is based on a communication channel under the TCP protocol.
 */
public class ClientStudent {
	
	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments
	 *        args[0] - name of the platform where is located the Bar server
	 *        args[1] - port number for listening to service requests
     *		  args[2] - name of the platform where is located the Kitchen server
	 *        args[3] - port number for listening to service requests
	 *        args[4] - name of the platform where is located the General Repository server
	 *        args[5] - port number for listening to service requests
	 */
	public static void main(String[] args) {

		Student[] student = new Student[ExecuteConst.N]; 	//Student threads
		BarStub barStub;									//remote reference to the bar stub
		TableStub tabStub;									//remote reference to the table stub
		GeneralReposStub genReposStub;						//remote reference to the general repository
		
		//Name of the platforms where kitchen and bar servers are located
		String barServerHostName, tabServerHostName, genRepoServerHostName;
		//Port numbers for listening to service requests
		int barServerPortNumb = -1, tabServerPortNumb = -1, genRepoServerPortNumb = -1;
		
		/* Getting problem runtime parameters */
		if(args.length != 6) {
			GenericIO.writelnString ("Wrong number of parameters!");
			System.exit(1);
		}
		//Get bar parameters
		barServerHostName = args[0];
		try {
			barServerPortNumb = Integer.parseInt (args[1]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[1] is not a number!");
			System.exit(1);
		}
		if( (barServerPortNumb < 22110) || (barServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[1] is not a valid port number!");
			System.exit(1);			
		}
		
		//Get tab parameters
		tabServerHostName = args[2];
		try {
			tabServerPortNumb = Integer.parseInt (args[3]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[3] is not a number!");
			System.exit(1);
		}
		if( (tabServerPortNumb < 22110) || (tabServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[3] is not a valid port number!");
			System.exit(1);			
		}
		
		//Get general repo parameters
		genRepoServerHostName = args[4];
		try {
			genRepoServerPortNumb = Integer.parseInt (args[5]);
		} catch (NumberFormatException e) {
			GenericIO.writelnString ("args[5] is not a number!");
			System.exit(1);
		}
		if( (genRepoServerPortNumb < 22110) || (genRepoServerPortNumb > 22119) ) {
			GenericIO.writelnString ("args[5] is not a valid port number!");
			System.exit(1);			
		}
		
		
		/* problem initialisation */
		barStub = new BarStub(barServerHostName, barServerPortNumb);
		tabStub = new TableStub(tabServerHostName, tabServerPortNumb);
		genReposStub = new GeneralReposStub(genRepoServerHostName, genRepoServerPortNumb);
		for (int i = 0; i < ExecuteConst.N; i++)
			student[i] = new Student ("student_" + (i+1), i, barStub, tabStub);
		
		/* start simulation */
		for (int i = 0; i < ExecuteConst.N; i++) {
			GenericIO.writelnString ("Launching Student Thread "+i);
			student[i].start();
		}
		
		/* waiting for the end of the simulation */
		for(int i = 0; i < ExecuteConst.N; i++)
		{
			try {
				student[i].join();
			}catch(InterruptedException e) {}
			GenericIO.writelnString ("The student"+(i+1)+" thread has terminated.");
		}
		genReposStub.shutdown();

	}

}
