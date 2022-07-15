package testPreemptiveness;

/**
 *    Shared variable.
 *    Access to it is public.
 */

public class Variable
{
  public volatile int a;                                   // shared access variable

  public Variable (int a)
  {
    this.a = a;
  }
}