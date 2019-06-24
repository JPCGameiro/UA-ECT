import static java.lang.System.*;

public class GuessGame 
{

  private int secretNumber; // to hold the secret
  private int min;
  private int max;
  private int numAttempts;
  private int lastAttempt;
  
  //...

  public GuessGame(int min, int max) 
  {
	  assert(max>min);
	  this.min=min;
	  this.max=max;
	  secretNumber = (int)(Math.random()*(max-min)+min);
	  numAttempts=0;
  }
  public int min() 
  {
	  return min;
  }

  public int max() 
  {
	  return max;	
  }

  public boolean validAttempt(int n) 
  {
	  boolean verify = false;
	  if((n>=min) && (n<=max))
	  {
		  verify = true;
	  }
	  else
	  {
		  verify = false;
	  }
	  return verify;
  }

  public boolean finished() 
  {
	  return lastAttempt==secretNumber;
  }

  public boolean attemptIsHigher() 
  {
	  assert (numAttempts() > 0);
	  return lastAttempt>secretNumber;
  }

  public boolean attemptIsLower() 
  {
	  assert (numAttempts() > 0);
	  return lastAttempt<secretNumber;   
  }

  public void play(int n) 
  {
	  assert validAttempt(n) : "Invalid Attempt";
	  assert finished() == false : "Game has already ended";
	  lastAttempt = n;
	  numAttempts++;
  }

  public int numAttempts() 
  {
	  return numAttempts;
  }

  /**
   * Simple tests of the GuessGame class.
   * This method tests the functionality of the GuessGame class.
   * It should be used by the programmer to test and debug the class.
   * It is not meant to be called in client programs.
   *
   * To run from the command line, use:
   *   java -ea GuessGame
   */
  public static void main(String[] args) {
    requireEA();
    out.println("Starting tests.");
    GuessGame game = new GuessGame(1, 10);
    // initial tests:
    assert !game.finished() : "game should not be finished yet";
    assert game.min() == 1;
    assert game.max() == 10;
    assert game.numAttempts() == 0 : "there should be no attempts yet";
    for(int i = -10; i <= 20; i++) {
      assert game.validAttempt(i) == (i >= 1 && i <= 10);
    }
    // trying all wrong answers:
    int nplay = 0; // how may times play was called
    for(int n = 1; n <= 10; n++) {
      if (n != game.secretNumber) {
        game.play(n); // make a wrong guess
        nplay++;
        assert game.numAttempts() == nplay;
        assert !game.finished() : "game should not be finished yet";
        assert (n < game.secretNumber) == game.attemptIsLower();
        assert (n > game.secretNumber) == game.attemptIsHigher();
      }
    }
    // make the right guess:
    game.play(game.secretNumber);
    nplay++;
    assert game.finished() : "game should be finished now";
    assert game.numAttempts() == nplay;
    out.println("No error detected!");
  }

  /** Check if program is being run with -ea, exit if not. */
  static void requireEA() {
    boolean ea = false;
    assert ea = true; // assert with a side-effect, on purpose!
    if (!ea) {
      err.println("This program must be run with -ea!");
      exit(1);
    }
  }

}

