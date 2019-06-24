
import static java.lang.System.*;
import java.util.Scanner;

public class TestSupermarket
{
  final static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    SupermarketOrdering smarket = new SupermarketOrdering();

    String[] clients = { "Joao", "Ana", "Nuno", "Luis", "Carlos",
      "Antonio", "Jose", "Susana", "Rosa", "Odete",
      "Amelia", "Mario", "Afonso", "Leonor",
      "Irene" };
    String[] products = { "couves", "pão", "frango",
      "agrafador", "pente", "escova" };

    out.println("Este programa simula o supermercado DaEsquina.");
    out.println("Pressione ENTER sucessivamente para observar o desenrolar");
    out.println("de operações aleatórias de registo e serviço de encomendas.");
    while (in.hasNextLine()) {
      in.nextLine();

      int op = 0;
      if (smarket.numOrders()>0) { // if there are orders to serve,
        // randomly choose whether to enter or serve an order
        op = myRandom(2);
      }
      
      if (op == 0) { // create and enter a random order!
        int p = myRandom(products.length);
        int c = myRandom(clients.length);
        int q = 1+myRandom(4);
        Order order = new Order(clients[c], products[p], q);
        smarket.enterOrder(order);
        out.println("Enter order: " + order.toString());
      }
      else { // serve!
        Order order = smarket.serveOrder();
        out.println("Serve order: " + order.toString());
      }
      smarket.displayOrders();
    }
  }

  /** Devolve um número aleatório entre 0 e n-1.  */
  public static int myRandom(int n) {
    return (int)(Math.random()*n);
  }
}

