import static java.lang.System.*;
import java.util.Scanner;

public class PlayGuessGame 
{
  private static final Scanner scin = new Scanner(System.in);

  public static void help(GuessGame game) 
  {
    assert game != null;
    out.printf("Find the secret number in range [%d,%d]!\n", game.min(), game.max());
    out.printf("Available commands:\n");
    out.printf("  number     (new attempt to guess secret)\n");
    out.printf("  count      (show current number of attempts)\n");
    out.printf("  show       (show all attempts done)\n");
    out.printf("  help       (information on command usage)\n");
    out.printf("  quit       (exits the program)\n");
  }

  public static void main(String[] args) 
  {
    if (args.length != 0 && args.length != 2)
     {
      out.println("Usage: PlayGuessGame [ <min> <max> ]");
      out.println("(by default range [0,20] is used)");
      exit(1);
    }
    int i=0;
    int min = 0;
    int max = 20;
    //Array para calcular as jogadas feitas
    int [] values = new int [max-min+1];
    for(i=min; i<=max;i++)
    {
		values[i] = 000;
	}
    if (args.length == 2) 
    {
      // parse arguments...
    }
    GuessGame game = new GuessGame(min, max);

    out.println();
    help(game);
    out.println();
    while (!game.finished()) 
    {
      out.print("Command? ");
      String command = scin.next();
      out.println();
      switch (command) 
      {
      case "quit":
		out.println("You just quited the Game");
        exit(1);
        break;
      case "show":
		out.println("Attempts Made");
		for(int w=0;w<values.length;w++)
		{
			if(values[w]!=000)
			{
				out.println(values[w]);
			}
		}
		break;
      case "help":
        help(game);
        break;
      case "count":
        out.printf("Currently there were made %d attempts\n", game.numAttempts());
        break;
       case "number":
		int z=0;
		out.printf("Attempt: ");
		int n = scin.nextInt();
		if(game.validAttempt(n)==false)
		{
			out.printf("Invalid Attempt\n");
			break;
		}
		game.play(n);
		for(z=0;z<values.length;z++)
		{
			if(values[z]==000)
			{
				break;
			}
		}
		values[z]=n;
		if(game.attemptIsHigher())
		{
			out.printf("Too High!\n");
		}
		else if(game.attemptIsLower())
		{
			out.printf("Too Low!\n");
		}
		else if(game.finished())
		{
			out.printf("Congratulations you guessed the number");
		}
      default:
        break;
      }
      out.println();
    }
    out.printf("Game finished in %d attempts\n", game.numAttempts());
  }
}

