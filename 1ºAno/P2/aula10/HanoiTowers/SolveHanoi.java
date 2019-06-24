import static java.lang.System.*;

public class SolveHanoi
{
  public static void main(String[] args) {
    if (args.length != 1) {
      out.println("Usage: java -ea SolveHanoi Number-of-Disks");
      exit(1);
    }

    int n = Integer.parseInt(args[0]);
    if (n < 1) {
      out.println("ERROR: invalid number of disks!");
      exit(2);
    }

    HanoiTowers t = new HanoiTowers(n);
    t.solve();
    out.format("Number of moves: %d\n", t.numberOfMoves());
  }
}
