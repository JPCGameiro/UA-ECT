package clientSide.entities;

/**
 *    Student cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */
public interface StudentCloning {
	
	/**
	 * Set student id
	 * 	@param id id of the student
	 */
	public void setStudentId(int id);
	
	/**
	 * Get student id
	 * 	@return id of the student
	 */
	public int getStudentId();
	
	/**
	 * Set student state
	 * 	@param state new state of the student
	 */
	public void setStudentState(int state);
	
	/**
	 * Get student state
	 * @return student state
	 */
	public int getStudentState();

}
