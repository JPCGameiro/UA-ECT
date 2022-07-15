package commInfra;

/**
 *    Parametric memory.
 *    Non-instantiatable data type. It must be derived.
 *    Errors are reported.
 *
 *    @param <R> data type of stored objects
 */

public abstract class MemObject<R>

{
  /**
   *   Internal storage area.
   */

   protected R [] mem;

  /**
   *   Memory instantiation.
   *   The instantiation only takes place if the memory exists.
   *
   *     @param storage memory to be used
   *     @throws MemException when the memory does not exist
   */

   protected MemObject (R [] storage) throws MemException
   {
     if (storage != null)
        mem = storage;
        else throw new MemException ("illegal storage device!");
   }

  /**
   *   Memory write.
   *   A parametric object is written into it.
   *   Virtual method, it has to be overridden in a derived data type.
   *
   *    @param val parametric object to be written
   *    @throws MemException when the memory is full
   */

   protected abstract void write (R val) throws MemException;

  /**
   *   Memory read.
   *   A parametric object is read from it.
   *   Virtual method, it has to be overridden in a derived data type.
   *
   *    @return last parametric object that was written
   *    @throws MemException when the memory is empty
   */

   protected abstract R read () throws MemException;
}
