package commInfra;

/**
 *    Message exception.
 *
 *    Definition of an exception for an invalid message.
 */

public class MessageException extends Exception
{
  /**
   *   Version Id for serialization.
   */

  private static final long serialVersionUID = 2021L;

  /**
   *  Message that has given rise to the exception.
   */

   private final Message msg;

  /**
   *   Exception instantiation.
   *
   *      @param errorMessage sentence signaling the error condition
   *      @param msg message that has given rise to the exception
   */

   public MessageException (String errorMessage, Message msg)
   {
     super (errorMessage);
     this.msg = msg;
   }

  /**
   *  Getting the message that has given rise to the exception.
   *
   *     @return message
   */

   public Message getMessageVal ()
   {
     return (msg);
   }
}
