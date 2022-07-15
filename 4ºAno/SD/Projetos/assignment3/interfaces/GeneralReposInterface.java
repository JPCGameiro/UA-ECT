package interfaces;

import java.rmi.*;

/**
 *   Operational interface of a remote object of type GeneralRepos.
 *
 *     It provides the functionality to access the General Repository of Information.
 */
public interface GeneralReposInterface extends Remote
{

	/**
	 * Write in the logging file the new chef state
	 * @param value chef state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void setChefState(int value) throws RemoteException;

	/**
	 * Write in the logging file the new waiter state
	 * @param value waiter state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void setWaiterState(int value) throws RemoteException;

	/**
	 * Write in the logging file the updated student state
	 * @param id student id
	 * @param value student state to set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void updateStudentState(int id, int value) throws RemoteException;
	
	/**
	 * Update student state
	 * @param id student id
	 * @param value student state to set
	 * @param hold specifies if prints line of report status
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void updateStudentState(int id, int value, boolean hold) throws RemoteException;

	/**
	 * Set variable nCourses and chefState to report status in the logging file
	 * @param value nCourses value to set
	 * @param chefState state of the chef to be set
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void setnCourses(int value, int chefState) throws RemoteException;

	/**
	 * Write the portion value and chefState in the logging file
	 * @param value nPortions value to set
	 * @param chefState state of the chef
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void setnPortions(int value, int chefState) throws RemoteException;
	
	/**
	 * Update the chef state, the nPortion and nCourse values
	 * 
	 * @param nPortion number of the portion to be set
	 * @param nCourse number of the course to be set
	 * @param chefState chef state
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void setnPortionsAndCourses(int nPortion, int nCourse, int chefState) throws RemoteException;

	/**
	 * Write to the logging file the updated seats values at the table 
	 * 
	 * @param seat seat at the table
	 * @param id student id to sit
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void updateSeatsAtTable(int seat, int id) throws RemoteException;

	/**
	 * Update the leaving of a student in the seats of the table
	 * @param id student id to leave table
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void updateSeatsAtLeaving(int id) throws RemoteException;
	
	/**
	 * Operation server shutdown.
	 * @throws RemoteException if either the invocation of the remote method, or the communication with the registry service fails
	 */
	public void shutdown () throws RemoteException;	
}
