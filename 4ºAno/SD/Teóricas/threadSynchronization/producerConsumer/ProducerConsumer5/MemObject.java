package producerConsumer.ProducerConsumer5;

/**
 *    General description:
 *       definition of a generic memory of generic objects.
 */

public abstract class MemObject<R>

{
  /**
   *  Internal storage
   */

   protected R [] mem;                         // storage area

  /**
   *  Constructor
   *
   *    @param storage storage area
   */

   protected MemObject (R [] storage)
   {
     if (storage.length > 0)
        mem = storage;
        else mem = null;
   }

  /**
   *  Writing a value.
   *
   *    @param val value to store
   */

   protected abstract void write (R val);

  /**
   *  Reading a value.
   *
   *    @return the retrieved value
   */

   protected abstract R read ();
}
