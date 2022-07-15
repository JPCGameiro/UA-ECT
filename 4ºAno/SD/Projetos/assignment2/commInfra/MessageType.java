package commInfra;

/**
 *   Type of the exchanged messages.
 *the
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class MessageType
{
	//---------KITCHEN INPUT/OUTPUT MESSAGES---------//

	/**
	 * Chef - Operation watching the news (request)
	 */
	public static final int REQWATTNWS = 1;
	/**
	 * Chef - Operation watching the news (reply)
	 */
	public static final int REPWATTNWS = 2;
	/**
	 * Chef - Operation start preparation of a course (request)
	 */
	public static final int REQSTRPR = 3;
	/**
	 * Chef - Operation start preparation of a course (reply)
	 */
	public static final int REPSTRPR = 4;
	/**
	 * Chef - Operation proceed to preparation (request)
	 */
	public static final int REQPROCPREP = 5;
	/**
	 * Chef - Operation proceed to preparation (reply)
	 */
	public static final int REPPROCPREP = 6;
	/**
	 * Chef - Operation have all portions been delivered (request)
	 */
	public static final int REQHVPRTDLVD = 7;
	/**
	 * Chef - Operation have all portions been delivered (reply)
	 */
	public static final int REPHVPRTDLVD = 8;
	/**
	 * Chef - Operation has the order been completed (request)
	 */
	public static final int REQHORDCOMPL = 9;
	/**
	 * Chef - Operation has the order been completed (reply)
	 */
	public static final int REPHORDCOMPL = 10;
	/**
	 * Chef - Operation continue to preparation (request)
	 */
	public static final int REQCONTPREP = 11;
	/**
	 * Chef - Operation continue to preparation (reply)
	 */
	public static final int REPCONTPREP = 12;
	/**
	 * Chef - Operation have next portion ready (request)
	 */
	public static final int REQHAVNEXPORRD = 13;
	/**
	 * Chef - Operation have next portion ready (reply)
	 */
	public static final int REPHAVNEXPORRD = 14;
	/**
	 * Chef - Operation clean up (request)
	 */
	public static final int REQCLEANUP = 15;
	/**
	 * Chef - Operation clean up (reply)
	 */
	public static final int REPCLEANUP = 16;
	/**
	 * Waiter - Operation hand note to chef (request)
	 */
	public static final int REQHNDNOTCHEF = 17;
	/**
	 * Waiter - Operation hand note to chef (reply)
	 */
	public static final int REPHNDNOTCHEF = 18;
	/**
	 * Waiter - Operation return to bar (request)
	 */
	public static final int REQRETURNTOBAR = 19;
	/**
	 * Waiter - Operation return to bar (reply)
	 */
	public static final int REPRETURNTOBAR = 20;
	/**
	 * Waiter - Operation collect portion (request)
	 */
	public static final int REQCOLLPORT = 21;
	/**
	 * Waiter - Operation collect portion (reply)
	 */
	public static final int REPCOLLPORT = 22;
	/**
	 * Additional - Operation kitchen server shutdown (request)
	 */
	public static final int REQKITSHUT = 23;
	/**
	 * Additional - Operation kitchen server shutdown (reply)
	 */
	public static final int REPKITSHUT = 24;



	//---------BAR INPUT/OUTPUT MESSAGES---------//

	/**
	 * Chef - Operation alert the waiter (request)
	 */
	public static final int REQALRTWAIT = 30;
	/**
	 * Chef - Operation alert the waiter (reply)
	 */
	public static final int REPALRTWAIT = 31;
	/**
	 * Waiter - Operation look around (request)
	 */
	public static final int REQLOOKARND = 32;
	/**
	 * Waiter - Operation look around (reply)
	 */
	public static final int REPLOOKARND = 33;
	/**
	 * Waiter - Operation prepare Bill (request)
	 */
	public static final int REQPRPREBILL = 34;
	/**
	 * Waiter - Operation prepare Bill (reply)
	 */
	public static final int REPPRPREBILL = 35;
	/**
	 * Waiter - Operation say goodbye (request)
	 */
	public static final int REQSAYGDBYE = 36;
	/**
	 * Waiter - Operation say goodbye (reply)
	 */
	public static final int REPSAYGDBYE = 37;
	/**
	 * Student - Operation enter (request)
	 */
	public static final int REQENTER = 38;
	/**
	 * Student - Operation enter (reply)
	 */
	public static final int REPENTER = 39;
	/**
	 * Student - Operation call waiter (request)
	 */
	public static final int REQCALLWAI = 40;
	/**
	 * Student - Operation call waiter (reply)
	 */
	public static final int REPCALLWAI = 41;
	/**
	 * Student - Operation signal waiter (request)
	 */
	public static final int REQSIGWAI = 42;
	/**
	 * Student - Operation signal waiter (reply)
	 */
	public static final int REPSIGWAI = 43;
	/**
	 * Student - Operation exit (request)
	 */
	public static final int REQEXIT = 44;
	/**
	 * Student - Operation exit (reply)
	 */
	public static final int REPEXIT = 45;
	/**
	 * Additional - Operation get student being answered (request)
	 */
	public static final int REQGETSTDBEIANSW = 46;
	/**
	 * Additional - Operation get student being answered (reply)
	 */
	public static final int REPGETSTDBEIANSW = 47;
	/**
	 * Additional - Operation bar server shutdown (request)
	 */
	public static final int REQBARSHUT = 48;
	/**
	 * Additional - Operation bar server shutdown (reply)
	 */
	public static final int REPBARSHUT = 49;
	
	
	
	
	//---------TABLE INPUT/OUTPUT MESSAGES---------//

	/**
	 * Waiter - Operation salute the client (request)
	 */
	public static final int REQSALUTCLI = 60;
	/**
	 * Waiter - Operation salute the client (reply)
	 */
	public static final int REPSALUTCLI = 61;
	/**
	 * Waiter - Operation return to the bar (request)
	 */
	public static final int REQRTRNBAR = 62;
	/**
	 * Waiter - Operation return to the bar (reply)
	 */
	public static final int REPRTRNBAR = 63;
	/**
	 * Waiter - Operation get the pad (request)
	 */
	public static final int REQGETPAD = 64;
	/**
	 * Waiter - Operation get the pad (reply)
	 */
	public static final int REPGETPAD = 65;
	/**
	 * Waiter - Operation have all clients been served (request)
	 */
	public static final int REQALLCLISERVED = 66;
	/**
	 * Waiter - Operation have all clients been served (reply)
	 */
	public static final int REPALLCLISERVED = 67;
	/**
	 * Waiter - Operation deliver portion (request)
	 */
	public static final int REQDELPOR = 68;
	/**
	 * Waiter - Operation deliver portion (reply)
	 */
	public static final int REPDELPOR = 69;
	/**
	 * Waiter - Operation present the bill (request)
	 */
	public static final int REQPRESBILL = 70;
	/**
	 * Waiter - Operation present the bill (reply)
	 */
	public static final int REPPRESBILL = 71;
	/**
	 * Student - Operation seat at table (request)
	 */
	public static final int REQSEATTABLE = 72;
	/**
	 * Student - Operation seat at table (reply)
	 */
	public static final int REPSEATTABLE = 73;
	/**
	 * Student - Operation read the menu (request)
	 */
	public static final int REQRDMENU = 74;
	/**
	 * Student - Operation read the menu (reply)
	 */
	public static final int REPRDMENU = 75;
	/**
	 * Student - Operation prepare the order (request)
	 */
	public static final int REQPREPORDER = 76;
	/**
	 * Student - Operation prepare the order (reply)
	 */
	public static final int REPPREPORDER = 77;
	/**
	 * Student - Operation every body has chosen (request)
	 */
	public static final int REQEVERYBDYCHO = 78;
	/**
	 * Student - Operation every body has chosen (reply)
	 */
	public static final int REPEVERYBDYCHO = 79;
	/**
	 * Student - Operation add up one choices (request)
	 */
	public static final int REQADDUP1CHOI = 80;
	/**
	 * Student - Operation add up ones choices (reply)
	 */
	public static final int REPADDUP1CHOI = 81;
	/**
	 * Student - Operation describe order (request)
	 */
	public static final int REQDESCRORDER = 82;
	/**
	 * Student - Operation describe order (reply)
	 */
	public static final int REPDESCRORDER = 83;
	/**
	 * Student - Operation join talk (request)
	 */
	public static final int REQJOINTALK = 84;
	/**
	 * Student - Operation join talk (reply)
	 */
	public static final int REPJOINTALK = 85;
	/**
	 * Student - Operation inform companion (request)
	 */
	public static final int REQINFORMCOMP = 86;
	/**
	 * Student - Operation inform companion (reply)
	 */
	public static final int REPINFORMCOMP = 87;
	/**
	 * Student - Operation start eating (request)
	 */
	public static final int REQSRTEATING = 88;
	/**
	 * Student - Operation start eating (reply)
	 */
	public static final int REPSRTEATING = 89;
	/**
	 * Student - Operation end eating (request)
	 */
	public static final int REQENDEATING = 90;
	/**
	 * Student - Operation end eating (reply)
	 */
	public static final int REPENDEATING = 91;
	/**
	 * Student - Operation has every body finished eating (request)
	 */
	public static final int REQEVERYBDFINISHEAT = 92;
	/**
	 * Student - Operation has every body finished eating (reply)
	 */
	public static final int REPEVERYBDFINISHEAT = 93;
	/**
	 * Student - Operation honour bill (request)
	 */
	public static final int REQHONBILL = 94;

	/**
	 * Student - Operation honour bill (reply)
	 */
	public static final int REPHONBILL = 95;
	/**
	 * Student - Operation have all courses been eaten (request)
	 */
	public static final int REQALLCOURBEENEAT = 96;
	/**
	 * Student - Operation have all courses been eaten (reply)
	 */
	public static final int REPALLCOURBEENEAT = 97;
	/**
	 * Student - Operation should have arrived earlier (request)
	 */
	public static final int REQSHOULDARREARLY = 98;
	/**
	 * Student - Operation should have arrived earlier (reply)
	 */
	public static final int REPSHOULDARREARLY = 99;
	/**
	 * Additional - Operation get first to arrive (request)
	 */
	public static final int REQGETFRSTARR= 100;
	/**
	 * Additional - Operation get first to arrive (reply)
	 */
	public static final int REPGETFRSTARR = 101;
	/**
	 * Additional - Operation get last to eat (request)
	 */
	public static final int REQGETLSTEAT = 102;
	/**
	 * Additional - Operation get first to arrive (reply)
	 */
	public static final int REPGETLSTEAT = 103;
	/**
	 * Additional - Operation set first to arrive (request)
	 */
	public static final int REQSETFRSTARR = 104;
	/**
	 * Additional - Operation get first to arrive (reply)
	 */
	public static final int REPSETFRSTARR = 105;
	/**
	 * Additional - Operation set last to arrive (request)
	 */
	public static final int REQSETLSTARR = 106;
	/**
	 * Additional - Operation set last to arrive (reply)
	 */
	public static final int REPSETLSTARR = 107;
	/**
	 * Additional - Operation table server shutdown (request)
	 */
	public static final int REQTABSHUT = 108;
	/**
	 * Additional - Operation table server shutdown (reply)
	 */
	public static final int REPTABSHUT = 109;
	
	
	
	//---------GENERAL REPO INPUT/OUTPUT MESSAGES---------//
	
	/**
	 * Operation set chef state (request)
	 */
	public static final int REQSETCHST = 120;
	/**
	 * Operation set chef state (reply)
	 */
	public static final int REPSETCHST = 121;
	/**
	 * Operation set waiter state (request)
	 */
	public static final int REQSETWAIST = 122;
	/**
	 * Operation set waiter state (reply)
	 */
	public static final int REPSETWAIST = 123;
	/**
	 * Operation update student state version 1(request)
	 */
	public static final int REQUPDTSTUST1 = 124;
	/**
	 * Operation update student state version 1(reply)
	 */
	public static final int REPUPDTSTUST1 = 125;
	/**
	 * Operation update student state version 2(request)
	 */
	public static final int REQUPDTSTUST2 = 126;
	/**
	 * Operation update student state version 2(reply)
	 */
	public static final int REPUPDTSTUST2 = 127;
	/**	
	 * Operation set n courses (request)
	 */
	public static final int REQSETNCOURSES = 128;
	/**	
	 * Operation set n courses (reply)
	 */
	public static final int REPSETNCOURSES = 129;
	/**	
	 * Operation set n portions (request)
	 */
	public static final int REQSETNPORTIONS = 130;
	/**	
	 * Operation set n portions (reply)
	 */
	public static final int REPSETNPORTIONS = 131;
	/**	
	 * Operation update seats at table (request)
	 */
	public static final int REQUPDSEATSTABLE = 132;
	/**	
	 * Operation update seats at table (reply)
	 */
	public static final int REPUPDSEATSTABLE = 133;
	/**	
	 * Operation update seats at table at leaving (request)
	 */
	public static final int REQUPDSEATSTABLELV = 134;
	/**	
	 * Operation update seats at table at leaving (reply)
	 */
	public static final int REPUPDSEATSTABLELV = 135;
	/**
	 * Operation shut down general repository (request)
	 */
	public static final int REQGENERALREPOSHUT = 136;
	/**
	 * Operation shut down general repository (reply)
	 */
	public static final int REPGENERALREPOSHUT = 137;
}

