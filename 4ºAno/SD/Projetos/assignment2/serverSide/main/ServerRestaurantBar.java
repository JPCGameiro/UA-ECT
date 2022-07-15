package serverSide.main;

import serverSide.entities.*;
import serverSide.sharedRegions.*;
import commInfra.*;
import genclass.GenericIO;
import java.net.*;

import clientSide.stubs.GeneralReposStub;
import clientSide.stubs.TableStub;

/**
 *    Server side of the Bar.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */
public class ServerRestaurantBar
{
	/**
	 *  Flag signaling the service is active.
	 */
	public static boolean waitConnection;

	/**
	 *  Main method.
	 *
	 *    @param args runtime arguments
	 *        args[0] - port number for listening to service requests
	 *        args[1] - name of the platform where is located the server for the general repository
	 *        args[2] - port number where the server for the general repository is listening to service requests
	 *        args[3] - name of the platform where is located the server for the table
	 *        args[4] - port number where the server for the table is listening to service requests
	 */
	public static void main (String [] args)
	{
		Bar bar;							// bar (service to be rendered)
		BarInterface barInter;				// interface to the bar
		GeneralReposStub reposStub;			// stub to the general repository
		TableStub tableStub;				// stub to the table
		ServerCom scon, sconi;				// communication channels
		int portNumb = -1;					// port number for listening to service requests
		String reposServerName;				// name of the platform where is located the server for the general repository
		int reposPortNumb = -1;				// port number where the server for the general repository is listening to service requests
		String tableServerName;				// name of the platform where is located the server for the table
		int tablePortNumb = -1;				// port number where the server for the table is listening to service requests

		if (args.length != 5)
		{ GenericIO.writelnString ("Wrong number of parameters!");
		System.exit (1);
		}
		for(int i = 0; i < args.length; i = i + 2) {
			try
			{ portNumb = Integer.parseInt (args[i]);
			}
			catch (NumberFormatException e)
			{ GenericIO.writeString ("args[");
				GenericIO.writeInt(i);
				GenericIO.writelnString("] is not a number!");
				System.exit (1);
			}
			if ((portNumb < 4000) || (portNumb >= 65536))
			{ GenericIO.writeInt(portNumb);
			GenericIO.writelnString ("is not a valid port number!");
			System.exit (1);
			}
		}
		portNumb = Integer.parseInt (args[0]);
		reposPortNumb = Integer.parseInt (args[2]);
		tablePortNumb = Integer.parseInt (args[4]);
		reposServerName = args[1];
		tableServerName = args[3];

		/* service is established */

		reposStub = new GeneralReposStub (reposServerName, reposPortNumb);	// communication to the general repository is instantiated
		tableStub = new TableStub (tableServerName, tablePortNumb);			// communication to the table is instantiated
		bar = new Bar(reposStub, tableStub);								// service is instantiated
		barInter = new BarInterface (bar);									// interface to the service is instantiated
		scon = new ServerCom (portNumb);									// listening channel at the public port is established
		scon.start ();
		GenericIO.writelnString ("Service is established!");
		GenericIO.writelnString ("Server is listening for service requests.");

		/* service request processing */

		BarClientProxy cliProxy;							// service provider agent

		waitConnection = true;
		while (waitConnection)
		{ try
		{ sconi = scon.accept ();							// enter listening procedure
		cliProxy = new BarClientProxy (sconi, barInter);	// start a service provider agent to address
		cliProxy.start ();									// the request of service
		}
		catch (SocketTimeoutException e) {}
		}
		scon.end ();										// operations termination
		GenericIO.writelnString ("Server was shutdown.");
	}
}
