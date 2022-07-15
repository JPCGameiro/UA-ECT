package producerConsumer.ProducerConsumer5;

/**
 *    General description:
 *       definition of the basic operations that can be peerformend on a condition variable.
 *
 */

public interface ConditionVar
{
   public void await () throws IllegalThreadStateException, InterruptedException;
   public void signal () throws IllegalThreadStateException;
   public void signalAll () throws IllegalThreadStateException;
}
